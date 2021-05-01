package ui;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apiservices.BidsAPI;
import apiservices.ContractsAPI;
import model.Bid;
import model.Contract;
import model.User;

public class RequestLayout extends RefreshableLayout implements ActionListener, ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	
	//Instance Vars
	User currentUser;
	Bid request;
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
    
	public RequestLayout(User currentUser, Bid request){
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

	@Override
	protected void bindActionListeners() {
		refreshBtn.addActionListener(this);
		messageBtn.addActionListener(this);
		closeBidBtn.addActionListener(this);
		bidsList.addListSelectionListener(this);
	}

	@Override
	protected void init() {
		requestDetails.setEditable(false);
		bidDetails.setEditable(false);
		messageBtn.setEnabled(false);
	}
	
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
	
	@Override
	public void dispose() {
		checkTimeLimit.cancel();
		super.dispose();
	}
	
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
