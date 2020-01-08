package Frame;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import communication.UDPListener;
import userGestion.LocalUser;
import userGestion.User;

public class ChatWindow {
	
	public DefaultListModel<String> model = new DefaultListModel<String>();
	public JList<String> list = new JList<String>(model);
	public LocalUser lu ;
	public UDPListener udpl ;
	
	public ChatWindow(LocalUser lu, UDPListener udpl) {
		this.lu = lu ;
		this.udpl = udpl;
		
		// Adding all active users to the GUI
		java.util.Iterator<User> itr = udpl.tabUsers.iterator();
		User element ;
	    while(itr.hasNext())
	    {
	    	element = itr.next();
	    	model.addElement(element.getUserPseudo());
	    }
		
		model.addElement("utilisateur1");
		model.addElement("utilisateur2");
		model.addElement("utilisateur3");
		model.addElement("utilisateur4");
		model.addElement("utilisateur5");
		model.addElement("utilisateur6");
		model.addElement("utilisateur7");
		
		JFrame f = new JFrame();
	    f.setTitle("Utilisateurs actifs");

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

	    JPanel leftPanel = new JPanel();

	    leftPanel.setLayout(new BorderLayout());

	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	    list.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	          if (e.getClickCount() == 2) {
	            int index = list.locationToIndex(e.getPoint());
	            Object item = model.getElementAt(index); // item corresponds to the Pseudo
	            										  // try to create a new object like :
	            										 // new SessionFrame(item)
	            if (index >= 0) {
	            	/*model.remove(index);*/
	            	JFrame nf = new JFrame((String)(item));
	            	nf.setVisible(true);
	            	nf.setSize(500, 500);
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
	
	/*public static void main (String[] args) {
		ChatWindow w = new ChatWindow() ;
	}*/
	
}
