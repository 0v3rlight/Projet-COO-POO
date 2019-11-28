import java.util.Date;

import com.sun.tools.javac.util.Pair;

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

	public String GetContent()
	{
		return this.content;
	}
	public Date GetDate()
	{
		return this.date;
	}
	public User GetSender()
	{
		return this.sender;
	}
	public User GetReciever()
	{
		return this.reciever;
	}

}
