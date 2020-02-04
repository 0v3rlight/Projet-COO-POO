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

public class PseudoChanging implements ActionListener {
	JFrame Frame;
	 JPanel Panel;
	 JLabel bonjour;
	 JButton sendPseudo;
	 JTextField newPseudo;
	 LocalUser lu = new LocalUser("provisoire");
	 
	 UDPListener udpl ;
	 UDPSender udps ;

	public PseudoChanging(LocalUser lu, UDPListener udpl, UDPSender udps) {
		this.lu = lu ;
		this.udpl = udpl ;
		this.udps = udps ;
		
       //Create and set up the window.
       Frame = new JFrame("Changement de pseudo");
       Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //Frame.setPreferredSize(new Dimension(600,400));

       //Create and set up the panel.
       Panel = new JPanel(new BorderLayout(2,2));
       
       newPseudo = new JTextField();
       newPseudo.setPreferredSize(new Dimension(400, 10));
       sendPseudo = new JButton("ok");
       bonjour = new JLabel("Veuillez choisir un nouveau pseudo", SwingConstants.CENTER);
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
       
       udps.sendBroadcast("New provisoire "+lu.getUserIP());
	}

	
	public void actionPerformed(ActionEvent arg0) {
		String ps = newPseudo.getText() ;
		if (udpl.pseudoIsAvailable(ps) != 0) {
			System.out.println("On va envoyer un broadcast");
			udps.sendBroadcast("Bye " + lu.getUserPseudo() + " " + lu.getUserIP());
			lu.SetPseudo(ps);
			udps.sendBroadcast("Hello " + ps + " " + lu.getUserIP());
			System.out.println("On a envoyé un broadcast");
			Frame.setVisible(false);
		} else {
			bonjour.setText("Pseudo déjà utilisé");
			newPseudo.setText("");
		}
	}
}
