package communication;

import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

public class UDPSender {
	
	private DatagramSocket dSocket ;
	private int port = 1235;

	public UDPSender() {
		try {
			this.dSocket = new DatagramSocket();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendBroadcast(String msg) {
		try {
			InetAddress broadcast = null ;
			dSocket.setBroadcast(true);
			
			/*Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() ) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (!interfaceReseau.getName().equals("lo")) {
					Enumeration<InetAddress> addresses = interfaceReseau.getInetAddresses();
					while (addresses.hasMoreElements() && interfaceReseau.getHardwareAddress().length > 0) {
						InetAddress nip = addresses.nextElement();
						if (nip instanceof Inet4Address) {
							if (nip.isSiteLocalAddress()) {
								broadcast = interfaceReseau.getInterfaceAddresses().get(0).getBroadcast() ;
								System.out.println(interfaceReseau.getName());
							}
						}
					}
				}
			}*/
			
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() ) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (interfaceReseau.getDisplayName().contains("eth4")) {
					System.out.println("dans le if") ;
					broadcast = interfaceReseau.getInterfaceAddresses().get(0).getBroadcast() ;
					System.out.println("fin du if") ;
				}
			}
			
			System.out.println("L'adresse de broadcast est : "  + broadcast.toString()) ;
			byte[] data = msg.getBytes() ;
			DatagramPacket outpacket = new DatagramPacket(data, data.length, broadcast, 1235);
			dSocket.send(outpacket);
			System.out.println("Paquet envoyé en broadcast : " + msg) ;
			dSocket.setBroadcast(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(String contenu, String adresse_distante) {
		try {
			byte[] data = contenu.getBytes() ;
			DatagramPacket outpacket = new DatagramPacket(data, data.length, InetAddress.getByName(adresse_distante), port);
			dSocket.send(outpacket);
			System.out.println("Paquet envoyé individuellement : " + contenu) ;
		} catch (Exception e) {}
	}

}
