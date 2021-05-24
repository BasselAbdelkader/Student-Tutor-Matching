package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apiservices.RequestAPI;
import apiservices.ContractsAPI;
import apiservices.SubjectAPI;
import model.Request;
import model.Contract;
import model.Subject;
import model.User;

/**
 * This is the layout for a new open bidding window. 
 * This window is opened when the tutor selects the bid on student request option and want to look for student request to bid on
 * @author Andrew Pang
 *
 */
public class OpenBidsLayout extends WindowLayout {
	
	 
	private static final long serialVersionUID = 1L;
		
	    
	    //Labels
	    JLabel searchSubjectLabel;
	    JLabel requestsLabel;
	    JLabel requestDetailsLabel;
	    JLabel createBidLabel;
	    JLabel hoursPerSessionLabel;
		JLabel sessionsPerWeekLabel;
		JLabel ratePerSessionLabel;
		JLabel orLabel;
		
		//Lists
	    DefaultListModel<String> requestListModel;
	    JList<String> requestList;  
	    
	    //Outputs
	    JTextArea requestDetails;
	    
	    //Inputs
	    JComboBox searchSubjectInput;
		JComboBox hoursPerSessionInput;
		JComboBox sessionsPerWeekInput;
		JTextField ratePerSessionInput;
		
		//Buttons
		JButton createBidBtn;
		JButton seeBidsBtn;
		JButton buyOutBtn;
		JButton messageBtn;
		JButton refreshBtn;
	    
	
	/**
     * Instantiate the View Elements to be added to the Layout
     */				
	@Override
	protected void initElements() {
		//Labels
	    searchSubjectLabel = new JLabel("Subject:");
	    requestsLabel = new JLabel("Available requests:");
	    requestDetailsLabel = new JLabel("Request Details:");
	    createBidLabel = new JLabel("Create a bid");
	    hoursPerSessionLabel = new JLabel("Hours per session :");
		sessionsPerWeekLabel = new JLabel("Sessions per week :");
		ratePerSessionLabel = new JLabel("Rate per session :");
		orLabel = new JLabel("OR");
		
		//Lists
	    requestListModel = new DefaultListModel<String>();
	    requestList = new JList<String>(requestListModel);  
	    
	    //Outputs
	    requestDetails = new JTextArea();
	    
	    //Inputs
	    String[] numbers = {"1","2","3","4","5","6","7"};
	    searchSubjectInput = new JComboBox();
		hoursPerSessionInput = new JComboBox(numbers);
		sessionsPerWeekInput = new JComboBox(numbers);
		ratePerSessionInput = new JTextField("",50);
		
		//Buttons
		refreshBtn = new JButton("Refresh");
		createBidBtn = new JButton("Create Bid");
		seeBidsBtn = new JButton("See All Bids");
		buyOutBtn = new JButton("Buy Out Request");
		messageBtn = new JButton("Message Student");

	}
	
	/**
	* Set the positions of the View elements to be added
	*/
	@Override
	protected void setElementBounds() {
		searchSubjectLabel.setBounds(10, 10, 100, 30);
    	searchSubjectInput.setBounds(110, 10, 200, 30);
    	refreshBtn.setBounds(340, 10, 100, 30);
    	requestsLabel.setBounds(10, 50, 150, 30);
    	requestList.setBounds(10,90, 460,200);
    	requestDetailsLabel.setBounds(10, 300, 150, 30);
    	requestDetails.setBounds(10, 340, 460, 150);
    	createBidLabel.setBounds(10, 500, 150, 30);
    	hoursPerSessionLabel.setBounds(10, 540, 150, 30);
    	sessionsPerWeekLabel.setBounds(10, 580, 150, 30);
    	ratePerSessionLabel.setBounds(10, 620, 150, 30);
    	hoursPerSessionInput.setBounds(160, 540, 100, 30);
    	sessionsPerWeekInput.setBounds(160, 580, 100, 30);
    	ratePerSessionInput.setBounds(160, 620, 100, 30);
    	seeBidsBtn.setBounds(10, 660, 120, 30);
    	createBidBtn.setBounds(140, 660, 120, 30);
    	orLabel.setBounds(290, 580, 30,30);
    	buyOutBtn.setBounds(330, 540, 140, 50);
    	messageBtn.setBounds(330, 600, 140, 50);
		
	}

	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		container.add(searchSubjectLabel);
        container.add(searchSubjectInput);
        container.add(requestsLabel);
        container.add(requestList);
        container.add(requestDetailsLabel);
        container.add(requestDetails);
        container.add(createBidLabel);
        container.add(hoursPerSessionLabel);
        container.add(sessionsPerWeekLabel);
        container.add(ratePerSessionLabel);
        container.add(hoursPerSessionInput);
        container.add(sessionsPerWeekInput);
        container.add(ratePerSessionInput);
        container.add(seeBidsBtn);
        container.add(createBidBtn);
        container.add(orLabel);
        container.add(buyOutBtn);
        container.add(messageBtn);
        container.add(refreshBtn);
	}


	public DefaultListModel<String> getRequestListModel() {
		return requestListModel;
	}

	public JList<String> getRequestList() {
		return requestList;
	}

	public JTextArea getRequestDetails() {
		return requestDetails;
	}

	public JComboBox getSearchSubjectInput() {
		return searchSubjectInput;
	}

	public JComboBox getHoursPerSessionInput() {
		return hoursPerSessionInput;
	}

	public JComboBox getSessionsPerWeekInput() {
		return sessionsPerWeekInput;
	}

	public JTextField getRatePerSessionInput() {
		return ratePerSessionInput;
	}

	public JButton getCreateBidBtn() {
		return createBidBtn;
	}

	public JButton getSeeBidsBtn() {
		return seeBidsBtn;
	}

	public JButton getBuyOutBtn() {
		return buyOutBtn;
	}

	public JButton getMessageBtn() {
		return messageBtn;
	}

	public JButton getRefreshBtn() {
		return refreshBtn;
	}
	
	
}
