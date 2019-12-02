package communication;

import java.io.*;
import java.net.*;

public class TCPServer {

	private Socket link ;
	private BufferedReader in ;
	private PrintWriter out ;
	
	public TCPServer(int port) throws IOException {
        // Creating Socket Server
        @SuppressWarnings("resource")
		ServerSocket servSock = new ServerSocket(port);
        // Get the associated socket
        link = servSock.accept();
        // input and output configuration
        in = new BufferedReader(new InputStreamReader(link.getInputStream())); 
        out = new PrintWriter(link.getOutputStream(),true);
        
	}
	
	public void sendingData(String data) {
        out.println(data);
	}
	
	public String receivingData() throws IOException {
		return in.readLine();
	}
	
	public void closeConnection() throws IOException {
		link.close();
	}

}