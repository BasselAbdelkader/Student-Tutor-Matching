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

public class SelectActionLayout extends WindowLayout implements ActionListener {
	 
	private static final long serialVersionUID = 1L;
	
	//Instance Vars
	User currentUser;
	
	//Labels
	JLabel userLabel;
	
	//Buttons
	JButton newRequestBtn;
	JButton biddingPageBtn;
	JButton seeContractBtn;
	 
	public SelectActionLayout(User currentUser) {
		super();
		this.currentUser = currentUser;
		userLabel.setText("Hello " + currentUser.getGivenName() + ", what do you want to do today?");
		if(!currentUser.isTutor()) {
			biddingPageBtn.setEnabled(false);
		}
	}

	@Override
	protected void initElements() {
		
		userLabel = new JLabel("Hello");
		
		newRequestBtn = new JButton("New Tutor Request");
		biddingPageBtn = new JButton("Bid on student request");
		seeContractBtn = new JButton("See my contracts");
	}
	
	@Override
	protected void setElementBounds() {
		userLabel.setBounds(10, 10, 380, 30);
		newRequestBtn.setBounds(10, 50, 380, 50);
		biddingPageBtn.setBounds(10, 110, 380, 50);
		seeContractBtn.setBounds(10, 170, 380, 50);
	}


	@Override
	protected void addToContainer() {
		container.add(userLabel);
        container.add(newRequestBtn);
        container.add(biddingPageBtn);
        container.add(seeContractBtn);
	}

	@Override
	protected void bindActionListeners() {
		newRequestBtn.addActionListener(this);
		biddingPageBtn.addActionListener(this);
		seeContractBtn.addActionListener(this);
	}
	
	@Override
	protected void init() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newRequestBtn) {
			new NewRequestWindow(currentUser);
            dispose();
        }
		else if (e.getSource() == biddingPageBtn) {
			new OpenBidsWindow(currentUser);
            dispose();
        }
		else if (e.getSource() == seeContractBtn) {
			new SeeContractWindow(currentUser);
            dispose();
        }
	}

}
