package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import adaptors.ContractsAPI;
import adaptors.RequestAPI;
import adaptors.SubjectAPI;
import model.Contract;
import model.Request;
import model.Subject;
import model.User;
import ui.OpenBidsLayout;
/**
 * This is boot-strapper class for the Open Bids Layout
 * it specifies the properties of the open bids window window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class OpenBidsWindow extends RefreshableController{
	
	private User currentUser;
	private ArrayList<Subject> subjects;
	private Request selectedBid;
	private Subject selectedSubject;
    
	private OpenBidsLayout window;
	
	public OpenBidsWindow(User currentUser) {
		super(new OpenBidsLayout(), "See All Requests", 500,750);
		this.window = (OpenBidsLayout) super.window;
		this.currentUser = currentUser;
    	bindActionListeners();
        init(); 
        refresh();
    }
	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		
		window.getSearchSubjectInput().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadRequests();
			}
		});
		
		window.getBuyOutBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
	        		Contract contract = ContractsAPI.getInstance().addContract(new Contract(currentUser, selectedBid));
	        		
	        		
	        		contract.sign();
	        		
	        		ContractsAPI.getInstance().signContract(selectedBid,contract);
					new ViewContractWindow(currentUser,contract);
					closeWindow();
				}
	        	catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(window, "Error buying out request");
				}
			}
		});
		
		window.getCreateBidBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
	        		Contract contract = new Contract(currentUser, selectedBid);
	        		contract.getLessonInfo().setHoursPerSession(window.getHoursPerSessionInput().getSelectedItem().toString());
	        		contract.getLessonInfo().setSessionsPerWeek(window.getSessionsPerWeekInput().getSelectedItem().toString());
	        		contract.getLessonInfo().setRatePerSession(window.getRatePerSessionInput().getText());
	        		ContractsAPI.getInstance().addContract(contract);
	        		new RequestWindow(currentUser,selectedBid);
				}
	        	catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(window, "Error creating a bid");
				}
			}
		});
	
		window.getMessageBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Contract contract = new Contract(currentUser, selectedBid);
	    		try {
					Contract addedContract = ContractsAPI.getInstance().addContract(contract);
					new ViewContractWindow(currentUser,addedContract);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		window.getSeeBidsBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RequestWindow(currentUser,selectedBid);
			}
		});
		
		window.getRefreshBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadSubjects();
	        	refresh();
			}
		});
		
		window.getRequestList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				loadRequestDetails();
			}
		});
	
	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		window.getRequestDetails().setEditable(false);
		window.getBuyOutBtn().setEnabled(false);
		window.getCreateBidBtn().setEnabled(false);
		window.getSeeBidsBtn().setEnabled(false);
		window.getMessageBtn().setEnabled(false);
		loadSubjects();
	}
	
	/**
	 * Default actions to perform on an auto refresh call
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		if(selectedBid != null) {
			loadRequestDetails();
		}
		loadRequests();
	}
	
	/**
	 * Reload the list of subjects available
	 */
	private void loadSubjects() {
		JComboBox<String> in = window.getSearchSubjectInput();
		in.removeAll();
		in.removeAllItems();
		try {
			subjects = SubjectAPI.getInstance().getAllSubjects();
			for (Subject s : subjects) {
				in.addItem(s.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(window, "Error getting subjects");
		}
	}
	
	/**
	 * Reload the list of requests available for that subject
	 */
	private void loadRequests() {
		
		window.getRequestListModel().clear();
		if(window.getSearchSubjectInput().getSelectedIndex() > 0) {
			selectedSubject = subjects.get(window.getSearchSubjectInput().getSelectedIndex());
			window.getRequestListModel().addAll(selectedSubject.getOpenRequestIds());
			window.getRequestDetails().setText("");
		}
	}
	
	/**
	 * Reload the details of the request
	 */
	private void loadRequestDetails() {
		String id = window.getRequestList().getSelectedValue();
		if(id != null) {
			try {
				selectedBid = RequestAPI.getInstance().getRequest(id);
				window.getRequestDetails().setText(selectedBid.toString());
				boolean qualified = selectedBid.getCompetency() <= currentUser.getCompetencyLevel(selectedSubject.getId());
				boolean closed = selectedBid.getDateClosedDown() == null;
				boolean check = closed && qualified;
				window.getBuyOutBtn().setEnabled(check && selectedBid.getType().contentEquals("open") );
				window.getCreateBidBtn().setEnabled(check && selectedBid.getType().contentEquals("open"));
				window.getSeeBidsBtn().setEnabled(check && selectedBid.getType().contentEquals("open"));
				window.getMessageBtn().setEnabled(check && selectedBid.getType().contentEquals("closed"));
				
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(window, "No longer able to get bid details");
				window.getRequestDetails().setText("");
				loadRequests();
			}
		}
	}

}