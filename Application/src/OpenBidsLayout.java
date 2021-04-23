import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OpenBidsLayout extends JFrame implements ActionListener, ListSelectionListener {
	
	 	Container container = getContentPane();
	    JLabel searchSubjectLabel = new JLabel("Search bids for subject:");
	    JComboBox searchSubjectInput = null;
	    JLabel bidsLabel = new JLabel("Available bids:");
	    DefaultListModel<String> bidListModel = new DefaultListModel<String>();
	    JList<String> bidsList = new JList<String>(bidListModel);  
	    JLabel bidsDetailsLabel = new JLabel("Bid Deatils:");
	    JTextArea bidDetails = new JTextArea();
	    JButton closeDownBtn = new JButton("Close This Bid");
	    JButton newBidBtn = new JButton("New Bid");
	    JButton seeMyBidsBtn = new JButton("My Bids");
	    JLabel messagesLbabel = new JLabel("Bid Messages");
	    DefaultListModel<Message> msgListModel = new DefaultListModel<Message>();
	    JList<Message> msgList = new JList<Message>(msgListModel);
	    JTextField chatInput = new JTextField();
	    JButton sendChatBtn = new JButton("Send");
	    ArrayList<Subject> subjects;
	    Bid selectedBid = null;
	    Subject selectedSubject = null;
	    User currentUser = null;
	    
	    OpenBidsLayout(User currentUser) {
	    	this.currentUser = currentUser;
			try {
				subjects = Application.subjects.getAllSubjects();
				ArrayList<String> names = new ArrayList<String>();
				for (Subject s : subjects) {
					names.add(s.getName());
				}
				searchSubjectInput = new JComboBox(names.toArray());
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error getting subjects");
			}
	          
	    	container.setLayout(null);
	    	
	    	searchSubjectLabel.setBounds(10, 10, 150, 30);
	    	searchSubjectInput.setBounds(160, 10, 200, 30);
	    	bidsLabel.setBounds(10, 50, 150, 30);
	    	bidsList.setBounds(10,90, 350,200);
	    	newBidBtn.setBounds(370, 90, 100,30);
	    	seeMyBidsBtn.setBounds(370,130, 100, 30);
	    	bidsDetailsLabel.setBounds(10, 300, 150, 30);
	    	bidDetails.setBounds(10, 340, 460, 150);
	    	messagesLbabel.setBounds(10, 500, 150, 30);
	    	closeDownBtn.setBounds(320, 500, 150, 30);
	    	bidDetails.setEditable(false);
	    	msgList.setBounds(10,540, 460, 150);
	        chatInput.setBounds(10,700, 350, 30);
	        sendChatBtn.setBounds(370, 700, 100, 30);
	        
	        searchSubjectInput.addActionListener(this);
	        closeDownBtn.addActionListener(this);
	        bidsList.addListSelectionListener(this);
	        sendChatBtn.addActionListener(this);
	        
	        
	        container.add(searchSubjectLabel);
	        container.add(searchSubjectInput);
	        container.add(bidsLabel);
	        container.add(bidsList);
	        container.add(bidsDetailsLabel);
	        container.add(bidDetails);
	        container.add(closeDownBtn);
	        container.add(messagesLbabel);
	        container.add(newBidBtn);
	        container.add(seeMyBidsBtn);
	        container.add(msgList);
	        container.add(chatInput);
	        container.add(sendChatBtn);


	    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == searchSubjectInput) {
			bidListModel.clear();
			selectedSubject = subjects.get(searchSubjectInput.getSelectedIndex());
			ArrayList<String> bids = selectedSubject.getBidIds();
			bidListModel.addAll(bids);
			bidDetails.setText("");
        }else if(e.getSource() == closeDownBtn) {
        	try {
				Application.bids.closeBid(selectedBid);
				JOptionPane.showMessageDialog(this, "Bid Closed");
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Error closing bids");
			}
        }else if(e.getSource() == sendChatBtn) {
        	if (chatInput.getText().length() > 0 && selectedBid != null) {
        		try {
					Application.messages.sendMessage(selectedBid, currentUser, chatInput.getText());
					JOptionPane.showMessageDialog(this, "Message sent");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Unable to send message");
				}
        	}
        }
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == bidsList) {
			String id = bidsList.getSelectedValue();
			if(id != null) {
				System.out.println(id);
				try {
					selectedBid = Application.bids.getBid(id);
					bidDetails.setText(selectedBid.toString());
					msgListModel.clear();
					msgListModel.addAll(selectedBid.getMessageIds());
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Unable to get bid details");
				}
			}
			
		}
	}

}
