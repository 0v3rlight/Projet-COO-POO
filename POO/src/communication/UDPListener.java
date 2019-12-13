package communication;

import java.io.IOException;
import java.net.*;

public class UDPListener extends Thread {
	
	private DatagramSocket dSocket ;
	private int port = 1234 ;
	private byte[] buffer = new byte[256];

	public UDPListener() {
		try {
			this.dSocket = new DatagramSocket(port);
		} catch (Exception e) {}
	}
	
	public String receive() throws IOException {
		DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
		dSocket.receive(inPacket) ;
		String response = new String(inPacket.getData(), 0, inPacket.getLength());
		System.out.println("Paquet reçu : " + response);
		return response ;
	}

}
