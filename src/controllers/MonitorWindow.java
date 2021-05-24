package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apiservices.ContractsAPI;
import model.Contract;
import model.User;
import ui.MonitorLayout;

public class MonitorWindow extends RefreshableController {
	
	MonitorLayout window;
	User currentUser;
	Contract selectedBid;
	
	public MonitorWindow(User currentUser) {
		super(new MonitorLayout(),"Bids Monitor",500,500);
		this.window = (MonitorLayout) super.window;
		this.currentUser = currentUser;
    	bindActionListeners();
        init(); 
	}

	@Override
	public void refresh() {

		try {
			DefaultListModel<Contract> model = window.getSubscribedBidsModel();
			model.clear();
			ArrayList<Contract> contracts = ContractsAPI.getInstance().getContractsForUser(currentUser);
			for(Contract c : contracts) {
				if(c.isSubscribed() && c.getDateSigned() == null && c.getFirstPartyId().contentEquals(currentUser.getId())) {
					model.add(model.getSize(), c);
				}
			}
		} catch (Exception e1) {
					e1.printStackTrace();
		}
	}

	@Override
	protected void bindActionListeners() {
		
		window.getSeeBidsBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewContractWindow(currentUser,selectedBid);
			}
		});
		
		window.getRefreshBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		window.getSubscribedBids().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				selectedBid = window.getSubscribedBids().getSelectedValue();
				if (selectedBid != null) {
					window.getBidDetails().setText(selectedBid.getContractDetails());
					window.getSeeBidsBtn().setEnabled(true);
				}
				
			}
			
		});
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		window.getSeeBidsBtn().setEnabled(false);
		window.getBidDetails().setEditable(false);
		refresh();
	}

}
