/*package communication;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class TCPServer {

	private Socket link ;
	private BufferedReader in ;
	//private PrintWriter out ;
	private Boolean active = true ;
	// Threads list that have been created
	private Vector<ServerThread> tabClients = new Vector<ServerThread>() ;
	
	public TCPServer(int port) throws IOException {
        // Creating Socket Server
        @SuppressWarnings("resource")
		ServerSocket servSock = new ServerSocket(port);
        // Wait for incoming connections
        while (active) {
        	ServerThread newClient = new ServerThread(servSock.accept()) ;
        	// NEED A COMMAND TO NOTIFY THE LOCAL USER OF A NEW CONNECTION
        	tabClients.add(newClient);
        }
        link.close();
	}
	
	public void sendingData(String data) {
        
	}
	
	public String receivingData() throws IOException {
		return in.readLine();
	}
	
	public void closeConnection() throws IOException {
		active = false;
	}

}*/