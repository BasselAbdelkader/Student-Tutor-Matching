package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import adaptors.ContractsAPI;
import model.Contract;
import model.User;
import ui.SeeContractsLayout;
/**
 * This is boot-strapper class for the See Contracts layout
 * it specifies the properties of the see contracts window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class SeeContractWindow extends RefreshableController{
	
	User currentUser;
	Contract selectedContract;
	
	SeeContractsLayout window;
	
	SeeContractWindow(User currentUser){
		super(new SeeContractsLayout(),"See All Bids and Contracts", 440,700);
		this.window = (SeeContractsLayout) super.window;
		this.currentUser = currentUser;
	    bindActionListeners();
	    init(); 
	    refresh();
	     
	}
	
	/**
	 * Default actions to perform on an auto refresh call
	 */
	@Override
	public void refresh() {
		try {
			window.getViewContractBtn().setEnabled(false);
			window.getBidsListModel().clear();
			window.getContractsListModel().clear();
			window.getPastContractListModel().clear();
			for (Contract c :  ContractsAPI.getInstance().getContractsForUser(currentUser)) {
				if (c.getDateSigned() == null) {
					window.getBidsListModel().add(window.getBidsListModel().getSize(), c);
				}else { 
					if(Instant.parse(c.getExpiryDate()).toEpochMilli() > System.currentTimeMillis()) {
						window.getContractsListModel().add(window.getContractsListModel().getSize(),c);
					}else {
						window.getPastContractListModel().add(window.getPastContractListModel().getSize(),c);
					}
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {

		window.getViewContractBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewContractWindow(currentUser, selectedContract);
			}
		});
		
		window.getRefreshBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		ListSelectionListener universal = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getSource() instanceof JList ) {
					JList<Contract> list = (JList<Contract>) e.getSource();
					
					if(list.getSelectedIndex() >= 0) {
						selectedContract = list.getSelectedValue();
						window.getViewContractBtn().setEnabled(true);
					}
					else {
						window.getViewContractBtn().setEnabled(false);
					}
				}	
			}
		
		};
		
		window.getBidsList().addListSelectionListener(universal );
		
		window.getContractsList().addListSelectionListener(universal);;
		
		window.getPastContractsList().addListSelectionListener(universal);;
		
	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		window.getViewContractBtn().setEnabled(false);
	}
}
