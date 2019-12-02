package communication;

import java.net.* ;
import java.io.* ;

public class TCPClient {
	
	private Socket link ;
	private BufferedReader in ;
	private PrintWriter out ;

	public TCPClient(InetAddress ip, int port) throws IOException {
        link = new Socket(ip,port);
        // Input and output configuration
        in = new BufferedReader(new InputStreamReader(link.getInputStream())); 
        out = new PrintWriter(link.getOutputStream(),true);
	}
	
	public void sendingData(String data) {
        out.println(data);
	}
	
	public String receivingData() throws IOException {
		return in.readLine();
	}
	
	public void closeConnection() throws IOException {
		link.close();
	}

}
