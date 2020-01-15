package Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import messageGestion.History;
import messageGestion.Message;
import userGestion.LocalUser;
import userGestion.User;

public class SessionTCP {

	public User utilisateurDistant;
	public LocalUser utilisateurLocal;
	public History historique;

	private SessionFrame SessionFrame;

	private BufferedReader inputStream;
	private PrintStream outputStream;
	private Socket socket;

	public SessionTCP(LocalUser utilisateurLocal, String pseudoDistant, Socket newSocket) throws IOException {
		this.socket = newSocket;
		utilisateurDistant = new User(pseudoDistant, socket.getInetAddress().toString(), socket.getPort());
		this.utilisateurLocal = utilisateurLocal;
		inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new PrintStream(socket.getOutputStream());
		SessionFrame = new SessionFrame();
		Thread Thread = new Thread(SessionFrame);
		Thread.start();
		run();

	}

	public SessionTCP() throws IOException {

		this.utilisateurDistant = new User("Michou", " dans_une_galaxie_lointaine");
		this.utilisateurLocal = new LocalUser("Clemouille la mouille");
		historique = new History(utilisateurDistant.getUserIP());
		SessionFrame = new SessionFrame();
		Thread Thread = new Thread(SessionFrame);
		Thread.start();
		run();

	}

	public void run() {
		String Message;
		while (true) {
			if ((Message = SessionFrame.getBuffer()) != null) {
				envoyerMessage(Message);
			}
			try {
				if ((Message = inputStream.readLine()) != null) {
					RecevoirMessage(Message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void RecevoirMessage(String msgStr) {
		Message message = new Message(msgStr, utilisateurDistant, utilisateurLocal);
		SessionFrame.updateHistory(message);
	}

	public void envoyerMessage(String newMsgStr) {
		Message newMsg = new Message(newMsgStr, utilisateurLocal, utilisateurDistant);
		SessionFrame.updateHistory(newMsg);
		outputStream.println(newMsgStr);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		try {
			System.out.println("42");
			SessionTCP dframe = new SessionTCP();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
