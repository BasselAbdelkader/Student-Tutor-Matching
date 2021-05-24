package ui;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import model.Contract;


public class MonitorLayout extends WindowLayout{

	private static final long serialVersionUID = 1L;

	JLabel subscribedBidsLabel, bidDetailsLabel;
	DefaultListModel<Contract> subscribedBidsModel;
	
	JList<Contract> subscribedBids;
	JTextArea bidDetails;
	JButton refreshBtn, seeBidsBtn, updateBidBtn;
	
	public MonitorLayout() {
		super();
	}	

	@Override
	protected void initElements() {
		// TODO Auto-generated method stub
		subscribedBidsLabel = new JLabel("Subscribed Bids");
		bidDetailsLabel = new JLabel("Bid Details");
		
		subscribedBidsModel = new DefaultListModel<Contract>();
		subscribedBids = new JList<Contract>(subscribedBidsModel);
		bidDetails = new JTextArea();
		
		seeBidsBtn = new JButton("See Bid");
		refreshBtn = new JButton("Refresh");
		
	}

	@Override
	protected void setElementBounds() {
		// TODO Auto-generated method stub
		subscribedBidsLabel.setBounds(10, 10, 200, 30);
		subscribedBids.setBounds(10, 50, 460, 150);
		bidDetailsLabel.setBounds(10, 210, 200, 30);
		bidDetails.setBounds(10, 250, 460, 150);
		refreshBtn.setBounds(10, 410, 230, 30);
		seeBidsBtn.setBounds(240, 410, 230, 30);
	}

	@Override
	protected void addToContainer() {
		// TODO Auto-generated method stub
		container.add(subscribedBidsLabel);
		container.add(subscribedBids);
		container.add(bidDetailsLabel);
		container.add(bidDetails);
		container.add(refreshBtn);
		container.add(seeBidsBtn);
	}


	public DefaultListModel<Contract> getSubscribedBidsModel() {
		return subscribedBidsModel;
	}

	public JTextArea getBidDetails() {
		return bidDetails;
	}

	public JButton getRefreshBtn() {
		return refreshBtn;
	}

	public JButton getSeeBidsBtn() {
		return seeBidsBtn;
	}

	public JButton getUpdateBidBtn() {
		return updateBidBtn;
	}

	public JList<Contract> getSubscribedBids() {
		return subscribedBids;
	}
	
}
