package communication;

import java.net.*;
import java.util.Enumeration;

public class UDPSender {
	
	private DatagramSocket dSocket ;
	private int port = 1234 ;

	public UDPSender() {
		try {
			this.dSocket = new DatagramSocket(port);
		} catch (Exception e) {}
	}
	
	public void sendBroadcast(String msg) {
		try {
			InetAddress broadcast = null ;
			dSocket.setBroadcast(true);
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() ) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (interfaceReseau.getDisplayName().contains("en0")) {
					broadcast = interfaceReseau.getInterfaceAddresses().get(1).getBroadcast() ;
				}
			}
			System.out.println("L'adresse de broadcast est : "  + broadcast.toString()) ;
			byte[] data = msg.getBytes() ;
			DatagramPacket outpacket = new DatagramPacket(data, data.length, broadcast, port);
			dSocket.send(outpacket);
			System.out.println("Paquet envoyé" ) ;
			dSocket.setBroadcast(false);
		} catch (Exception e) {}
	}
	
	public void send(String contenu, String adresse_distante) {
		try {
			byte[] data = contenu.getBytes() ;
			DatagramPacket outpacket = new DatagramPacket(data, data.length, InetAddress.getByName(adresse_distante), port);
			dSocket.send(outpacket);
		} catch (Exception e) {}
	}

}
