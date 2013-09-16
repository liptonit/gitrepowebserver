package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;

public class HTTPRespons {
	private OutputStream out;
	private HTTPRequest request;

	public HTTPRespons(OutputStream out) {
		this.out = out;
	}

	public void setRequest(HTTPRequest request) {
		this.request = request;
	}

	public void sendResponse() throws IOException {
		byte[] bytes = new byte[HTTPSettings.BUFFER_SIZE];
		FileInputStream fis = null;
		String fileName = request.getUri();

		try {		
			File file = new File(HTTPSettings.DOC_ROOT, fileName);
			String contentType = null;
			Integer statusCode = 0;		
			
			if(file.exists()) {
				String fileType = getFileType(fileName);
				contentType = HTTPSettings.dataTypes.get(fileType.toUpperCase());
				if(contentType != null) {
					statusCode = 200;
				} else {
					file = new File(HTTPSettings.INTERAL_SERVER_ERROR);
					contentType = HTTPSettings.dataTypes.get("HTML");
					statusCode = 500;
				}
			} else {
				file = new File(HTTPSettings.FILE_NOT_FOUND);
				contentType = HTTPSettings.dataTypes.get("HTML");
				statusCode = 404;
			}
			
			FileInputStream inputStream = getInputStream (file);
			
			out.write(getHTTPHeader(file, contentType, statusCode)); 
		
			int ch = inputStream.read(bytes, 0, HTTPSettings.BUFFER_SIZE);
			while (ch != -1) {
				out.write(bytes, 0, ch);
				ch = inputStream.read(bytes, 0, HTTPSettings.BUFFER_SIZE);
			}
		} catch (Exception e) {
			this.sendErrorResponse(e.getStackTrace().toString());
		} finally {
			if (fis != null)
				fis.close();
			System.exit(-1);
		}
	}
	
	private FileInputStream getInputStream (File file) {		
		FileInputStream fis = null;

		try{
			fis = new FileInputStream(file);
		} catch(FileNotFoundException e) {
			File notFound = new File(HTTPSettings.FILE_NOT_FOUND);
			try {
				fis = new FileInputStream(notFound);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.out.println("EXCEPTION 3");
			}			
			System.out.println("EXCEPTION 2");
		} 
				
		return fis;		
	}

	private byte[] getHTTPHeader(File file, String contentType, Integer statusCode) {	
		
		long contentLength = file.length();
				
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");		
		String header = "" +
				"HTTP/1.1 "+HTTPSettings.httpCodes.get(statusCode)+" \n" +
				"Content-type: " + contentType + "\n" +
				"Content-length: " + contentLength + "\n" +
				"Date: "+simpleFormat.format(date)+ "\n" +
				"Server: Henri zijn server " + 
				"\n\n"; 
		byte[] rv = header.getBytes();
		return rv;
	}

	private String getFileType(String fileName) {
		int i = fileName.lastIndexOf(".");
		String ext = "";
		if (i > 0 && i < fileName.length() - 1) {
			ext = fileName.substring(i + 1);
		}

		return ext;
	}
	
	public void sendErrorResponse(String stackTrace) {
		String test = "TEST";
		File file = new File(HTTPSettings.INTERAL_SERVER_ERROR);
		byte[] bytes = new byte[HTTPSettings.BUFFER_SIZE];
		try {
			out.write(getHTTPHeader(file, HTTPSettings.dataTypes.get("HTML"), 500)); 			
			FileInputStream inputStream = new FileInputStream(file);
			int ch = inputStream.read(bytes, 0, HTTPSettings.BUFFER_SIZE);
			while (ch != -1) {
				out.write(bytes, 0, ch);
				ch = inputStream.read(bytes, 0, HTTPSettings.BUFFER_SIZE);
			}				
			out.write(test.getBytes());	
			inputStream.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(-1);
		}
	}

	private void showResponse(byte[] respons) {
		StringBuffer buf = new StringBuffer(HTTPSettings.BUFFER_SIZE);

		for (int i = 0; i < respons.length; i++) {
			buf.append((char) respons[i]);
		}
		System.out.print(buf.toString());

	}

}
