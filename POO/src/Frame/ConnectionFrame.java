package Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import communication.UDPListener;
import communication.UDPSender;
import userGestion.LocalUser;

public class ConnectionFrame implements ActionListener {
	
	 JFrame Frame;
	 JPanel Panel;
	 JLabel bonjour;
	 JButton sendPseudo;
	 JTextField newPseudo;
	 LocalUser lu;
	 public ChatWindow w ;
	 UDPListener udpl = new UDPListener(w);
	 UDPSender udps = new UDPSender(udpl.port);

	public ConnectionFrame() {

        //Create and set up the window.
        Frame = new JFrame("Connexion");
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Frame.setPreferredSize(new Dimension(600,400));

        //Create and set up the panel.
        Panel = new JPanel(new BorderLayout(2,2));
        
        newPseudo = new JTextField();
        newPseudo.setPreferredSize(new Dimension(400, 10));
        sendPseudo = new JButton("ok");
        bonjour = new JLabel("Bonjour ! Veuillez choisir un pseudo", SwingConstants.CENTER);
        bonjour.setPreferredSize(new Dimension(400, 50));
        
        sendPseudo.addActionListener(this);

        Panel.add(bonjour, BorderLayout.NORTH);
        Panel.add(newPseudo, BorderLayout.LINE_START);
        Panel.add(sendPseudo, BorderLayout.LINE_END);

        bonjour.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //Set the default button.
        Frame.getRootPane().setDefaultButton(sendPseudo);

        //Add the panel to the window.
        Frame.getContentPane().add(Panel, BorderLayout.CENTER);

        //Display the window.
        Frame.pack();
        Frame.setVisible(true);
        
        lu = new LocalUser("provisoire");
        udps.sendBroadcast("New provisoire "+lu.getUserIP());
	}

	
	public void actionPerformed(ActionEvent arg0) {
		String ps = newPseudo.getText() ;
		if (udpl.pseudoIsAvailable(ps) != 0) {
			LocalUser lu = new LocalUser(ps);
			System.out.println("On va envoyer un broadcast");
			udps.sendBroadcast("Hello " + ps + " " + lu.getUserIP());
			System.out.println("On a envoyé un broadcast");
			w = new ChatWindow(lu, udpl) ;
			Frame.setVisible(false);
		} else {
			bonjour.setText("Pseudo déjà utilisé");
			newPseudo.setText("");
		}
	}
	
	 
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	@SuppressWarnings("unused")
				ConnectionFrame dframe = new ConnectionFrame();
            }
        });
    }

}
