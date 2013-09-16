package runner;

import java.io.File;
import java.util.Scanner;

import http.HTTPHandlerImpl;
import http.HTTPListener;
import http.HTTPSettings;

public class HTTPRunner {
	public static void main (String args[]) {
		/* 
		  *** OPGAVE 1.1 ***
		  zorg ervoor dat het port-nummer en de basis-directory vanuit de command-line kunnen worden meegegeven.
		  LET OP: de default-waarden moet je nog wel instellen in de Settings-klasse.
		*/
		
		int portnumber = 0;
		if(args.length > 0) {
			portnumber = Integer.parseInt(args[0]);
		} else {
			System.out.println("The portnumber to listen to is: ");			
			Scanner scanner = new Scanner(System.in);
			portnumber =  Integer.parseInt(scanner.nextLine());
			scanner.close();
		}
		
		
	    try {
	    	HTTPListener listener = new HTTPListener (portnumber, new HTTPHandlerImpl());
	    	listener.startUp();	    	
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
