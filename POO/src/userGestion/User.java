package userGestion;

import java.net.*;

public class User {
		
	protected String Pseudo;
	protected InetAddress IP_address;
		
	public User(String Ps) {
		this.Pseudo = Ps ;
	}
	
	public User(String Ps, InetAddress IP) {
		this.Pseudo = Ps ;
		this.IP_address = IP ;
	}
	
	public String getUserPseudo() {
		return this.Pseudo ;
	}
	
	public InetAddress getUserIP() {
		return this.IP_address ;
	}
	
}
