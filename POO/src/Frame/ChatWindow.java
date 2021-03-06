package Frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Session.Session;
import communication.UDPListener;
import communication.UDPListenerMessage;
import communication.UDPSender;
import userGestion.LocalUser;
import userGestion.User;

public class ChatWindow {
	
	public DefaultListModel<String> model = new DefaultListModel<String>();
	public JList<String> list = new JList<String>(model);
	public LocalUser lu ;
	public JFrame f = new JFrame();
	public UDPListener udpl ;
	public UDPSender udps ;
	public UDPListenerMessage udplm = new UDPListenerMessage();
	//public UDPListener udpl ;
	
	public ChatWindow(LocalUser lu) {
		this.lu = lu ;
		
	    f.setTitle("Utilisateurs actifs");
	    
	    f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                udps.sendBroadcast("Bye "+ lu.getUserPseudo() + " " + lu.getUserIP());
            }
        });

	    JPanel panel = new JPanel();
	    JButton change_pseudo = new JButton("Changer de Pseudo");
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	    change_pseudo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PseudoChanging pc = new PseudoChanging(lu,udpl,udps) ;
			}
		});

	    JPanel leftPanel = new JPanel();
	    leftPanel.setLayout(new BorderLayout());
	    leftPanel.add(change_pseudo,BorderLayout.SOUTH);

	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	    list.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	          if (e.getClickCount() == 2) {
	            int index = list.locationToIndex(e.getPoint());
	            Object item = model.getElementAt(index); // item corresponds to the Pseudo
	            										  // try to create a new object like :
	            										 // new SessionFrame(item)
	            String pseudo_d = (String)item ;
	            if (index >= 0) {
	            	/*model.remove(index);*/
	            	try {
						Session s = new Session(lu,pseudo_d,udpl);
						udplm.addSession(s);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	          }
	        }
	      });
	      leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	      leftPanel.add(new JScrollPane(list));

	      panel.add(leftPanel);

	      f.add(panel);

	      f.setSize(500, 500);
	      f.setLocationRelativeTo(null);
	      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      f.setVisible(true);

	}
	
	public void setUdpl(UDPListener udpl) {
		this.udpl = udpl ;
	}
	
	public void setUdps(UDPSender udps) {
		this.udps = udps ;
	}
	
	/*public static void main (String[] args) {
		ChatWindow w = new ChatWindow() ;
	}*/
	
}
