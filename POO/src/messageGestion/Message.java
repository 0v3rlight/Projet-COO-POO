package messageGestion ;

import java.util.Date;
import userGestion.User;

public class Message {
	private String content;
	private Date date;
	private User sender;
	private User reciever;
	
	public Message(String content, Date date, User sender, User reciever)
	{
		this.content = content;
		this.date = date;
		this.sender = sender;
		this.reciever = reciever;
	}

	public String getContent()
	{
		return this.content;
	}
	public Date getDate()
	{
		return this.date;
	}
	public User getSender()
	{
		return this.sender;
	}
	public User getReciever()
	{
		return this.reciever;
	}

	public String toString(){
		String stringMsg = 	"[ To : " + this.getSender().getUserPseudo() +
				" ; From : " + this.getReciever().getUserPseudo() +
				" ; Date : " + this.getDate().toString() +"]\n"
				+ this.getContent() + "\n\n";
		return stringMsg;
	}
}
