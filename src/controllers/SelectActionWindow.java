package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import apiservices.ContractsAPI;
import model.Contract;
import model.User;
import ui.SelectActionLayout;
/**
 * This is boot-strapper class for the Select Action Layout 
 * it specifies the properties of the select action window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class SelectActionWindow extends WindowController{
	
	SelectActionLayout window;
	User currentUser;
	
	SelectActionWindow(User currentUser){
		super(new SelectActionLayout(),"Welcome Back", 820, 330);
		this.window = (SelectActionLayout) super.window;
		this.currentUser = currentUser;
    	bindActionListeners();
        init();        
	}
	
	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {

		window.getNewRequestBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewRequestWindow(currentUser);
	            closeWindow();
			}
		});
		
		window.getBiddingPageBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new OpenBidsWindow(currentUser);
	            closeWindow();
			}
		});
		
		window.getBidsMonitorBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MonitorWindow(currentUser);
	            closeWindow();
			}
		});
		
		
		window.getSeeContractBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SeeContractWindow(currentUser);
	            closeWindow();
			}
		});
		
	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		window.getUserLabel().setText("Hello " + currentUser.getGivenName() + ", what do you want to do today?");
		if(!currentUser.isTutor()) {
			window.getBiddingPageBtn().setEnabled(false);
			window.getBidsMonitorBtn().setEnabled(false);
		}
		try {
			ArrayList<Contract> contracts = ContractsAPI.getInstance().getContractsForUser(currentUser);
			long now = System.currentTimeMillis();
			for(Contract c : contracts) {
				long expiry = Instant.parse(c.getExpiryDate()).toEpochMilli();
				long diff = expiry - now ;
				if(c.getDateSigned() != null && diff > 0 && diff < TimeUnit.DAYS.toMillis(30) ) {
					window.getNotificationModel().add(window.getNotificationModel().getSize(), "Contract : " + c.toString() + " will expire in less than a month!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
