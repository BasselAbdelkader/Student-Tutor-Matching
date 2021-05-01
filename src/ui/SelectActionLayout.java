package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;


import model.User;
/**
 * This is the layout for a select action or main menu window. 
 * This window is opened when the user first logs in. It then present sthe users with 3 options that fcan be selected by the user
 * 1. Create a new request
 * 2. Bid on student's request
 * 3. See my contracts
 * 
 * @author Andrew Pang
 *
 */
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
	
	/**
     * Instantiate the View Elements to be added to the Layout
     */
	@Override
	protected void initElements() {
		
		userLabel = new JLabel("Hello");
		
		newRequestBtn = new JButton("New Tutor Request");
		biddingPageBtn = new JButton("Bid on student request");
		seeContractBtn = new JButton("See my contracts");
	}
	
	/**
	 * Set the positions of the View elements to be added
	*/

	@Override
	protected void setElementBounds() {
		userLabel.setBounds(10, 10, 380, 30);
		newRequestBtn.setBounds(10, 50, 380, 50);
		biddingPageBtn.setBounds(10, 110, 380, 50);
		seeContractBtn.setBounds(10, 170, 380, 50);
	}

	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		container.add(userLabel);
        container.add(newRequestBtn);
        container.add(biddingPageBtn);
        container.add(seeContractBtn);
	}
	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		newRequestBtn.addActionListener(this);
		biddingPageBtn.addActionListener(this);
		seeContractBtn.addActionListener(this);
	}
	
	/**
	 * Initialize the elements properties
	 */

	@Override
	protected void init() {
		
	}
	
	/**
	 * Actions to be performed in the case of a user induced events
	 * @param e The action event
	 */
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
