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
			// getHardwareAddress return sometimes null, sometimes the real MAC address
			NetworkInterface interf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			byte[] mac = interf.getHardwareAddress();
			StringBuilder sbe = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sbe.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
			}
			this.MAC_address = sbe.toString() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}