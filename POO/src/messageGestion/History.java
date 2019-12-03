package messageGestion ;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import userGestion.LocalUser;

public class History {
	
	private List<Message> ListeMessages;
	
	public History ( String AdresseIPExterne) {
		
		
		
	}
	
	public List<Message> getHistory(){
		return this.ListeMessages;
	}
	
	public void add_msg(Message msg) throws IOException {
		
		this.ListeMessages.add(msg);
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
