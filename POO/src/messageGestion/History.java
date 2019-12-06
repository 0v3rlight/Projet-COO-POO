package messageGestion ;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import userGestion.LocalUser;

public class History {
	
	private String ListeMessages;
	
	public History ( String AdresseIPExterne) throws IOException {

		this.ListeMessages = Files.readString( Paths.get(".//History/" + AdresseIPExterne + ".txt"), StandardCharsets.US_ASCII);
		
	}
	
	public String getHistory(){
		return this.ListeMessages;
	}
	
	public void add_msg(Message msg) throws IOException {
		
		this.ListeMessages.concat(msg.toString());
		String stringMsg = msg.toString();
		String exteriorIP = "";
		
		if (msg.getReciever() instanceof LocalUser){
			exteriorIP = msg.getSender().getUserIP();
		}
		else{
			exteriorIP = msg.getReciever().getUserIP();
		}
		
		File file = new File("../Historique/" + exteriorIP + ".txt");
		
		if (file.exists() && !file.isDirectory()) { 
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.newLine();
			br.write(stringMsg);
			br.close();
			fr.close();
		}
		else{
			FileWriter fr = new FileWriter(file, true);
			fr.write(stringMsg);
			fr.close();
		}
		
	}
}
