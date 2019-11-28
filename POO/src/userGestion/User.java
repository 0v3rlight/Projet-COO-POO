package userGestion;

public class User {
		
	protected String Pseudo;
	protected String IP_address;
	protected String MAC_address;
		
	public User() {}
	
	public User(String Ps, String IP, String MAC) {
		this.Pseudo = Ps ;
		this.IP_address = IP ;
		this.MAC_address = MAC ;
	}
	
	public String getUserPseudo() {
		return this.Pseudo ;
	}
	
	public String getUserIP() {
		return this.IP_address ;
	}
	
	public String getUserMAC() {
		return this.MAC_address ;
	}
	
}
