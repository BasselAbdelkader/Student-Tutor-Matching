package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apiservices.ContractsAPI;
import model.Contract;
import model.User;

public class MonitorLayout extends RefreshableLayout implements ActionListener,ListSelectionListener{

	private static final long serialVersionUID = 1L;
	User currentUser;
	Contract selectedBid;
	
	JLabel subscribedBidsLabel, bidDetailsLabel;
	DefaultListModel<Contract> subscribedBidsModel;
	JList<Contract> subscribedBids;
	JTextArea bidDetails;
	JButton refreshBtn, seeBidsBtn, updateBidBtn;
	
	public MonitorLayout(User currentUser) {
		// TODO Auto-generated constructor stub
		this.currentUser = currentUser;
		refresh();
	}	

	@Override
	protected void initElements() {
		// TODO Auto-generated method stub
		subscribedBidsLabel = new JLabel("Subscribed Bids");
		bidDetailsLabel = new JLabel("Bid Details");
		
		subscribedBidsModel = new DefaultListModel<Contract>();
		subscribedBids = new JList<Contract>(subscribedBidsModel);
		
		bidDetails = new JTextArea();
		
		seeBidsBtn = new JButton("See Other Bidders");
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

	@Override
	protected void bindActionListeners() {
		// TODO Auto-generated method stub
		refreshBtn.addActionListener(this);
		seeBidsBtn.addActionListener(this);
		subscribedBids.addListSelectionListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		seeBidsBtn.setEnabled(false);
		bidDetails.setEditable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == seeBidsBtn) {
			new ViewContractWindow(currentUser,selectedBid);
		}else if(e.getSource() == refreshBtn) {
			refresh();
		}
	}

	@Override
	protected void refresh() {
		// TODO Auto-generated method stub
		try {
			subscribedBidsModel.clear();
			ArrayList<Contract> contracts = ContractsAPI.getInstance().getContractsForUser(currentUser);
			for(Contract c : contracts) {
				if(c.isSubscribed() && c.getFirstPartyId().contentEquals(currentUser.getId())) {
					subscribedBidsModel.add(subscribedBidsModel.getSize(), c);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == subscribedBids && subscribedBids.getSelectedIndex() >= 0) {
			selectedBid = subscribedBids.getSelectedValue();
			bidDetails.setText(selectedBid.getContractDetails());
			seeBidsBtn.setEnabled(true);
		}
	}

}
