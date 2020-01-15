package userGestion;

import java.net.*;

public class User {
	
	public String Pseudo;
	public String IP_address;
	
	public User(String Ps) {
		this.Pseudo = Ps ;
	}
	
	public User(String Ps, String IP) {
		this.Pseudo = Ps ;
		this.IP_address = IP ;
	}
	
	public void SetPseudo(String ps) {
		this.Pseudo = ps ;
	}
	
	public String getUserPseudo() {
		return this.Pseudo ;
	}
	
	public String getUserIP() {
		return this.IP_address ;
	}
	
}
