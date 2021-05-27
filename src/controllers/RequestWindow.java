package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import adaptors.ContractsAPI;
import adaptors.RequestAPI;
import model.Contract;
import model.Request;
import model.User;
import ui.RequestLayout;
/**
 * This is boot-strapper class for the Request Layout
 * it specifies the properties of the request window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class RequestWindow extends RefreshableController{
	
	private User currentUser;
	private Request request;
	private ArrayList<Contract> bids;
	private Contract selectedContract;
	private long dueTimeMillis;
	
	private Timer timer = new Timer();
    private TimerTask checkTimeLimit = new TimerTask() {  
	    @Override  
	    public void run() {  
	    	refresh(); 
			timeLimitReached(this);
	    };  
	};
	
	private RequestLayout window;
	
	RequestWindow(User currentUser, Request request){
		super(new RequestLayout(),"Request " + request.getId(), 510,700);
		this.window = (RequestLayout) super.window;
		this.currentUser = currentUser;
		this.request = request;
    	bindActionListeners();
        init(); 
        refresh();
	}

	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		
		window.getMessageBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewContractWindow(currentUser,selectedContract);
			}
		});
		

		
		window.getCloseBidBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					selectedContract.sign();
	        		ContractsAPI.getInstance().signContract(request,selectedContract);
					new ViewContractWindow(currentUser,ContractsAPI.getInstance().getContract(selectedContract.getId()));
					closeWindow();
				}catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(window, "Error buying out request");
					refresh();
				}
			}
		});
		
		window.getRefreshBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		window.getBidsList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String id = window.getBidsList().getSelectedValue();
				if(id != null) {
					try {
						selectedContract = ContractsAPI.getInstance().getContract(id);
						window.getBidDetails().setText(selectedContract.getContractDetails());
						window.getMessageBtn().setEnabled(true);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(window, "Unable to get bid details");
					}
				}
			}
		});


	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		
		window.getRequestDetails().setEditable(false);
		window.getBidDetails().setEditable(false);
		window.getMessageBtn().setEnabled(false);
		
		boolean userIsIntiator = currentUser.getId().contentEquals(request.getInitiatorId());
		
		if(request.getType().contentEquals("closed")) {
			dueTimeMillis = Instant.parse(request.getDateCreated()).toEpochMilli() + (int) TimeUnit.DAYS.toMillis(7);	
			window.container.add(window.getMessageBtn());
		}
		else if(request.getType().contentEquals("open")) {
			dueTimeMillis = Instant.parse(request.getDateCreated()).toEpochMilli() + (int) TimeUnit.MINUTES.toMillis(30);
			if (userIsIntiator) {
				window.container.add(window.getCloseBidBtn());
			}
		}
		System.out.println(dueTimeMillis);
		timer.schedule(checkTimeLimit, new Date(dueTimeMillis)); 
		refresh();
	}
	
	/**
	 * Default actions to perform on an auto refresh call
	 */
	@Override
	public void refresh() {
		try {
			request = RequestAPI.getInstance().getRequest(request.getId()); //Update the request
			Contract contract = ContractsAPI.getInstance().getSignedContract(request);
			if( contract != null) {
				JOptionPane.showMessageDialog(window, "This request has been bought out.");
				if(contract.getFirstPartyId().contentEquals(currentUser.getId()) || contract.getSecondPartyId().contentEquals(currentUser.getId())) {
					new ViewContractWindow(currentUser,contract);
				}
				closeWindow();
				return;
			}
			
			window.getRequestDetails().setText(request.toString());
			window.getBidListModel().clear();
			bids = ContractsAPI.getInstance().getUnsignedContracts(request);
			for(int i = 0; i < bids.size(); i++) {
				window.getBidListModel().add(i,bids.get(i).getId());
			}
			window.getBidDetails().setText("");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(window, "Error getting request details. This request may have already ended.");
			closeWindow();
		}
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
    			RequestAPI.getInstance().deleteRequest(request);
    			JOptionPane.showMessageDialog(window, "There are no bids for your request within time limit. Closing request.");
    		}else {
    			//select best bid
    			contracts.get(0).sign();
    			ContractsAPI.getInstance().signContract(request,contracts.get(0));
    			JOptionPane.showMessageDialog(window, "Your time limit is up. Best bidder was selected. Closing request");
    		}
			closeWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}  	
	}

	/**
	 * Default actions to perform before closing the window
	 */
	@Override
	public void closeWindow() {
		checkTimeLimit.cancel();
		super.closeWindow();
	}
}
