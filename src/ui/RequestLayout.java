package ui;


import javax.swing.DefaultListModel;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 * This is the layout for a request window. 
 * This window is opened after the user has created a new request and is waiting to see the bids on his/her request 
 * This window is also used for bidders to see other bidder's requests in open bidding
 * @author Andrew Pang
 *
 */
public class RequestLayout extends WindowLayout{
	
	private static final long serialVersionUID = 1L;

	//Labels
	JLabel requestDetailsLabel;
	JLabel bidListLabel;
	JLabel bidDetailsLabel;
	
	//Outputs
	JTextArea requestDetails;
	JTextArea bidDetails;
	
	//Lists
    DefaultListModel<String> bidListModel;
    JList<String> bidsList;  
    
    //Buttons
    JButton refreshBtn;
    JButton messageBtn;
    JButton closeBidBtn; 
    
	/**
     * Instantiate the View Elements to be added to the Layout
     */
	@Override
	protected void initElements() {

		//Labels
		requestDetailsLabel = new JLabel("Details of the request.");
		bidListLabel = new JLabel("Current bids for the request");
		bidDetailsLabel = new JLabel("Bid details");
		
		//Outputs
		requestDetails = new JTextArea();
		bidDetails = new JTextArea();
		
		//Lists
	    bidListModel = new DefaultListModel<String>();
	    bidsList = new JList<String>(bidListModel);  
	    
	    //Buttons
	    refreshBtn = new JButton("Refresh");
	    messageBtn = new JButton("Messages");
	    closeBidBtn = new JButton("Close this bid");
	}
	
	/**
	 * Set the positions of the View elements to be added
	*/
	@Override
	protected void setElementBounds() {
		requestDetailsLabel.setBounds(10, 10, 300, 30);
		requestDetails.setBounds(10, 50, 470, 150);
		bidListLabel.setBounds(10, 210, 300, 30);
		bidsList.setBounds(10, 250, 470, 150);
		bidDetailsLabel.setBounds(10, 410, 300, 30);
		bidDetails.setBounds(10, 450, 470, 150);
		refreshBtn.setBounds(10, 610, 150, 30);
		messageBtn.setBounds(170, 610, 150, 30);
		closeBidBtn.setBounds(330, 610, 150, 30);
	}
	
	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		container.add(requestDetailsLabel);
		container.add(requestDetails);
		container.add(bidListLabel);
		container.add(bidsList);
		container.add(bidDetailsLabel);
		container.add(bidDetails);
		container.add(refreshBtn);

	}


	public JTextArea getRequestDetails() {
		return requestDetails;
	}

	public JTextArea getBidDetails() {
		return bidDetails;
	}

	public DefaultListModel<String> getBidListModel() {
		return bidListModel;
	}

	public JList<String> getBidsList() {
		return bidsList;
	}

	public JButton getRefreshBtn() {
		return refreshBtn;
	}

	public JButton getMessageBtn() {
		return messageBtn;
	}

	public JButton getCloseBidBtn() {
		return closeBidBtn;
	}



}
