package ui;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apiservices.BidsAPI;
import apiservices.ContractsAPI;
import apiservices.SubjectAPI;
import model.Bid;
import model.Contract;
import model.Subject;
import model.User;

public class OpenBidsLayout extends RefreshableLayout implements ActionListener, ListSelectionListener {
	
	 	ArrayList<Subject> subjects;
	    Bid selectedBid;
	    Subject selectedSubject;
	    User currentUser;
	    
	    
	    
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
	    
	public OpenBidsLayout(User currentUser) {
		this.currentUser = currentUser;
	}
									
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

	@Override
	protected void bindActionListeners() {
		 searchSubjectInput.addActionListener(this);
	     buyOutBtn.addActionListener(this);
	     requestList.addListSelectionListener(this);
	     messageBtn.addActionListener(this);
	     seeBidsBtn.addActionListener(this);
	     createBidBtn.addActionListener(this);
	     refreshBtn.addActionListener(this);
	}

	@Override
	protected void init() {
		requestDetails.setEditable(false);
		buyOutBtn.setEnabled(false);
		createBidBtn.setEnabled(false);
		seeBidsBtn.setEnabled(false);
		messageBtn.setEnabled(false);
		loadSubjects();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == searchSubjectInput) {
			loadRequests();
        }
		else if(e.getSource() == buyOutBtn) {
        	try {
        		Contract contract = ContractsAPI.getInstance().addContract(new Contract(currentUser, selectedBid));
        		ContractsAPI.getInstance().signContract(selectedBid,contract);
				new ViewContractWindow(currentUser,contract);
				dispose();
			}
        	catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error buying out request");
			}
        }
		else if (e.getSource() ==  createBidBtn) {
        	try {
        		Contract contract = new Contract(currentUser, selectedBid);
        		contract.setHoursPerSession(hoursPerSessionInput.getSelectedItem().toString());
        		contract.setSessionsPerWeek(sessionsPerWeekInput.getSelectedItem().toString());
        		contract.setRatePerSession(ratePerSessionInput.getText());
        		ContractsAPI.getInstance().addContract(contract);
        		new RequestWindow(currentUser,selectedBid);
			}
        	catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error creating a bid");
			}
        }
        else if(e.getSource() == messageBtn) {
        	Contract contract = new Contract(currentUser, selectedBid);
    		try {
				Contract addedContract = ContractsAPI.getInstance().addContract(contract);
				new ViewContractWindow(currentUser,addedContract);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        	
        }
        else if(e.getSource() == seeBidsBtn) {
        	new RequestWindow(currentUser,selectedBid);
        }
        else if(e.getSource() == refreshBtn) {
        	loadSubjects();
        	refresh();
        }

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == requestList) {
			loadRequestDetails();
		}
	}

	@Override
	protected void refresh() {
		if(selectedBid != null) {
			loadRequestDetails();
		}
		loadRequests();
	}
	
	private void loadSubjects() {
		searchSubjectInput.removeAll();
		searchSubjectInput.removeAllItems();
		try {
			subjects = SubjectAPI.getInstance().getAllSubjects();
			for (Subject s : subjects) {
				searchSubjectInput.addItem(s.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error getting subjects");
		}
	}
	private void loadRequests() {
		requestListModel.clear();
		if(searchSubjectInput.getSelectedIndex() > 0) {
			selectedSubject = subjects.get(searchSubjectInput.getSelectedIndex());
			requestListModel.addAll(selectedSubject.getOpenRequestIds());
			requestDetails.setText("");
		}
	}
	
	private void loadRequestDetails() {
		String id = requestList.getSelectedValue();
		if(id != null) {
			try {
				selectedBid = BidsAPI.getInstance().getBid(id);
				requestDetails.setText(selectedBid.toString());
				boolean qualified = selectedBid.getCompetency() <= currentUser.getCompetencyLevel(selectedSubject.getId());
				boolean closed = selectedBid.getDateClosedDown() == null;
				boolean check = closed && qualified;
				buyOutBtn.setEnabled(check && selectedBid.getType().contentEquals("open") );
				createBidBtn.setEnabled(check && selectedBid.getType().contentEquals("open"));
				seeBidsBtn.setEnabled(check && selectedBid.getType().contentEquals("open"));
				messageBtn.setEnabled(check && selectedBid.getType().contentEquals("closed"));
				
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "No longer able to get bid details");
				requestDetails.setText("");
				loadRequests();
			}
		}
	}

	
}
