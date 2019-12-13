package main;

import java.io.IOException;
import java.net.InetAddress;

import communication.*;

public class mainClass {

	public mainClass() {
	}
	
	public static void main (String arg[]) throws IOException {
		TCPServer server = new TCPServer(12036);
		TCPClient client = new TCPClient(InetAddress.getLocalHost(),12037);
		server.sendingData("Je suis le client et je parle au serveur\n");
		System.out.println(client.receivingData());
		server.closeConnection();
		client.closeConnection();
	}

}
