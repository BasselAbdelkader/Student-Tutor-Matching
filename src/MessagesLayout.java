import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessagesLayout extends JFrame implements ActionListener{

	User currentUser = null;
	Bid selectedBid;
	Contract selectedContract;
	DefaultListModel<Message> msgListModel = new DefaultListModel<Message>();
	
	
	Container container = getContentPane();
	JLabel messagesLabel = new JLabel("Chat with the request inititator");
	JList<Message> msgList = new JList<Message>(msgListModel);
	JButton refreshBtn = new JButton("Refresh");
	JTextField chatInput = new JTextField();
	JButton sendChatBtn = new JButton("Send");
	
	JLabel contractDetailsLabel = new JLabel("Details of the contract.");
	JLabel adjustContractLabel = new JLabel("Adjust details of the contract.");
	JLabel hoursPerSessionLabel = new JLabel("Hours per session :");
	JLabel sessionsPerWeekLabel = new JLabel("Sessions per week :");
	JLabel ratePerSessionLabel = new JLabel("Rate per session :");
	
	String[] numbers = {"1","2","3","4","5","6","7"};
	
	JTextArea contractDetails = new JTextArea();
	JComboBox<String> hoursPerSessionInput = new JComboBox<String>(numbers);
	JComboBox<String> sessionsPerWeekInput = new JComboBox<String>(numbers);
	JTextField ratePerSessionInput = new JTextField();
	
	JButton updateContractBtn = new JButton("Update Contract");
	JButton signContractBtn = new JButton("Sign Contract");
	
	public MessagesLayout(User currentUser, String bidId, String contractId) {
		this.currentUser = currentUser;
		refresh(bidId,contractId);

		container.setLayout(null);
		
		
		contractDetailsLabel.setBounds(10,10,300,30);
		contractDetails.setBounds(10,50,460,150);
		
		adjustContractLabel.setBounds(10,210,300,30);
		hoursPerSessionLabel.setBounds(10,250,200,30);
		hoursPerSessionInput.setBounds(210,250,200,30);
		sessionsPerWeekLabel.setBounds(10,290,200,30);
		sessionsPerWeekInput.setBounds(210,290,200,30);
		ratePerSessionLabel.setBounds(10,330,200,30);
		ratePerSessionInput.setBounds(210,330,200,30);
		signContractBtn.setBounds(100,370,150,30);
		updateContractBtn.setBounds(260,370,150,30);
		
		messagesLabel.setBounds(10, 410, 300, 30);
		refreshBtn.setBounds(370, 10, 100, 30);
    	msgList.setBounds(10, 450, 460, 300);
        chatInput.setBounds(10,760, 350, 30);
        sendChatBtn.setBounds(370, 760, 100, 30);

        signContractBtn.addActionListener(this);
        updateContractBtn.addActionListener(this);
        sendChatBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        
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
        container.add(signContractBtn);
        container.add(updateContractBtn);
        
	}


	private void refresh(String bidId, String contractId) {
		
		
		
		try {
			selectedContract = Application.contracts.getContract(contractId);
			contractDetails.setText(selectedContract.toString());
			selectedBid = Application.bids.getBid(bidId);
			msgListModel.clear();
			msgListModel.addAll(selectedBid.getMessagesForContract(contractId));
			boolean notsigned = selectedContract.getDateSigned() == null ;
			signContractBtn.setEnabled(notsigned);
			updateContractBtn.setEnabled(notsigned);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to get contract details. The request may have ended.");
			dispose();
		}

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
      if(e.getSource() == sendChatBtn) {
    	if (chatInput.getText().length() > 0 && selectedBid != null) {
    		try {
				Application.messages.sendMessage(selectedBid, selectedContract, currentUser, chatInput.getText());
				JOptionPane.showMessageDialog(this, "Message sent");
				refresh(selectedBid.getId(),selectedContract.getId());
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Unable to send message");
			}
    	}
      } else if(e.getSource() == refreshBtn) {
    	  refresh(selectedBid.getId(),selectedContract.getId());
      } else if(e.getSource() == updateContractBtn) {
    	  selectedContract.setHoursPerSession(hoursPerSessionInput.getSelectedItem().toString());
    	  selectedContract.setSessionsPerWeek(sessionsPerWeekInput.getSelectedItem().toString());
    	  selectedContract.setRatePerSession(ratePerSessionInput.getText());
    	  try {
			Application.contracts.updateContract(selectedContract);
    	  } catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Failed to update contract details. Try again.");
				e1.printStackTrace();
    	  }
    	  refresh(selectedBid.getId(),selectedContract.getId());
      } else if(e.getSource() == signContractBtn) {
    	  try {
			Application.contracts.signContract(selectedBid,selectedContract);
			Application.bids.closeBid(selectedBid);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		  
      }
		
	}
}
