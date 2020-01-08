package userGestion;

import java.net.*;
import java.util.Enumeration;

public class LocalUser extends User {

	public LocalUser(String Ps) {
		super(Ps) ;
		InetAddress ip = null ;
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() ) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (interfaceReseau.getDisplayName().contains("en0")) {
					ip = interfaceReseau.getInterfaceAddresses().get(1).getAddress() ;
				}
			}
			this.IP_address = ip.toString() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}