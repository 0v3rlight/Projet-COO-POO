package Session;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
//import javax.swing.filefileToSend.FileNameExtensionFilter;

import communication.UDPListener;
import communication.UDPListenerMessage;
import communication.UDPSender;
import communication.UDPSenderMessage;
import messageGestion.History;
import messageGestion.Message;
import userGestion.LocalUser;
import userGestion.User;

public class Session extends Thread implements ActionListener, ScrollPaneConstants {

	private JFrame Frame;
	private JPanel Panel;

	private JScrollPane historiquePane;
	private JTextArea historiqueLabel;
	private JButton sendMessage;
	private JButton sendFile;
	private JTextArea zoneTexte;

	public User utilisateurDistant;
	public LocalUser utilisateurLocal;
	public History historique;

	public UDPSenderMessage udpsm;

	public Session(LocalUser utilisateurLocal, String pseudoDistant, UDPListener udpl) throws IOException {
		this.utilisateurDistant = udpl.findUser(pseudoDistant);
		this.utilisateurLocal = utilisateurLocal;
		this.historique = new History(utilisateurLocal, utilisateurDistant.getUserIP());
		this.udpsm = new UDPSenderMessage(this.utilisateurDistant.getUserIP());
		start();
	}

	public Session() throws IOException {

		this.utilisateurDistant = new User("Michou", " dans_une_galaxie_lointaine");
		this.utilisateurLocal = new LocalUser("Clemouille la mouille");
		historique = new History(utilisateurLocal, utilisateurDistant.getUserIP());
		createframe();

	}

	private void createframe() {

		// Create and set up the window.
		Frame = new JFrame(utilisateurDistant.getUserPseudo());

		// Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Frame.setVisible(false);
			}
		});

		Panel = new JPanel(new BorderLayout(2, 2));

		zoneTexte = new JTextArea();
		sendMessage = new JButton("<html><center>" + "Send<br>Text" + "</center></html>");
		sendFile = new JButton("<html><center>" + "Send<br>File" + "</center></html>");

		historiqueLabel = new JTextArea(historique.getHistory());
		historiqueLabel.setBackground(Color.white);
		historiqueLabel.setOpaque(true);
		historiqueLabel.setEditable(false);
		historiquePane = new JScrollPane(historiqueLabel);
		historiqueLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		historiquePane.setPreferredSize(new Dimension(800, 400));
		zoneTexte.setPreferredSize(new Dimension(700, 0));
		sendMessage.setPreferredSize(new Dimension(80, 80));
		sendFile.setPreferredSize(new Dimension(80, 80));

		sendMessage.addActionListener(this);
		sendFile.addActionListener(this);

		Panel.add(historiquePane, BorderLayout.NORTH);
		Panel.add(zoneTexte, BorderLayout.WEST);
		Panel.add(sendMessage, BorderLayout.CENTER);
		Panel.add(sendFile, BorderLayout.EAST);

		Frame.getRootPane().setDefaultButton(sendMessage);
		Frame.getContentPane().add(Panel, BorderLayout.CENTER);
		Frame.pack();
		Frame.setVisible(true);
		Frame.setResizable(false);
		historiquePane.getVerticalScrollBar().setValue(historiquePane.getVerticalScrollBar().getMaximum());

	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == sendMessage) {
			String contenu = zoneTexte.getText();
			Message newMsg = new Message(contenu, utilisateurLocal, utilisateurDistant);
			try {
				udpsm.send("Message " + contenu);
				historique.add_msg(newMsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				historiquePane.getVerticalScrollBar().setValue(0);
			}
			zoneTexte.setText("");
			refreshHistory();
		}
		if (arg0.getSource() == sendFile) {
	        JFileChooser fileToSend = new JFileChooser();
	        int returnVal = fileToSend.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	            System.out.println("You chose to open this file: " +
	                    fileToSend.getSelectedFile().getName());
	        }
		}
		

	}

	public void refreshHistory() {
		try {
			historique = new History(utilisateurLocal, utilisateurDistant.getUserIP());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		historiqueLabel.setText(historique.getHistory());
		historiquePane.getVerticalScrollBar().setValue(historiquePane.getVerticalScrollBar().getMaximum());
	}

	public void run() {
		/*
		 * //Schedule a job for the event-dispatching thread: //creating and showing
		 * this application's GUI. javax.swing.SwingUtilities.invokeLater(new Runnable()
		 * { public void run() { try { Session dframe = new Session(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * });
		 */
		createframe();
	}

}
