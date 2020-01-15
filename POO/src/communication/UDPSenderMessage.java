package communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSenderMessage {
	
	private DatagramSocket dSocket ;
	private int port = 1236;
	private String ad_distante ;
	
	public UDPSenderMessage(String ad_dist) {
		try {
			this.dSocket = new DatagramSocket();
			this.ad_distante = ad_dist ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(String contenu) {
		try {
			byte[] data = contenu.getBytes() ;
			DatagramPacket outpacket = new DatagramPacket(data, data.length, InetAddress.getByName(ad_distante), port);
			dSocket.send(outpacket);
			System.out.println("Paquet envoyé individuellement : " + contenu) ;
		} catch (Exception e) {}
	}
	
}
