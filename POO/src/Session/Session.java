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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import communication.UDPListener;
import communication.UDPSender;
import messageGestion.History;
import messageGestion.Message;
import userGestion.LocalUser;
import userGestion.User;

public class Session implements ActionListener, ScrollPaneConstants{

	 private JFrame Frame;
	 private JPanel Panel;
	 
	 private JScrollPane historiquePane;
	 private JTextArea historiqueLabel;
	 private JButton sendMessage;
	 private JTextArea zoneTexte;
	 
	 
	 private User utilisateurDistant;
	 private LocalUser utilisateurLocal;
	 private History historique;
	 
	 

	public Session(LocalUser utilisateurLocal, String pseudoDistant, UDPListener udpl) throws IOException {
		this.utilisateurDistant = udpl.findUser(pseudoDistant);
		this.utilisateurLocal = utilisateurLocal;
		this.historique = new History(utilisateurDistant.getUserIP());
		createframe();
		
	}
	
	public Session() throws IOException {
		
		this.utilisateurDistant = new User("Michou"," dans_une_galaxie_lointaine");
		this.utilisateurLocal = new LocalUser("Clemouille la mouille");
		historique = new History(utilisateurDistant.getUserIP());
		createframe();
		
	}
	
	private void createframe(){
		
       //Create and set up the window.
       Frame = new JFrame(utilisateurDistant.getUserPseudo());
       
       //Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Frame.addWindowListener(new WindowAdapter()
       {
           public void windowClosing(WindowEvent e)
           {
               Frame.setVisible(false);
           }
       });
       
       Panel = new JPanel(new BorderLayout(2,2));

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

       historiqueLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
       
       Frame.getRootPane().setDefaultButton(sendMessage);
       Frame.getContentPane().add(Panel, BorderLayout.CENTER);
       Frame.pack();
       Frame.setVisible(true);
       		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Date D = new Date();
		Message newMsg = new Message(zoneTexte.getText(), D, utilisateurLocal, utilisateurDistant);
		try {
			historique.add_msg(newMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zoneTexte.setText("");
		refreshHistory();
		
	}
	
	private void refreshHistory() {
		try {
			historique = new History(utilisateurDistant.getUserIP());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		historiqueLabel.setText(historique.getHistory());
	}
	
	public static void main(String[] args) {
       //Schedule a job for the event-dispatching thread:
       //creating and showing this application's GUI.
       javax.swing.SwingUtilities.invokeLater(new Runnable() {
           public void run() {
        	   try {
				Session dframe = new Session();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
       });
   }
	

}
