package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import apiservices.RequestAPI;
import apiservices.UserAPI;
import apiservices.ContractsAPI;
import apiservices.MessagesAPI;
import model.Request;
import model.Contract;
import model.Message;
import model.User;
/**
 * This is the layout for a view contract window. 
 * This window is opened when the user has selected a contract to view, so the user can view the contract details and message the other party.
 * This window will also open when the requestor wants selected a bid to view in close bidding to message the bidder.
 * In closed bidding, the bidder will also see this window after pressing the message seller button to message the seller. 
 * @author Andrew Pang
 *
 */
public class ViewContractLayout extends RefreshableLayout implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	User currentUser;
	Contract contract;
	Request bid;

	//Labels
	JLabel contractDetailsLabel;
	JLabel adjustContractLabel;
	JLabel hoursPerSessionLabel;
	JLabel sessionsPerWeekLabel;
	JLabel ratePerSessionLabel;
	JLabel messagesLabel;
	JLabel contractDurationLabel;
	JLabel newTutorIDLabel;
	
	
	//Outputs 
	JTextArea contractDetails;
		
	//Inputs
	JComboBox<String> hoursPerSessionInput;
	JComboBox<String> sessionsPerWeekInput ;
	JTextField ratePerSessionInput;
	JComboBox<String> contractDurationInput;
	JTextField chatInput;
	JTextField newTutorIDInput;
	
	//Lists
	DefaultListModel<Message> msgListModel;
	JList<Message> msgList;
	
	//Buttons
	JButton refreshBtn;
	JButton updateContractBtn;
	JButton signContractBtn;
	JButton renewContractBtn;
	JButton sendChatBtn;
	
	
	public ViewContractLayout(User currentUser, Contract contract) {
		super();
		this.currentUser = currentUser;
		this.contract = contract;
		refresh();
	}
	
	/**
     * Instantiate the View Elements to be added to the Layout
     */
	@Override
	protected void initElements() {
		//Labels
		contractDetailsLabel = new JLabel("Details of the contract.");
		adjustContractLabel = new JLabel("Adjust details of the contract.");
		hoursPerSessionLabel = new JLabel("Hours per session :");
		sessionsPerWeekLabel = new JLabel("Sessions per week :");
		ratePerSessionLabel = new JLabel("Rate per session :");
		contractDurationLabel =  new JLabel("Duration of contract:");
		newTutorIDLabel = new JLabel("New Tutor's ID: ");
		messagesLabel = new JLabel("Chat");
		
		//Outputs 
		contractDetails = new JTextArea();
			
		//Inputs
		String[] numbers = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		String[] months = {"6","3","9","12"};
		hoursPerSessionInput = new JComboBox<String>(numbers);
		sessionsPerWeekInput = new JComboBox<String>(numbers);
		ratePerSessionInput = new JTextField();
		contractDurationInput = new JComboBox<String>(months);
		chatInput = new JTextField();
		newTutorIDInput =  new JTextField();
		
		//Lists
		msgListModel = new DefaultListModel<Message>();
		msgList = new JList<Message>(msgListModel);
		
		//Buttons
		refreshBtn = new JButton("Refresh");
		updateContractBtn = new JButton("Update Contract");
		signContractBtn = new JButton("Sign Contract");
		sendChatBtn = new JButton("Send");
		renewContractBtn = new JButton("Renew Contract");
	}
	
	/**
	 * Set the positions of the View elements to be added
	*/
	@Override
	protected void setElementBounds() {
		contractDetailsLabel.setBounds(10,10,300,30);
		contractDetails.setBounds(10,50,460,150);
		adjustContractLabel.setBounds(10,210,300,30);
		hoursPerSessionLabel.setBounds(10,250,200,30);
		hoursPerSessionInput.setBounds(210,250,200,30);
		sessionsPerWeekLabel.setBounds(10,290,200,30);
		sessionsPerWeekInput.setBounds(210,290,200,30);
		ratePerSessionLabel.setBounds(10,330,200,30);
		ratePerSessionInput.setBounds(210,330,200,30);
		contractDurationLabel.setBounds(10,370,200,30);
		contractDurationInput.setBounds(210,370,200,30);
		newTutorIDLabel.setBounds(10, 410, 200, 30);
		newTutorIDInput.setBounds(210, 410, 200, 30);
		
		renewContractBtn.setBounds(10, 450, 140, 30);
		signContractBtn.setBounds(160,450,140,30);
		updateContractBtn.setBounds(310,450,140,30);
		messagesLabel.setBounds(10, 490, 300, 30);
		refreshBtn.setBounds(370, 10, 100, 30);
    	msgList.setBounds(10, 530, 460, 300);
        chatInput.setBounds(10,840, 350, 30);
        sendChatBtn.setBounds(370, 840, 100, 30);
	}

	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		container.add(messagesLabel);
        container.add(msgList);
        container.add(chatInput);
        container.add(sendChatBtn);
        container.add(refreshBtn);
        container.add(contractDetailsLabel);
        container.add(contractDetails);
        container.add(adjustContractLabel);
        container.add(hoursPerSessionLabel);
        container.add(hoursPerSessionInput);
        container.add(sessionsPerWeekLabel);
        container.add(sessionsPerWeekInput);
        container.add(ratePerSessionLabel);
        container.add(ratePerSessionInput);
        container.add(contractDurationLabel);
        container.add(contractDurationInput);
        container.add(newTutorIDLabel);
        container.add(newTutorIDInput);
        container.add(renewContractBtn);
        container.add(signContractBtn);
        container.add(updateContractBtn);
	}

	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		signContractBtn.addActionListener(this);
        updateContractBtn.addActionListener(this);
        sendChatBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        renewContractBtn.addActionListener(this);
	}

	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		signContractBtn.setEnabled(false);
		updateContractBtn.setEnabled(false);
		renewContractBtn.setEnabled(false);
	}
	
	/**
	 * Actions to be performed in the case of a user induced events
	 * @param e The action event
	 */	
	@Override
	public void actionPerformed(ActionEvent e) {
		
      if(e.getSource() == sendChatBtn) {
    	if (chatInput.getText().length() > 0) {
    		try {
    			Message m = new Message(bid, currentUser, contract, chatInput.getText());
    			
				MessagesAPI.getInstance().sendMessage(m);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Unable to send message");
			}
    		refresh();
    	}
      }
      else if(e.getSource() == refreshBtn) {
    	  refresh();
      } 
      else if(e.getSource() == updateContractBtn) {
    	  
    	  contract.setHoursPerSession(hoursPerSessionInput.getSelectedItem().toString());
    	  contract.setSessionsPerWeek(sessionsPerWeekInput.getSelectedItem().toString());
    	  contract.setRatePerSession(ratePerSessionInput.getText());
    	  contract.setContractDuration(contractDurationInput.getSelectedItem().toString());
    	  try {
    		  ContractsAPI.getInstance().updateContract(contract);
    	  } catch (Exception e1) {
    		  JOptionPane.showMessageDialog(this, "Failed to update contract details. Try again.");
    		  e1.printStackTrace();
    	  }
    	  refresh();
      } 
      else if(e.getSource() == signContractBtn) {
    	  try {
    		  contract.sign();
    		  ContractsAPI.getInstance().signContract(bid,contract);
    	  } catch (Exception e1) {
    		  JOptionPane.showMessageDialog(this, "Cannot Sign Contract");
    		  e1.printStackTrace();
    	  }
    	  refresh();
      }
      else if (e.getSource() == renewContractBtn) {
    	  
    	  try {
    		  User tutor = UserAPI.getInstance().getUserByID(newTutorIDInput.getText());
    		  if (tutor.getCompetencyLevel(contract.getSubjectId()) > currentUser.getCompetencyLevel(contract.getSubjectId()) + 2) {
    			  contract.setHoursPerSession(hoursPerSessionInput.getSelectedItem().toString());
    	    	  contract.setSessionsPerWeek(sessionsPerWeekInput.getSelectedItem().toString());
    	    	  contract.setRatePerSession(ratePerSessionInput.getText());
    	    	  contract.setContractDuration(contractDurationInput.getSelectedItem().toString());
    	    	  Contract newcontract = new Contract(tutor, contract);
        		  Contract renewed = ContractsAPI.getInstance().addContract(newcontract);
        		  renewed.sign();
        		  ContractsAPI.getInstance().signContract(RequestAPI.getInstance().getRequest(renewed.getInitialRequestId()), renewed);
        		  new ViewContractWindow(this.currentUser,renewed);
        		  this.dispose();
    		  }else {
    			  JOptionPane.showMessageDialog(this, "Tutor is incompetent!");
    		  }
    		  
    		  
    	  }catch (Exception e1) {
    		  JOptionPane.showMessageDialog(this, "Cannot Sign Contract");
    		  e1.printStackTrace();
    	  }
    	  refresh();
      }
	}


	/**
	 * Default actions to perform on an auto refresh call
	 */
	@Override
	protected void refresh() {
		try {
			contract = ContractsAPI.getInstance().getContract(contract.getId());
			contractDetails.setText(contract.getContractDetails());
			newTutorIDInput.setText(contract.getFirstPartyId());
			bid = RequestAPI.getInstance().getRequest(contract.getInitialRequestId());
			msgListModel.clear();
			msgListModel.addAll(bid.getMessagesForContract(contract.getId()));
			
			boolean userIsRequestor = currentUser.getId().contentEquals(contract.getSecondPartyId());
			boolean userIsBidder = currentUser.getId().contentEquals(contract.getFirstPartyId());
			boolean notsigned = contract.getDateSigned() == null ;
			boolean isExpired = Instant.parse(contract.getExpiryDate()).toEpochMilli() < System.currentTimeMillis();
			signContractBtn.setEnabled(notsigned && userIsRequestor );
			updateContractBtn.setEnabled(notsigned && userIsBidder);
			
			renewContractBtn.setEnabled(!notsigned && isExpired && userIsRequestor);
			newTutorIDInput.setEnabled(!notsigned && isExpired && userIsRequestor );
			

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to get contract details. The request may have ended.");
			dispose();
		}
	}

	
}
