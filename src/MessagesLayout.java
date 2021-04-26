import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MessagesLayout extends JFrame implements ActionListener{

	User currentUser = null;
	Bid selectedBid;
	DefaultListModel<Message> msgListModel = new DefaultListModel<Message>();
	
	
	Container container = getContentPane();
	JLabel messagesLabel = new JLabel("Chat with the request inititator");
	JList<Message> msgList = new JList<Message>(msgListModel);
	JButton refreshBtn = new JButton("Refresh");
	JTextField chatInput = new JTextField();
	JButton sendChatBtn = new JButton("Send");
	
	
	public MessagesLayout(User currentUser, String bidId) {
		this.currentUser = currentUser;
		refresh(bidId);
		
		container.setLayout(null);
		
		messagesLabel.setBounds(10, 10, 300, 30);
		refreshBtn.setBounds(370, 10, 100, 30);
    	msgList.setBounds(10,50, 460, 300);
        chatInput.setBounds(10,360, 350, 30);
        sendChatBtn.setBounds(370, 360, 100, 30);

        sendChatBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        
        container.add(messagesLabel);
        container.add(msgList);
        container.add(chatInput);
        container.add(sendChatBtn);
        container.add(refreshBtn);
        
	}


	private void refresh(String bidId) {
		msgListModel.clear();
		
		try {
			selectedBid = Application.bids.getBid(bidId);
			msgListModel.addAll(selectedBid.getMessageIds());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to get messages");
		}

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
      if(e.getSource() == sendChatBtn) {
    	if (chatInput.getText().length() > 0 && selectedBid != null) {
    		try {
				Application.messages.sendMessage(selectedBid, currentUser, chatInput.getText());
				JOptionPane.showMessageDialog(this, "Message sent");
				refresh(selectedBid.getId());
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Unable to send message");
			}
    	}
      } else if(e.getSource() == refreshBtn) {
    	  refresh(selectedBid.getId());
      }
		
	}
}
