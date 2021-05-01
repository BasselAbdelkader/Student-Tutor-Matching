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

public class SeeContractsLayout extends RefreshableLayout implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	
	User currentUser;
	ArrayList<Contract> contracts;
	Contract selectedContract;
	
	JLabel contractsLabel;
	
	DefaultListModel<String> contractsListModel;
	JList contractsList;
	
	JButton refreshBtn;
	JButton viewContractBtn;
	
	
	public SeeContractsLayout(User currentUser) {
		this.currentUser = currentUser;
		refresh();
	}
	
	

	@Override
	protected void initElements() {
		contractsLabel = new JLabel("Your contracts");
		
		contractsListModel = new DefaultListModel<String>();
		contractsList = new JList<String>(contractsListModel);  
		
		refreshBtn =  new JButton("Refresh");
		viewContractBtn = new JButton("View");
	}

	@Override
	protected void setElementBounds() {
		contractsLabel.setBounds(10,10,200,30);
		contractsList.setBounds(10,50,400,200);
		refreshBtn.setBounds(10,260,195,30);
		viewContractBtn.setBounds(205,260,195,30);
	}

	@Override
	protected void addToContainer() {
		container.add(contractsLabel);
		container.add(contractsList);
		container.add(viewContractBtn);
		container.add(refreshBtn);
		

	}

	@Override
	protected void bindActionListeners() {
		viewContractBtn.addActionListener(this);
		refreshBtn.addActionListener(this);
		contractsList.addListSelectionListener(this);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		viewContractBtn.setEnabled(false);
	}

	@Override
	protected void refresh() {
		// TODO Auto-generated method stub
		
		try {
			viewContractBtn.setEnabled(false);
			contractsListModel.clear();
			contracts = ContractsAPI.getInstance().getContractsForUser(currentUser);
			ArrayList<String> contractIds = new ArrayList<String>();
			for (Contract c : contracts) {
				contractIds.add(c.getId());
			}
			contractsListModel.addAll(contractIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource() == contractsList) {
			if(contractsList.getSelectedIndex() >= 0) {
				selectedContract = contracts.get(contractsList.getSelectedIndex());
				viewContractBtn.setEnabled(true);
			}
			else {
				viewContractBtn.setEnabled(false);
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == viewContractBtn) {
			new ViewContractWindow(currentUser, selectedContract);
			
		}
		else if (e.getSource() == refreshBtn) {
			refresh();
			
		}
		
	}

}
