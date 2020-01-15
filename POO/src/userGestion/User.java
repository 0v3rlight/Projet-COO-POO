package userGestion;

public class User {
	
	public String Pseudo;
	public String IP_address;
	protected int Port;
	
	public User(String Ps) {
		this.Pseudo = Ps ;
	}
	
	public User(String Ps, String IP) {
		this.Pseudo = Ps ;
		this.IP_address = IP ;
	}
	
	public User(String Ps, String IP, int Port) {
		this.Pseudo = Ps ;
		this.IP_address = IP ;
		this.Port = Port;
	}
	
	public void SetPseudo(String ps) {
		this.Pseudo = ps ;
	}
	
	public int getUserPort() {
		return this.Port;
	}
	
	public String getUserPseudo() {
		return this.Pseudo ;
	}
	
	public String getUserIP() {
		return this.IP_address ;
	}
	
}
