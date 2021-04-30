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

public class RequestLayout extends JFrame implements ActionListener, ListSelectionListener{
	
	User currentUser = null;
	Bid request = null;
	ArrayList<Contract> bids = null;
	Contract selectedContract = null;
	
	Container container = getContentPane();
	JLabel requestDetailsLabel = new JLabel("Details of the request.");
	JLabel bidListLabel = new JLabel("Current bids for the request");
	JLabel bidDetailsLabel = new JLabel("Bid details");
	
	JTextArea requestDetails = new JTextArea();
    DefaultListModel<String> bidListModel = new DefaultListModel<String>();
    JList<String> bidsList = new JList<String>(bidListModel);  
    JTextArea bidDetails = new JTextArea();
    
    JButton refreshBtn = new JButton("Refresh");
    JButton messageBtn = new JButton("Messages");
    JButton closeBidBtn = new JButton("Close this bid");
    
    Timer t = new Timer();
    TimerTask tt = new TimerTask() {  
	    @Override  
	    public void run() {  
	    	refresh(); 
	    };  
	};
    TimerTask tt2 = new TimerTask() {  
	    @Override  
	    public void run() {  
	    	refresh(); 
			timeLimitReached(this);
	    };  
	};;
    
    
	public RequestLayout(User currentUser, Bid request){
		this.currentUser = currentUser;
		this.request = request;
		
		
		container.setLayout(null);
		
		requestDetails.setEditable(false);
		bidDetails.setEditable(false);
		
		requestDetailsLabel.setBounds(10, 10, 300, 30);
		requestDetails.setBounds(10, 50, 470, 150);
		bidListLabel.setBounds(10, 210, 300, 30);
		bidsList.setBounds(10, 250, 470, 150);
		bidDetailsLabel.setBounds(10, 410, 300, 30);
		bidDetails.setBounds(10, 450, 470, 150);
		refreshBtn.setBounds(10, 610, 150, 30);
		messageBtn.setBounds(170, 610, 150, 30);
		closeBidBtn.setBounds(330, 610, 150, 30);
		
		refreshBtn.addActionListener(this);
		messageBtn.addActionListener(this);
		closeBidBtn.addActionListener(this);
		bidsList.addListSelectionListener(this);
		
		
		container.add(requestDetailsLabel);
		container.add(requestDetails);
		container.add(bidListLabel);
		container.add(bidsList);
		container.add(bidDetailsLabel);
		container.add(bidDetails);
		container.add(refreshBtn);

		//TODO: Add condition, if currrentUser != requestor, disable closeBidBtn and messageBtn
		if (currentUser.getId().contentEquals(request.getInitiatorId())) {
			if(request.getType().contentEquals("closed")) {
				long total = Instant.parse(request.getDateCreated()).toEpochMilli() + TimeUnit.DAYS.toMillis(7);				
				t.schedule(tt2, new Date(total)); 
				messageBtn.setEnabled(false);
				container.add(messageBtn);
			}
			else if(request.getType().contentEquals("open")) {
				container.add(closeBidBtn);
				t.schedule(tt2, new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30))); 
			}
			
		}
		
		//Run refresh every 30 seconds
		t.schedule(tt, new Date(), 30000); 

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == bidsList) {
			String id = bidsList.getSelectedValue();
			if(id != null) {
				try {
					selectedContract = Application.contracts.getContract(id);
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
			new MessagesWindow(currentUser,request.getId(),selectedContract.getId());
		}else if(e.getSource() == closeBidBtn) {
        	try {
        		Application.contracts.signContract(request,selectedContract);
				Application.bids.closeBid(request);
				JOptionPane.showMessageDialog(this, "Bid closed");
				dispose();
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Error buying out request");
			}
        }else if(e.getSource() == refreshBtn) {
    		refresh();
    	}
		
	}

	private void refresh() {
		try {
			
			request = Application.bids.getBid(request.getId()); //Update the request
			if(Application.contracts.getSignedContract(request) != null) {
				JOptionPane.showMessageDialog(this, "This request has been bought out. Check your contracts.");
				dispose();
			}
			
			requestDetails.setText(request.toString());
			
			bidListModel.clear();
			bids = Application.contracts.getUnsignedContracts(request);
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
	
	private void timeLimitReached(TimerTask task) {
		try {
			ArrayList<Contract> contracts = Application.contracts.getUnsignedContracts(request);
    		if(contracts.size() <= 0) {
    			//no bids - delete the request and move on
    			Application.bids.deleteBid(request);
    			JOptionPane.showMessageDialog(this, "There are no bids for your request within time limit. Closing request.");
    		}else {
    			//select best bid
    			Application.contracts.signContract(request,contracts.get(0));
    			JOptionPane.showMessageDialog(this, "Your time limit is up. Best bidder was selected. Closing request");
    		}
			dispose();
			task.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}  	
	}
	
	public void dispose() {
		tt.cancel();
		tt2.cancel();
		super.dispose();
	}
}
