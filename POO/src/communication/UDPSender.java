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
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() ) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (interfaceReseau.getDisplayName().contains("eth0")) {
					broadcast = interfaceReseau.getInterfaceAddresses().get(1).getBroadcast() ;
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
	
	public static void main(String[] args) throws SocketException {
		UDPSender udps = new UDPSender();
		udps.send("Hello HAHAHAHAHA 0", "10.1.5.27");
        /*while (true) {
        	UDPSender udps = new UDPSender();
            Scanner sc = new Scanner(System.in);
            System.out.println("Que voulez-vous envoyer ?");
            String str = sc.nextLine();
            udps.sendBroadcast(str);
            System.out.println("Vous avez envoyé : " + str);
        }*/
    }

}
