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

public class OpenBidsLayout extends JFrame implements ActionListener, ListSelectionListener {
	
	 	ArrayList<Subject> subjects;
	    Bid selectedBid = null;
	    Subject selectedSubject = null;
	    User currentUser = null;
	    String[] numbers = {"1","2","3","4","5","6","7"};
	    
	 	Container container = getContentPane();
	    JLabel searchSubjectLabel = new JLabel("Search request for subject:");
	    JComboBox searchSubjectInput = null;
	    JLabel requestsLabel = new JLabel("Available requests:");
	    DefaultListModel<String> requestListModel = new DefaultListModel<String>();
	    JList<String> requestList = new JList<String>(requestListModel);  
	    JLabel requestDetailsLabel = new JLabel("Request Details:");
	    JTextArea requestDetails = new JTextArea();
	    
	    
	    JLabel createBidLabel = new JLabel("Create a bid");
	   
		JLabel hoursPerSessionLabel = new JLabel("Hours per session :");
		JLabel sessionsPerWeekLabel = new JLabel("Sessions per week :");
		JLabel ratePerSessionLabel = new JLabel("Rate per session :");
		JComboBox hoursPerSessionInput = new JComboBox(numbers);
		JComboBox sessionsPerWeekInput = new JComboBox(numbers);
		JTextField ratePerSessionInput = new JTextField("",50);
		JButton createBidBtn = new JButton("Create Bid");
		JButton seeBidsBtn = new JButton("See All Bids");
		
		JLabel orLabel = new JLabel("OR");
		JButton buyOutBtn = new JButton("Buy Out Request");
		JButton messageBtn = new JButton("Message Student");
		

	    
	   
	    
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
	        
	    	requestDetails.setEditable(false);
	    	
	    	searchSubjectLabel.setBounds(10, 10, 200, 30);
	    	searchSubjectInput.setBounds(210, 10, 200, 30);
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

	        searchSubjectInput.addActionListener(this);
	        buyOutBtn.addActionListener(this);
	        requestList.addListSelectionListener(this);
	        messageBtn.addActionListener(this);
	        seeBidsBtn.addActionListener(this);
	        createBidBtn.addActionListener(this);
	        
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
	        



	    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == searchSubjectInput) {
			requestListModel.clear();
			selectedSubject = subjects.get(searchSubjectInput.getSelectedIndex());
			ArrayList<String> bids = selectedSubject.getBidIds();
			requestListModel.addAll(bids);
			requestDetails.setText("");
        }else if(e.getSource() == buyOutBtn) {
        	try {
        		Contract contract = new Contract(currentUser, selectedBid);
        		Contract addedContract = Application.contracts.addContract(contract);
        		Application.contracts.signContract(selectedBid,addedContract);
				Application.bids.closeBid(selectedBid);
				JOptionPane.showMessageDialog(this, "Request bought out");
			}catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error buying out request");
			}
        }else if (e.getSource() ==  createBidBtn) {
        	try {
        		Contract contract = new Contract(currentUser, selectedBid, hoursPerSessionInput.getSelectedItem().toString(), sessionsPerWeekInput.getSelectedItem().toString(), ratePerSessionInput.getText());
        		Contract addedContract = Application.contracts.addContract(contract);
				JOptionPane.showMessageDialog(this, "Bid Added. You bid ID is : " + addedContract.getId());
			}catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error creating a bid");
			}
        }
        else if(e.getSource() == messageBtn) {
        	new MessagesWindow(currentUser,selectedBid.getId());
        }else if(e.getSource() == seeBidsBtn) {
        	new RequestWindow(currentUser,selectedBid);
        }

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == requestList) {
			String id = requestList.getSelectedValue();
			if(id != null) {
				try {
					selectedBid = Application.bids.getBid(id);
					requestDetails.setText(selectedBid.toString());
					boolean qualified = selectedBid.getCompetency() <= currentUser.getCompetencyLevel(selectedSubject.getId());
					boolean closed = selectedBid.getDateClosedDown() == null;
					boolean check = closed && qualified;
					buyOutBtn.setEnabled(check);
					createBidBtn.setEnabled(check);
					messageBtn.setEnabled(check);
					seeBidsBtn.setEnabled(check);

				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Unable to get bid details");
				}
			}
			
		}
	}

}
