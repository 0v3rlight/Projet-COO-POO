package communication;

import java.net.*;
import java.util.Enumeration;

public class UDPSender {
	
	private DatagramSocket dSocket ;
	private int port = 0;
	private int port_ecoute = 67000;

	public UDPSender(int port_e) {
		try {
			this.port_ecoute = port_e ;
			this.dSocket = new DatagramSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendBroadcast(String msg) {
		System.out.println("Début sendBroadcast" ) ;
		try {
			InetAddress broadcast = null ;
			dSocket.setBroadcast(true);
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() ) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (interfaceReseau.getDisplayName().contains("eth0")) {
					broadcast = interfaceReseau.getInterfaceAddresses().get(1).getBroadcast() ;
				}
			}
			System.out.println("L'adresse de broadcast est : "  + broadcast.toString()) ;
			byte[] data = msg.getBytes() ;
			DatagramPacket outpacket = new DatagramPacket(data, data.length, broadcast, port_ecoute);
			dSocket.send(outpacket);
			System.out.println("Paquet envoyé" ) ;
			dSocket.setBroadcast(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(String contenu, String adresse_distante) {
		try {
			byte[] data = contenu.getBytes() ;
			DatagramPacket outpacket = new DatagramPacket(data, data.length, InetAddress.getByName(adresse_distante), port_ecoute);
			dSocket.send(outpacket);
		} catch (Exception e) {}
	}

}
