package ui;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

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
public class SelectActionLayout extends WindowLayout {
	 
	private static final long serialVersionUID = 1L;
	
	//Labels
	JLabel userLabel;
	JLabel notificationLabel;
	
	//Buttons
	JButton newRequestBtn;
	JButton biddingPageBtn;
	JButton seeContractBtn;
	JButton bidsMonitorBtn;
	 
	DefaultListModel<String> notificationModel;
	JList<String> notificationList;
	
	/**
     * Instantiate the View Elements to be added to the Layout
     */
	@Override
	protected void initElements() {
		
		userLabel = new JLabel("Hello");
		notificationLabel = new JLabel("Notfications");
		newRequestBtn = new JButton("New Tutor Request");
		biddingPageBtn = new JButton("Bid on student request");
		seeContractBtn = new JButton("See my contracts");
		bidsMonitorBtn = new JButton("Bids Monitoring Dashboard");
		notificationModel = new DefaultListModel<String>();
		notificationList = new JList<String>(notificationModel);
	}
	
	/**
	 * Set the positions of the View elements to be added
	*/

	@Override
	protected void setElementBounds() {
		userLabel.setBounds(10, 10, 380, 30);
		notificationLabel.setBounds(400, 10, 380, 30);
		newRequestBtn.setBounds(10, 50, 380, 50);
		biddingPageBtn.setBounds(10, 110, 380, 50);
		bidsMonitorBtn.setBounds(10, 170, 380, 50);
		seeContractBtn.setBounds(10, 230, 380, 50);
		notificationList.setBounds(400, 50,380,230);
		
	}

	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		container.add(userLabel);
		container.add(notificationLabel);
		container.add(notificationList);
        container.add(newRequestBtn);
        container.add(biddingPageBtn);
        container.add(bidsMonitorBtn);
        container.add(seeContractBtn);
	}
	


	public JLabel getUserLabel() {
		return userLabel;
	}

	public JButton getNewRequestBtn() {
		return newRequestBtn;
	}

	public JButton getBiddingPageBtn() {
		return biddingPageBtn;
	}

	public JButton getSeeContractBtn() {
		return seeContractBtn;
	}

	public JButton getBidsMonitorBtn() {
		return bidsMonitorBtn;
	}

	public DefaultListModel<String> getNotificationModel() {
		return notificationModel;
	}

}
