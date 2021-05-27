package ui;



import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import model.Message;
/**
 * This is the layout for a view contract window. 
 * This window is opened when the user has selected a contract to view, so the user can view the contract details and message the other party.
 * This window will also open when the requestor wants selected a bid to view in close bidding to message the bidder.
 * In closed bidding, the bidder will also see this window after pressing the message seller button to message the seller. 
 * @author Andrew Pang
 *
 */
public class ViewContractLayout extends WindowLayout {

	
	private static final long serialVersionUID = 1L;

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
	JButton seeOtherBidsBtn;
	JButton extendButton;
	


	//Checkboxs
	JCheckBox subscribeBox;
	
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
		updateContractBtn = new JButton("Update Terms");
		signContractBtn = new JButton("Sign Contract");
		sendChatBtn = new JButton("Send");
		renewContractBtn = new JButton("Update & Reuse");
		seeOtherBidsBtn = new JButton("See Other Bids");
		extendButton = new JButton("Extend");
		
		//Checkboxes
		subscribeBox = new JCheckBox("Subscribe to bid");
		
	}
	
	/**
	 * Set the positions of the View elements to be added
	*/
	@Override
	protected void setElementBounds() {
		contractDetailsLabel.setBounds(10,10,300,30);
		contractDetails.setBounds(10,50,460,150);
		adjustContractLabel.setBounds(10,210,250,30);
		subscribeBox.setBounds(260, 210, 150, 30);
		
		hoursPerSessionLabel.setBounds(10,250,150,30);
		hoursPerSessionInput.setBounds(160,250,150,30);
		sessionsPerWeekLabel.setBounds(10,290,150,30);
		sessionsPerWeekInput.setBounds(160,290,150,30);
		ratePerSessionLabel.setBounds(10,330,150,30);
		
		ratePerSessionInput.setBounds(160,330,150,30);
		contractDurationLabel.setBounds(10,370,150,30);
		contractDurationInput.setBounds(160,370,150,30);
		extendButton.setBounds(320,370,150,30);
		newTutorIDLabel.setBounds(10, 410, 200, 30);
		newTutorIDInput.setBounds(160, 410, 150, 30);
		renewContractBtn.setBounds(320, 410, 150, 30);
		
		signContractBtn.setBounds(10,450,220,30);
		updateContractBtn.setBounds(245,450,220,30);
		
		refreshBtn.setBounds(370, 10, 100, 30);
		
		//Open bid neccecistes
		seeOtherBidsBtn.setBounds(10, 490, 460, 30);
		
		//Close bid neccessities
		messagesLabel.setBounds(10, 530, 300, 30);
    	msgList.setBounds(10, 570, 460, 300);
        chatInput.setBounds(10,880, 350, 30);
        sendChatBtn.setBounds(370, 880, 100, 30);
	}

	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		
        
        container.add(refreshBtn);
        container.add(contractDetailsLabel);
        container.add(contractDetails);
        container.add(adjustContractLabel);
        container.add(subscribeBox);
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
        container.add(extendButton);
        container.add(signContractBtn);
        container.add(updateContractBtn);
        container.add(seeOtherBidsBtn);
		container.add(messagesLabel);
        container.add(chatInput);
        container.add(msgList);
        container.add(sendChatBtn);
	}

	/**
	 * Getters and Setters for each element
	 */

	public JTextArea getContractDetails() {
		return contractDetails;
	}

	public JComboBox<String> getHoursPerSessionInput() {
		return hoursPerSessionInput;
	}

	public JComboBox<String> getSessionsPerWeekInput() {
		return sessionsPerWeekInput;
	}

	public JTextField getRatePerSessionInput() {
		return ratePerSessionInput;
	}

	public JComboBox<String> getContractDurationInput() {
		return contractDurationInput;
	}

	public JTextField getChatInput() {
		return chatInput;
	}

	public JTextField getNewTutorIDInput() {
		return newTutorIDInput;
	}

	public DefaultListModel<Message> getMsgListModel() {
		return msgListModel;
	}

	public JList<Message> getMsgList() {
		return msgList;
	}

	public JButton getRefreshBtn() {
		return refreshBtn;
	}

	public JButton getUpdateContractBtn() {
		return updateContractBtn;
	}

	public JButton getSignContractBtn() {
		return signContractBtn;
	}

	public JButton getRenewContractBtn() {
		return renewContractBtn;
	}

	public JButton getSendChatBtn() {
		return sendChatBtn;
	}

	public JButton getSeeOtherBidsBtn() {
		return seeOtherBidsBtn;
	}

	public JCheckBox getSubscribeBox() {
		return subscribeBox;
	}
	
	public JButton getExtendButton() {
		return extendButton;
	}
}
