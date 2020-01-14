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
	public ChatWindow cw ;
	
	public Vector<User> tabUsers = new Vector<User>() ;
	
	private DatagramSocket dSocket ;
	public int port = 0 ;
	private byte[] buffer = new byte[256];
	private UDPSender udps = new UDPSender();

	/*public UDPListener(int port_en) {
		try {
			this.port_envoi = port_en ;
			this.dSocket = new DatagramSocket(port);
			start();
		} catch (Exception e) {}
	}*/
	
	public UDPListener(ChatWindow chwi, LocalUser lu) {
		try {
			this.lu = lu ;
			this.cw = chwi ;
			this.dSocket = new DatagramSocket(1235);
			start();
		} catch (Exception e) {}
	}
	
	public UDPListener() {
		try {
			this.dSocket = new DatagramSocket(1235);
			System.out.println(this.dSocket.getLocalPort()) ;
			System.out.println(this.dSocket.getPort()) ;
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
		System.out.println("[refreshingActiveUsers]");
		if(msg.contains(" ")){
			String[] output = msg.split(" ");
			if(output.length!=3){
				System.out.println("Paquet inutile reçu (mauvais nombre d'arguments) ");
			} else {
				User currentUser = new User(output[1],output[2]);
				if(output[0].compareTo("Hello") == 0) {
					//if (output[2].compareTo(lu.getUserIP()) != 0) {
						tabUsers.addElement(currentUser);
						this.cw.model.addElement(output[1]);
					//}
				} else if (output[0].compareTo("Bye") == 0) {
					java.util.Iterator<User> itr = tabUsers.iterator();
					User element ;
					User a_effacer = null;
					while(itr.hasNext()) {
						element = itr.next();
					    if ((element.getUserPseudo().contentEquals(currentUser.getUserPseudo())) && (element.getUserIP().contentEquals(currentUser.getUserIP()))) {
					    	a_effacer = element;
					    }
					}
					tabUsers.remove(a_effacer);
					//tabUsers.remove(currentUser);
					this.cw.model.removeElement(output[1]);
				} else if (output[0].compareTo("New") == 0) {
					if (output[2].compareTo(lu.getUserIP()) != 0) {
						String ad_distante = output[2];
						udps.send(("Hello "+lu.getUserPseudo()+" "+lu.getUserIP()), ad_distante);
					}
				} else {
					System.out.println("Paquet inutile reçu : " + output[0]);
				}
			}
		}else{
			System.out.println("Paquet inutile reçu (pas le bon format)");
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
	
	// Find the IP address given the pseudo
		public User findUser(String Pseudo) {
			java.util.Iterator<User> itr = tabUsers.iterator();
			User element ;
			User recherche = null ;
		    while(itr.hasNext())
		    {
		    	element = itr.next();
		    	if (element.getUserPseudo() == Pseudo) {
		    		recherche = element ;
		    	}
		    }
		    return recherche;
		}
	
	// return 0 if the pseudo is not available, else 1
	public int pseudoIsAvailable(String Pseudo) {
		java.util.Iterator<User> itr = tabUsers.iterator();
		User element ;
		int res = 1;
		while(itr.hasNext()) {
			element = itr.next();
		    if (element.getUserPseudo().compareTo(Pseudo) == 0) {
		    	res = 0;
		    }
		}
		return res;
	}
	
	public void afficherUsers() {
        System.out.println("Affichage du taleau des Users actifs");
        java.util.Iterator<User> itr = tabUsers.iterator();
		User element ;
		while(itr.hasNext()) {
			element = itr.next();
		    System.out.println(element.getUserPseudo() + " / " + element.getUserIP());
		}
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
	
	public void run() {
		// TODO : The use of the thread UDPListener ?
		//		 -> It will create a UDPSender to broadcast to anyone that it is connected
		//		 -> It will then wait for any responses and build an array of others active users
		//		 -> It would be its charge to display and refresh the user selection window
		String msg ;
		System.out.println(this.dSocket.getPort());
		while (true) {
			try {
				msg = receive() ;
				refreshingActiveUsers(msg);
				afficherUsers();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public static void main (String args[]) {
		UDPListener udpl = new UDPListener();
	}*/

}
