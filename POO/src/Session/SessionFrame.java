package Session;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import messageGestion.History;
import messageGestion.Message;
import userGestion.LocalUser;
import userGestion.User;

public class SessionFrame implements ActionListener, ScrollPaneConstants, Runnable {

	private JFrame Frame;
	private JPanel Panel;

	private JScrollPane historiquePane;
	private JTextArea historiqueLabel;
	private JButton sendMessage;
	private JTextArea zoneTexte;

	private User utilisateurDistant;
	private LocalUser utilisateurLocal;
	private History historique;
	private String MessageBuffer;

	public SessionFrame(User utilisateurDistant, LocalUser utilisateurLocal) throws IOException {
		this.utilisateurDistant = utilisateurDistant;
		this.utilisateurLocal = utilisateurLocal;
		historique = new History(utilisateurDistant.getUserIP());
	}

	public SessionFrame() throws IOException {
		this.utilisateurDistant = new User("Michou", " dans_une_galaxie_lointaine");
		this.utilisateurLocal = new LocalUser("Clemouille la mouille");
		try {
			historique = new History(utilisateurDistant.getUserIP());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createframe();
		Thread Thread = new Thread(this);
		Thread.start();

	}

	public void createframe() {

		// Create and set up the window.
		Frame = new JFrame("Conversation");
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel = new JPanel(new BorderLayout(2, 2));

		zoneTexte = new JTextArea();
		zoneTexte.setPreferredSize(new Dimension(400, 10));
		sendMessage = new JButton("ok");

		historiqueLabel = new JTextArea(historique.getHistory());
		historiqueLabel.setBackground(Color.white);
		historiqueLabel.setOpaque(true);
		historiqueLabel.setEditable(false);
		historiquePane = new JScrollPane(historiqueLabel);
		historiquePane.setPreferredSize(new Dimension(400, 200));

		sendMessage.addActionListener(this);

		Panel.add(historiquePane, BorderLayout.NORTH);
		Panel.add(zoneTexte, BorderLayout.LINE_START);
		Panel.add(sendMessage, BorderLayout.LINE_END);

		historiqueLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		Frame.getRootPane().setDefaultButton(sendMessage);
		Frame.getContentPane().add(Panel, BorderLayout.CENTER);
		Frame.pack();
		Frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent arg0) {
		NouveauMessage();
	}

	private void updateHistory() {
		try {
			historique = new History(utilisateurDistant.getUserIP());
			historiqueLabel.setText(historique.getHistory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateHistory(Message Message) {
		try {
			historique = new History(utilisateurDistant.getUserIP());
			historique.add_msg(Message);
			historiqueLabel.setText(historique.getHistory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getBuffer() {
		String message =  MessageBuffer;
		MessageBuffer = null;
		return message;
	}
	
	public String NouveauMessage() {
		String contenu = zoneTexte.getText();
		MessageBuffer = contenu;
		zoneTexte.setText(null);
		return contenu;
	}

	@Override
	public void run() {
	}

}
