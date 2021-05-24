package ui;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import model.Contract;


/**
 * This is the layout for a see my contracts window. 
 * This window is opened when the user selects the see my contracts option and want to his/her contracts as a tutor or student
 * @author Andrew Pang
 *
 */
public class SeeContractsLayout extends WindowLayout {

	private static final long serialVersionUID = 1L;
	
	JLabel bidsLabel, contractsLabel, pastContractsLabel;
	
	DefaultListModel<Contract> contractsListModel, bidsListModel, pastContractListModel;
	JList<Contract> contractsList, bidsList, pastContractsList;
	
	JButton refreshBtn, viewContractBtn;
	
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

	public DefaultListModel<Contract> getContractsListModel() {
		return contractsListModel;
	}

	public DefaultListModel<Contract> getBidsListModel() {
		return bidsListModel;
	}

	public DefaultListModel<Contract> getPastContractListModel() {
		return pastContractListModel;
	}

	public JList<Contract> getContractsList() {
		return contractsList;
	}

	public JList<Contract> getBidsList() {
		return bidsList;
	}

	public JList<Contract> getPastContractsList() {
		return pastContractsList;
	}

	public JButton getRefreshBtn() {
		return refreshBtn;
	}

	public JButton getViewContractBtn() {
		return viewContractBtn;
	}

	
	
	
	

}
