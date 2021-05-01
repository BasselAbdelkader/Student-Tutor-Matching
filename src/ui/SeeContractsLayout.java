package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	ArrayList<Contract> contracts;
	Contract selectedContract;
	
	JLabel contractsLabel;
	
	DefaultListModel<String> contractsListModel;
	JList<String> contractsList;
	
	JButton refreshBtn;
	JButton viewContractBtn;
	
	
	public SeeContractsLayout(User currentUser) {
		this.currentUser = currentUser;
		refresh();
	}
	
	
	/**
     * Instantiate the View Elements to be added to the Layout
     */
	@Override
	protected void initElements() {
		contractsLabel = new JLabel("Your contracts");
		
		contractsListModel = new DefaultListModel<String>();
		contractsList = new JList<String>(contractsListModel);  
		
		refreshBtn =  new JButton("Refresh");
		viewContractBtn = new JButton("View");
	}
	
	/**
	 * Set the positions of the View elements to be added
	*/

	@Override
	protected void setElementBounds() {
		contractsLabel.setBounds(10,10,200,30);
		contractsList.setBounds(10,50,400,200);
		refreshBtn.setBounds(10,260,195,30);
		viewContractBtn.setBounds(205,260,195,30);
	}

	/**
	 * Add the elements to the view container
	 */

	@Override
	protected void addToContainer() {
		container.add(contractsLabel);
		container.add(contractsList);
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
	
	/**
	 * Actions to be performed in the case of a user induced events for list views
	 * @param e The action event
	 */
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
