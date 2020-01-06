package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

import userGestion.User;
import Frame.*;

public class UDPListener extends Thread {
	
	public ChatWindow cw ;
	
	public Vector<User> tabUsers = new Vector<User>() ;
	
	private DatagramSocket dSocket ;
	private int port = 1234 ;
	private byte[] buffer = new byte[256];

	public UDPListener(ChatWindow chwi) {
		try {
			this.cw = chwi ;
			this.dSocket = new DatagramSocket(port);
			start();
		} catch (Exception e) {}
	}
	
	public String receive() throws IOException {
		DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
		dSocket.receive(inPacket) ;
		String response = new String(inPacket.getData(), 0, inPacket.getLength());
		System.out.println("Paquet reçu : " + response);
		return response ;
	}
	
	public void refreshingActiveUsers(String msg, Vector<User> tabUsers) {
		if(msg.contains(" ")){
			String[] output = msg.split(" ");
			if(output.length!=3){
				throw new IllegalArgumentException(msg + " - invalid format (numbers of arguments) !");
			}else{
				User currentUser = new User(output[1],output[2]);
				if(output[0] == "Hello") {
					tabUsers.addElement(currentUser);
					this.cw.model.addElement(output[1]);
				} else if (output[0] == "Bye") {
					tabUsers.remove(currentUser);
					this.cw.model.removeElement(output[1]);
				} else {
					throw new IllegalArgumentException(msg + " - invalid format for first argument !");
				}
			}
		}else{
			throw new IllegalArgumentException(msg + " - invalid format ! (no spaces detected)");
		}
	}
	
	public String findIP(String Pseudo, Vector<User> tabUsers) {
		java.util.Iterator<User> itr = tabUsers.iterator();
		User element ;
		String IP = null ;
	    while(itr.hasNext())
	    {
	    	element = itr.next();
	    	if (element.getUserPseudo() == Pseudo) {
	    		IP = element.getUserIP() ;
	    	}
	    }
	    return IP;
	}
	
	public void run() {
		// TODO : The use of the thread UDPListener ?
		//		 -> It will create a UDPSender to broadcast to anyone that it is connected
		//		 -> It will then wait for any responses and build an array of others active users
		//		 -> It would be its charge to display and refresh the user selection window
	}

}
