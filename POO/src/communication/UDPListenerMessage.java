package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

import userGestion.LocalUser;
import userGestion.User;
import Frame.*;
import Session.Session;
import messageGestion.Message;

public class UDPListenerMessage extends Thread {
	
	public LocalUser lu ;
	public Session s ;
	
	public Vector<User> tabUsers = new Vector<User>() ;
	
	private DatagramSocket dSocket ;
	public int port = 0 ;
	private byte[] buffer = new byte[256];
	private UDPSender udps = new UDPSender();
	
	public UDPListenerMessage(Session s) {
		try {
			this.s = s ;
			this.dSocket = new DatagramSocket(1236);
			start();
		} catch (Exception e) {}
	}
	
	public void receive() throws IOException {
		DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
		dSocket.receive(inPacket) ;
		String response = new String(inPacket.getData(), 0, inPacket.getLength());
		String[] output = response.split(" ");
		if (output[0].compareTo("Message") == 0) {
			String msg = response.replaceFirst("Message ", "");
			Message Msg = new Message(msg,this.s.utilisateurDistant,this.s.utilisateurLocal);
			this.s.historique.add_msg(Msg);
			this.s.refreshHistory();
		} else {
			System.out.println("Le signal reçu en UDP n'est pas un message");
		}
	}
	
	public void run() {
		while (true) {
			try {
				receive();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
