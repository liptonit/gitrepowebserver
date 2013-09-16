package http;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public final class HTTPSettings {
	
	static final String DOC_ROOT = "C:/Users/Henri Nijborg/Documents/Specialisatie Webdevelopment/Opgave 4";
	static final String FILE_NOT_FOUND = "C:/Users/Henri Nijborg/Documents/Specialisatie Webdevelopment/Opgave 4/error/404.html";
	static final String INTERAL_SERVER_ERROR = "C:/Users/Henri Nijborg/Documents/Specialisatie Webdevelopment/Opgave 4/error/500.html";
	
	static final int BUFFER_SIZE = 2048;	
	static final int PORT_MIN=0;
	static final int PORT_MAX=65535;
	
	static final public int PORT_NUM = 4444;
	static final HashMap<String, String> dataTypes; 
	static {
		dataTypes = new HashMap<String, String>();
		dataTypes.put("HTML", "text/html");
		dataTypes.put("CSS", "text/css");
		dataTypes.put("GIF", "image/gif");
		dataTypes.put("PNG", "image/png");
		dataTypes.put("JPEG", "image/jpeg");
		dataTypes.put("JS", "text/javascript");
		dataTypes.put("TXT", "text/plain");
		dataTypes.put("PDF", "application/pdf");
	}
	
	static final HashMap<Integer, String> httpCodes;
	static {
		httpCodes = new HashMap<Integer, String>();
		httpCodes.put(200, "200 OK");
		httpCodes.put(404, "404 NOT_FOUND");
		httpCodes.put(500, "500 INTERNAL_SERVER_ERROR");		
	}


	static final String[] DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	public static String getDate() {
		GregorianCalendar cal = new GregorianCalendar();
		String rv = "";
		rv += DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", ";
		rv += cal.get(Calendar.DAY_OF_MONTH) + " " + MONTHS[cal.get(Calendar.MONTH)];
		rv += " " + cal.get(Calendar.YEAR) + "\r\n";
		
		return rv;
	}
}
