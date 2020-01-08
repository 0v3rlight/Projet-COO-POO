package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

import userGestion.LocalUser;
import userGestion.User;
import Frame.*;

public class UDPListener extends Thread {
	
	public LocalUser lu ;
	public ChatWindow cw = new ChatWindow(lu,this) ;
	
	public Vector<User> tabUsers = new Vector<User>() ;
	
	private DatagramSocket dSocket ;
	public int port = 12000 ;
	private int port_envoi = 67000;
	private byte[] buffer = new byte[256];
	private UDPSender udps = new UDPSender(port);

	/*public UDPListener(int port_en) {
		try {
			this.port_envoi = port_en ;
			this.dSocket = new DatagramSocket(port);
			start();
		} catch (Exception e) {}
	}*/
	
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
	
	// Update the users table by analyzing the received message
	// Message format :
	// Prefix Pseudo IP
	public void refreshingActiveUsers(String msg) {
		if(msg.contains(" ")){
			String[] output = msg.split(" ");
			if(output.length!=3){
				throw new IllegalArgumentException(msg + " - invalid format (numbers of arguments) !");
			}else{
				User currentUser = new User(output[1],output[2]);
				if(output[0].compareTo("Hello") != 0) {
					tabUsers.addElement(currentUser);
					this.cw.model.addElement(output[1]);
				} else if (output[0].compareTo("Bye") != 0) {
					tabUsers.remove(currentUser);
					this.cw.model.removeElement(output[1]);
				} else if (output[0].compareTo("New") != 0) {
					String ad_distante = output[2];
					udps.send(("Hello "+lu.getUserPseudo()+" "+lu.getUserIP()), ad_distante);
				} else {
					System.out.println(output[0]);
					throw new IllegalArgumentException(msg + " - invalid format for first argument !");
				}
			}
		}else{
			throw new IllegalArgumentException(msg + " - invalid format ! (no spaces detected)");
		}
	}
	
	// Find the IP address given the pseudo
	public String findIP(String Pseudo) {
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
	
	// return 0 if the pseudo is not available, else 1
	public int pseudoIsAvailable(String Pseudo) {
		java.util.Iterator<User> itr = tabUsers.iterator();
		User element ;
		int res = 1;
		while(itr.hasNext()) {
			element = itr.next();
		    if (element.getUserPseudo().compareTo(Pseudo) != 0) {
		    	res = 0;
		    }
		}
		return res;
	}
	
	
	public void run() {
		// TODO : The use of the thread UDPListener ?
		//		 -> It will create a UDPSender to broadcast to anyone that it is connected
		//		 -> It will then wait for any responses and build an array of others active users
		//		 -> It would be its charge to display and refresh the user selection window
		String msg ;
		while (true) {
			try {
				msg = receive() ;
				refreshingActiveUsers(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
