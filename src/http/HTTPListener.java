package http;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import process.JavaProcessBuilder;

public class HTTPListener {
	private int portnumber;	
	private HTTPHandler httpHandler;
	
	public HTTPListener(int port, HTTPHandler hh) throws Exception {
		if (port < HTTPSettings.PORT_MIN || port > HTTPSettings.PORT_MAX) {
			System.out.println("The portnumber "+port+" is not valid. Turning to default port "+HTTPSettings.PORT_NUM);
			port = HTTPSettings.PORT_NUM;
		}
			
		this.portnumber=port;
		this.httpHandler=hh;
	}
	
	public void startUp() throws Exception {
		ServerSocket servsock = new ServerSocket(portnumber);
		System.out.println("Server started");
		System.out.println("Waiting for requests at port " + portnumber);
		
		while (true) {
			Socket s=servsock.accept();
			
			JavaProcessBuilder processBuilder = new JavaProcessBuilder();
			processBuilder.setWorkingDirectory(".");
			processBuilder.setMainClass("runner.HTTPRunner");
			processBuilder.addArgument(portnumber+"");
			processBuilder.startProcess();
			
			httpHandler.handleRequest(s.getInputStream(), s.getOutputStream());
			s.close();
		}		
	}
}
