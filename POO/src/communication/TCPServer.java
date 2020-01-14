package communication;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JList;

import userGestion.User;

@SuppressWarnings("deprecation")
public class TCPServer extends Observable {

	private List<User> NewUserList;
	private int Port;

	public TCPServer() {

	}

	public List<User> initUsers(List<User> UserWithoutSocketList) {

		for (User CurrentUser : UserWithoutSocketList) {
			User UserWithSocket = new User();
			Socket Socket;
			try {
				UserWithSocket = openSocket(CurrentUser);
				NewUserList.add(UserWithSocket);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List<User> UserList = NewUserList;
		NewUserList.clear();
		return UserList;
	}

	public User openSocket(User User) throws UnknownHostException, IOException {
		
		Socket Socket = new Socket(User.getUserIP(), User.getUserPort());
		User.setUserSocket(Socket);
		return User;
	}

	public void OuvrirServer() {
		/*
		 * début de la boucle dans laquelle on crée un socket à chaque fois que
		 * quelqu'un se connecte
		 */
		
		while(true) {
	        try {
	  	      ServerSocket socketServeur = new ServerSocket();
		      this.Port = socketServeur.getLocalPort();
				Socket socketClient = socketServeur.accept();
				NewUserList
				setChanged();
				notifyObservers();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		/* fin de la boucle */
	}
	
	public int getPort() {
		return this.Port;
	}

}
