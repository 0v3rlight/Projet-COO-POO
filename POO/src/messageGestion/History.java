package messageGestion ;
import java.util.List;

public class History {
	
	private List<Message> ListeMessages;
	
	public List<Message> getHistory()
	{
		return this.ListeMessages;
	}
	
	public void add_msg(Message msg)
	{
		this.ListeMessages.add(msg);
	}

}
