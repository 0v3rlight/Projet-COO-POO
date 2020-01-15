package messageGestion ;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import userGestion.LocalUser;

public class History {
	
	private String ListeMessages;
	public LocalUser lu;
	
	public History (LocalUser lu, String AdresseIPExterne) throws IOException {
		this.lu = lu ;
		File file = new File("Historique/" + AdresseIPExterne + "-" + lu.getUserIP() + ".txt");
		file.createNewFile();
		this.ListeMessages = Files.readString(Paths.get("Historique/" + AdresseIPExterne + "-" + lu.getUserIP() + ".txt"), StandardCharsets.US_ASCII);

	}
	
	public String getHistory(){
		return this.ListeMessages;
	}
	
	public void add_msg(Message msg) throws IOException {

		String stringMsg = msg.toString();
		this.ListeMessages.concat(msg.toString());
		String exteriorIP = "";
		
		if (msg.getReciever().getUserIP().compareTo(lu.getUserIP())==0){
			exteriorIP = msg.getSender().getUserIP().toString();
		}
		else if (msg.getSender().getUserIP().compareTo(lu.getUserIP())==0){
			exteriorIP = msg.getReciever().getUserIP().toString();
		}
		
		File file = new File("Historique/" + exteriorIP + "-" + lu.getUserIP() + ".txt");
		
		if (file.exists() && !file.isDirectory()) {
			FileWriter writer = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(writer);
			br.newLine();
			br.write(stringMsg);
			br.close();
			writer.close();
		}
		else{
			file.createNewFile();
			FileWriter writer = new FileWriter(file, true);
			writer.write(stringMsg);
			writer.close();
		}
		
	}
}
