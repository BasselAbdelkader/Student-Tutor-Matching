package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apiservices.BidsAPI;
import apiservices.ContractsAPI;
import model.Request;
import model.Contract;
import model.User;

/**
 * This is the layout for a request window. 
 * This window is opened after the user has created a new request and is waiting to see the bids on his/her request 
 * This window is also used for bidders to see other bidder's requests in open bidding
 * @author Andrew Pang
 *
 */
public class RequestLayout extends RefreshableLayout implements ActionListener, ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	
	//Instance Vars
	User currentUser;
	Request request;
	ArrayList<Contract> bids;
	Contract selectedContract;
	long dueTimeMillis;
	
	
	
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
    
    //Timers
    private Timer timer = new Timer();
    private TimerTask checkTimeLimit = new TimerTask() {  
	    @Override  
	    public void run() {  
	    	refresh(); 
			timeLimitReached(this);
	    };  
	};
    
	public RequestLayout(User currentUser, Request request){
		super();
		
		this.currentUser = currentUser;
		this.request = request;
		boolean userIsIntiator = currentUser.getId().contentEquals(request.getInitiatorId());
		
		if(request.getType().contentEquals("closed")) {
			dueTimeMillis = Instant.parse(request.getDateCreated()).toEpochMilli() + (int) TimeUnit.DAYS.toMillis(7);	
			container.add(messageBtn);
		}
		else if(request.getType().contentEquals("open")) {
			dueTimeMillis = Instant.parse(request.getDateCreated()).toEpochMilli() + (int) TimeUnit.MINUTES.toMillis(30);
			if (userIsIntiator) {
				container.add(closeBidBtn);
			}
		}
		System.out.println(dueTimeMillis);
		timer.schedule(checkTimeLimit, new Date(dueTimeMillis)); 
		refresh();
	}
	
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


	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		refreshBtn.addActionListener(this);
		messageBtn.addActionListener(this);
		closeBidBtn.addActionListener(this);
		bidsList.addListSelectionListener(this);
	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		requestDetails.setEditable(false);
		bidDetails.setEditable(false);
		messageBtn.setEnabled(false);
	}
	
	/**
	 * Default actions to perform on an auto refresh call
	 */
	@Override
	protected void refresh() {
		try {
			request = BidsAPI.getInstance().getBid(request.getId()); //Update the request
			Contract contract = ContractsAPI.getInstance().getSignedContract(request);
			if( contract != null) {
				JOptionPane.showMessageDialog(this, "This request has been bought out.");
				if(contract.getFirstPartyId().contentEquals(currentUser.getId()) || contract.getSecondPartyId().contentEquals(currentUser.getId())) {
					new ViewContractWindow(currentUser,contract);
				}
				dispose();
				return;
			}
			
			requestDetails.setText(request.toString());
			bidListModel.clear();
			bids = ContractsAPI.getInstance().getUnsignedContracts(request);
			for(int i = 0; i < bids.size(); i++) {
				bidListModel.add(i,bids.get(i).getId());
			}
			bidDetails.setText("");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error getting request details. This request may have already ended.");
			dispose();
		}
	}
	
	/**
	 * Actions to be performed in the case of a user induced events on list views
	 * @param e The action event
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == bidsList) {
			String id = bidsList.getSelectedValue();
			if(id != null) {
				try {
					selectedContract = ContractsAPI.getInstance().getContract(id);
					bidDetails.setText(selectedContract.toString());
					messageBtn.setEnabled(true);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Unable to get bid details");
				}
			}
		}
	}

	/**
	 * Actions to be performed in the case of a user induced events
	 * @param e The action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == messageBtn) {
			new ViewContractWindow(currentUser,selectedContract);
		}
		else if(e.getSource() == closeBidBtn) {
        	try {
        		ContractsAPI.getInstance().signContract(request,selectedContract);
				new ViewContractWindow(currentUser,ContractsAPI.getInstance().getContract(selectedContract.getId()));
				dispose();
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Error buying out request");
				refresh();
			}
        }
		else if(e.getSource() == refreshBtn) {
    		refresh();
    	}
	}
	
	/**
	 * Default actions to perform before closing the window
	 */
	@Override
	public void dispose() {
		checkTimeLimit.cancel();
		super.dispose();
	}
	
	/**
	 * Actions to perform when the request time limit has been reached
	 * This method will close the request, delete it if there are no bids on it by the time limit is reacehd
	 * This method will also automatically choose the first bid for the requestor if there are bid but not yet closed 
	 * After that it closes the window and exits
	 * @param task The timer task
	 */
	private void timeLimitReached(TimerTask task) {
		try {
			ArrayList<Contract> contracts = ContractsAPI.getInstance().getUnsignedContracts(request);
    		if(contracts.size() <= 0) {
    			//no bids - delete the request and move on
    			ContractsAPI.getInstance().deleteUnsignedContracts(request);
    			BidsAPI.getInstance().deleteBid(request);
    			JOptionPane.showMessageDialog(this, "There are no bids for your request within time limit. Closing request.");
    		}else {
    			//select best bid
    			ContractsAPI.getInstance().signContract(request,contracts.get(0));
    			JOptionPane.showMessageDialog(this, "Your time limit is up. Best bidder was selected. Closing request");
    		}
			dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}  	
	}

	

}
