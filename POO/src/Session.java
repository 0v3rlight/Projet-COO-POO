import java.io.File;
import java.io.IOException;

import messageGestion.History;
import userGestion.*;


public class Session {
	private User LocalUser;
	private User DistantUser;
	private History SessionHistory;
	//private SessionFrame SessionFrame;
	
	public Session(User LocalUser, User DistantUser, History SessionHistory) throws IOException
	{
		this.LocalUser = LocalUser;
		this.DistantUser = DistantUser;
		fetchHistory();
	}
	
	private void fetchHistory() throws IOException
	{
		File file = new File("../Historique/" + this.DistantUser.getUserMAC() + ".txt");
		
	}

}
