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
				if (!interfaceReseau.getName().equals("lo")) {
					Enumeration<InetAddress> addresses = interfaceReseau.getInetAddresses();
					while (addresses != null && addresses.hasMoreElements() && interfaceReseau.getHardwareAddress().length > 0) {
						InetAddress nip = addresses.nextElement();
						if (nip instanceof Inet4Address) {
							if (nip.isSiteLocalAddress()) {
								ip = InetAddress.getByName(nip.getHostAddress());
								System.out.println(interfaceReseau.getName());
							}
						}
					}
				}
			}
			this.IP_address = ip.toString() ;
			this.IP_address = this.IP_address.replaceAll("/", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*InetAddress ip = null ;
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() ) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (interfaceReseau.getDisplayName().contains("eth4")) {
					ip = interfaceReseau.getInterfaceAddresses().get(1).getAddress() ;
				}
			}
			this.IP_address = ip.toString() ;
			this.IP_address = this.IP_address.replaceAll("/", "");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
}