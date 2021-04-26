import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RequestLayout extends JFrame implements ActionListener, ListSelectionListener{
	
	User currentUser = null;
	Bid request = null;
	
	Container container = getContentPane();
	JLabel requestDetailsLabel = new JLabel("Details of the request.");
	JLabel bidListLabel = new JLabel("Current bids for the request");
	JLabel bidDetailsLabel = new JLabel("Bid details");
	
	JTextArea requestDetails = new JTextArea();
    DefaultListModel<String> bidListModel = new DefaultListModel<String>();
    JList<String> bidsList = new JList<String>(bidListModel);  
    JTextArea bidDetails = new JTextArea();
    
    JButton refreshBtn = new JButton("Refresh");
    JButton messageBtn = new JButton("Messages");
    JButton closeBidBtn = new JButton("Close this bid");
    
    
	public RequestLayout(User currentUser, String requestId){
		this.currentUser = currentUser;
		try {
			request = Application.bids.getBid(requestId);
			requestDetails.setText(request.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
//			dispose();
		}
		
		container.setLayout(null);
		
		requestDetails.setEditable(false);
		bidDetails.setEditable(false);
		
		requestDetailsLabel.setBounds(10, 10, 300, 30);
		requestDetails.setBounds(10, 50, 470, 150);
		bidListLabel.setBounds(10, 210, 300, 30);
		bidsList.setBounds(10, 250, 470, 150);
		bidDetailsLabel.setBounds(10, 410, 300, 30);
		bidDetails.setBounds(10, 450, 470, 150);
		refreshBtn.setBounds(10, 610, 150, 30);
		messageBtn.setBounds(170, 610, 150, 30);
		closeBidBtn.setBounds(330, 610, 150, 30);
		
		refreshBtn.addActionListener(this);
		messageBtn.addActionListener(this);
		closeBidBtn.addActionListener(this);
		bidsList.addListSelectionListener(this);
		
		
		container.add(requestDetailsLabel);
		container.add(requestDetails);
		container.add(bidListLabel);
		container.add(bidsList);
		container.add(bidDetailsLabel);
		container.add(bidDetails);
		container.add(refreshBtn);
		
		
		//TODO: Add condition, if currrentUser != requestor, disable closeBidBtn and messageBtn
		container.add(messageBtn);
		container.add(closeBidBtn);
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == messageBtn) {
			new MessagesWindow(currentUser,request.getId());
		}
	}

}
