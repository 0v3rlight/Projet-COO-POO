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
	
	public Vector<User> tabUsers = new Vector<User>() ;
	public Vector<Session> tabSessions = new Vector<Session>() ;
	
	private DatagramSocket dSocket ;
	public int port ;
	private byte[] buffer = new byte[256];
	private UDPSender udps = new UDPSender();
	
	public UDPListenerMessage() {
		try {
			this.dSocket = new DatagramSocket(1236);
			this.port = this.dSocket.getLocalPort();
			start();
		} catch (Exception e) {}
	}
	
	public void receive() throws IOException {
		DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
		dSocket.receive(inPacket) ;
		String response = new String(inPacket.getData(), 0, inPacket.getLength());
		String[] output = response.split(" ");
		String adresse_paquet = inPacket.getAddress().toString().replaceAll("/", "");
		
		java.util.Iterator<Session> itr = tabSessions.iterator();
		Session se ;
	    while(itr.hasNext())
	    {
	    	se = itr.next();
	    	if ((output[0].compareTo("Message") == 0) && (se.utilisateurDistant.getUserIP().contains(adresse_paquet))) {
	    		String msg = response.replaceFirst("Message ", "");
	    		System.out.println("Message reçu : " + msg);
	    		Message Msg = new Message(msg,se.utilisateurDistant,se.utilisateurLocal);
	    		se.historique.add_msg(Msg);
	    		se.refreshHistory();
	    	} else {
	    		System.out.println("Le signal reçu en UDP n'est pas un message");
	    	}
	    }
	    
	}
	
	public void addSession (Session se) {
		tabSessions.add(se);
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
