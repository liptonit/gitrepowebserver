package http;

import java.io.*;

public interface HTTPHandler {
	public void handleRequest(InputStream in, OutputStream out);
}
