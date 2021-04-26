import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SelectActionLayout extends JFrame implements ActionListener {
	 
	Container container = getContentPane();
	 JLabel userLabel = new JLabel("Hello");
	 JButton newRequestBtn = new JButton("New Tutor Request");
	 JButton biddingPageBtn = new JButton("Bid on student request");
	 JButton seeContractBtn = new JButton("See my contracts");
	 User currentUser = null;
	 
	 
	public SelectActionLayout(User currentUser) {
		this.currentUser = currentUser;
		userLabel.setText("Hello " + currentUser.getGivenName() + ", what do you want to do today?");
		if(!currentUser.isTutor()) {
			biddingPageBtn.setEnabled(false);
		}
		container.setLayout(null);
		
		userLabel.setBounds(10, 10, 380, 30);
		newRequestBtn.setBounds(10, 50, 380, 50);
		biddingPageBtn.setBounds(10, 110, 380, 50);
		seeContractBtn.setBounds(10, 170, 380, 50);

		newRequestBtn.addActionListener(this);
		biddingPageBtn.addActionListener(this);
		seeContractBtn.addActionListener(this);
		
		container.add(userLabel);
        container.add(newRequestBtn);
        container.add(biddingPageBtn);
        container.add(seeContractBtn);

	}
	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == newRequestBtn) {
			new NewRequestWindow(currentUser);
            dispose();
        }else if (e.getSource() == biddingPageBtn) {
			new OpenBidsWindow(currentUser);
            dispose();
        }
	}

}
