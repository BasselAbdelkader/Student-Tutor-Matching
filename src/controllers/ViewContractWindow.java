package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import apiservices.ContractsAPI;
import apiservices.MessagesAPI;
import apiservices.RequestAPI;
import apiservices.UserAPI;
import model.Contract;
import model.Message;
import model.Request;
import model.User;
import ui.ViewContractLayout;
/**
 * This is boot-strapper class for the View Contract Window
 * it specifies the properties of the view contract window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class ViewContractWindow extends RefreshableController{
	
	User currentUser;
	Contract contract;
	Request request;
	
	ViewContractLayout window;
	
	ViewContractWindow(User currentUser, Contract contract){
		super(new ViewContractLayout(),"View contract  " + contract.getId(), 500, 960);
		this.window = (ViewContractLayout) super.window;
		this.currentUser = currentUser;
		this.contract = contract;
		bindActionListeners();
		init();
		refresh();
	}

	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		
		JComboBox<String> hoursPerSessionInput = window.getHoursPerSessionInput();
		JComboBox<String> sessionsPerWeekInput = window.getSessionsPerWeekInput();
		JComboBox<String> contractDurationInput = window.getContractDurationInput();
		JTextField ratePerSessionInput = window.getRatePerSessionInput();
		
		window.getSignContractBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
	        		  contract.sign();
	        		  ContractsAPI.getInstance().signContract(request,contract);
	        	  } catch (Exception e1) {
	        		  JOptionPane.showMessageDialog(window, "Cannot Sign Contract");
	        		  e1.printStackTrace();
	        	  }
	        	  refresh();
			}
		});
		
		window.getUpdateContractBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contract.setHoursPerSession(hoursPerSessionInput.getSelectedItem().toString());
	        	  contract.setSessionsPerWeek(sessionsPerWeekInput.getSelectedItem().toString());
	        	  contract.setRatePerSession(ratePerSessionInput.getText());
	        	  contract.setContractDuration(contractDurationInput.getSelectedItem().toString());
	        	  try {
	        		  ContractsAPI.getInstance().updateContract(contract);
	        	  } catch (Exception e1) {
	        		  JOptionPane.showMessageDialog(window, "Failed to update contract details. Try again.");
	        		  e1.printStackTrace();
	        	  }
	        	  refresh();
			}
		});
		
		window.getSendChatBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (window.getChatInput().getText().length() > 0) {
	        		try {
	        			Message m = new Message(request, currentUser, contract, window.getChatInput().getText());
	        			
	    				MessagesAPI.getInstance().sendMessage(m);
	    			} catch (Exception e1) {
	    				e1.printStackTrace();
	    				JOptionPane.showMessageDialog(window, "Unable to send message");
	    			}
	        		refresh();
	        	}
			}
		});
		
		window.getRefreshBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		window.getExtendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					contract.setContractDuration(contractDurationInput.getSelectedItem().toString());
					Contract newcontract = new Contract(contract);
	          		Contract renewed = ContractsAPI.getInstance().addContract(newcontract);
	          		new ViewContractWindow(currentUser,renewed);
	          		closeWindow();        		  
	        		  
	        	  }catch (Exception e1) {
	        		  JOptionPane.showMessageDialog(window, "Cannot Sign Contract");
	        		  e1.printStackTrace();
	        	  }
	        	  refresh();
			}
			
		});
		
		window.getRenewContractBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
	        		  User tutor = UserAPI.getInstance().getUserByID(window.getNewTutorIDInput().getText());
	        		  if (tutor.getCompetencyLevel(contract.getSubjectId()) > currentUser.getCompetencyLevel(contract.getSubjectId()) + 2) {
	        			  contract.setHoursPerSession(hoursPerSessionInput.getSelectedItem().toString());
	        	    	  contract.setSessionsPerWeek(sessionsPerWeekInput.getSelectedItem().toString());
	        	    	  contract.setRatePerSession(ratePerSessionInput.getText());
	        	    	  contract.setContractDuration(contractDurationInput.getSelectedItem().toString());
	        	    	  Contract newcontract = new Contract(tutor, contract);
	            		  Contract renewed = ContractsAPI.getInstance().addContract(newcontract);
	            		  new ViewContractWindow(currentUser,renewed);
	            		  closeWindow();
	        		  }else {
	        			  JOptionPane.showMessageDialog(window, "Tutor is incompetent!");
	        		  }
	        		  
	        		  
	        	  }catch (Exception e1) {
	        		  JOptionPane.showMessageDialog(window, "Cannot Sign Contract");
	        		  e1.printStackTrace();
	        	  }
	        	  refresh();
			}
		});
		
		
		window.getSubscribeBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (window.getSubscribeBox().isSelected()) {
					contract.subscribe();
				}else {
					contract.unsubscribe();
				}
	        	try {
	        		ContractsAPI.getInstance().updateContract(contract);
	        	} catch (Exception e1) {
	        		JOptionPane.showMessageDialog(window, "Failed to update contract details. Try again.");
	        		e1.printStackTrace();
	        	}
	        	refresh();
			}
		});
		
		window.getSeeOtherBidsBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RequestWindow(currentUser,request);
			}
		});
        
	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		window.getSignContractBtn().setEnabled(false);
		window.getUpdateContractBtn().setEnabled(false);
		window.getRenewContractBtn().setEnabled(false);
		window.getSubscribeBox().setEnabled(false);
		window.getSeeOtherBidsBtn().setEnabled(false);
		window.getSendChatBtn().setEnabled(false);
		window.getChatInput().setEnabled(false);
	}
	
	
	/**
	 * Default actions to perform on an auto refresh call
	 */
	@Override
	public void refresh() {
		try {
			contract = ContractsAPI.getInstance().getContract(contract.getId());
			window.getContractDetails().setText(contract.getContractDetails());
			window.getNewTutorIDInput().setText(contract.getFirstPartyId());
			window.getSubscribeBox().setSelected(contract.isSubscribed());
			request = RequestAPI.getInstance().getRequest(contract.getInitialRequestId());
			window.getMsgListModel().clear();
			window.getMsgListModel().addAll(request.getMessagesForContract(contract.getId()));
			
			boolean userIsRequestor = currentUser.getId().contentEquals(contract.getSecondPartyId());
			boolean userIsBidder = currentUser.getId().contentEquals(contract.getFirstPartyId());
			boolean notsigned = contract.getDateSigned() == null ;
			boolean isExpired = Instant.parse(contract.getExpiryDate()).toEpochMilli() < System.currentTimeMillis();
			boolean isOpenRequest = request.getType().contentEquals("open");
			boolean isRenewed = ContractsAPI.getInstance().getSignedContract(request) != null;
					
			window.getSignContractBtn().setEnabled((notsigned && userIsRequestor && !isRenewed) || (notsigned && isRenewed && userIsBidder));
			window.getUpdateContractBtn().setEnabled(notsigned && userIsBidder);
			window.getSubscribeBox().setEnabled(isOpenRequest && notsigned && userIsBidder);
			window.getSendChatBtn().setEnabled(!isOpenRequest|| !notsigned);
			window.getChatInput().setEnabled(!isOpenRequest || !notsigned);
			window.getSeeOtherBidsBtn().setEnabled(isOpenRequest && notsigned && !isRenewed);
			window.getRenewContractBtn().setEnabled(!notsigned && isExpired && userIsRequestor);
			window.getRenewContractBtn().setEnabled(!notsigned && userIsRequestor);
			window.getNewTutorIDInput().setEnabled(!notsigned && isExpired && userIsRequestor );

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(window, "Failed to get contract details. The request may have ended.");
			closeWindow();
		}
	}

}
