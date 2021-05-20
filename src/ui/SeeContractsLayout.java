package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apiservices.ContractsAPI;
import model.Contract;
import model.User;

/**
 * This is the layout for a see my contracts window. 
 * This window is opened when the user selects the see my contracts option and want to his/her contracts as a tutor or student
 * @author Andrew Pang
 *
 */
public class SeeContractsLayout extends RefreshableLayout implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	
	User currentUser;
	
	Contract selectedContract;
	
	JLabel bidsLabel, contractsLabel, pastContractsLabel;
	
	DefaultListModel<Contract> contractsListModel, bidsListModel, pastContractListModel;
	JList<Contract> contractsList, bidsList, pastContractsList;
	
	JButton refreshBtn, viewContractBtn;

	
	public SeeContractsLayout(User currentUser) {
		this.currentUser = currentUser;
		refresh();
	}
	
	
	/**
     * Instantiate the View Elements to be added to the Layout
     */
	@Override
	protected void initElements() {
		bidsLabel = new JLabel("Your bids");
		contractsLabel = new JLabel("Your contracts");
		pastContractsLabel = new JLabel("Your past contracts");
		
		contractsListModel = new DefaultListModel<Contract>();
		contractsList = new JList<Contract>(contractsListModel);
		
		bidsListModel =  new DefaultListModel<Contract>();
		bidsList =  new JList<Contract>(bidsListModel);
		
		pastContractListModel =  new DefaultListModel<Contract>();
		pastContractsList =  new JList<Contract>(pastContractListModel);
		
		refreshBtn =  new JButton("Refresh");
		viewContractBtn = new JButton("View");
	}
	
	/**
	 * Set the positions of the View elements to be added
	*/

	@Override
	protected void setElementBounds() {
		bidsLabel.setBounds(10,10,200,30);
		bidsList.setBounds(10,50,400,150);
		contractsLabel.setBounds(10,210,200,30);
		contractsList.setBounds(10,250,400,150);
		pastContractsLabel.setBounds(10,410,200,30);
		pastContractsList.setBounds(10,450,400,150);
		refreshBtn.setBounds(10,610,195,30);
		viewContractBtn.setBounds(205,610,195,30);
	}

	/**
	 * Add the elements to the view container
	 */

	@Override
	protected void addToContainer() {
		container.add(bidsLabel);
		container.add(bidsList);
		container.add(contractsLabel);
		container.add(contractsList);
		container.add(pastContractsLabel);
		container.add(pastContractsList);
		container.add(viewContractBtn);
		container.add(refreshBtn);
		

	}
	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		viewContractBtn.addActionListener(this);
		refreshBtn.addActionListener(this);
		contractsList.addListSelectionListener(this);
		bidsList.addListSelectionListener(this);
		pastContractsList.addListSelectionListener(this);
	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		viewContractBtn.setEnabled(false);
	}
	
	/**
	 * Default actions to perform on an auto refresh call
	 */
	@Override
	protected void refresh() {
		// TODO Auto-generated method stub
		
		try {
			viewContractBtn.setEnabled(false);
			bidsListModel.clear();
			contractsListModel.clear();
			pastContractListModel.clear();
			for (Contract c :  ContractsAPI.getInstance().getContractsForUser(currentUser)) {
				if (c.getDateSigned() == null) {
					bidsListModel.add(bidsListModel.getSize(), c);
				}else { 
					if(Instant.parse(c.getExpiryDate()).toEpochMilli() > System.currentTimeMillis()) {
						contractsListModel.add(contractsListModel.getSize(),c);
					}else {
						pastContractListModel.add(pastContractListModel.getSize(),c);
					}
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Actions to be performed in the case of a user induced events for list views
	 * @param e The action event
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource() instanceof JList ) {
			JList<Contract> list = (JList<Contract>) e.getSource();
			
			if(list.getSelectedIndex() >= 0) {
				selectedContract = list.getSelectedValue();
				viewContractBtn.setEnabled(true);
			}
			else {
				viewContractBtn.setEnabled(false);
			}
		}		
	}
	
	/**
	 * Actions to be performed in the case of a user induced events
	 * @param e The action event
	 */
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
