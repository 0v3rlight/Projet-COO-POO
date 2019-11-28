package userGestion;

import java.net.*;

public class LocalUser extends User {

	public LocalUser(String Ps) {
		this.Pseudo = Ps ;
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			this.IP_address = ip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			NetworkInterface interf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			byte[] address_MAC = interf.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < address_MAC.length; i++) {
				sb.append(String.format("%02X%s", address_MAC[i], (i < address_MAC.length - 1) ? ":" : ""));		
			}
			this.MAC_address = sb.toString() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] arg) {
		LocalUser user1 = new LocalUser("user1");
		System.out.println(user1.IP_address);
		System.out.println(user1.MAC_address);
	}

}