//import java.awt.Container;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import javax.swing.DefaultListModel;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//import javax.swing.JTextArea;
//
//import apiservices.ContractsAPI;
//import model.Contract;
//import model.User;
//
//public class ContractDetailLayout extends JFrame implements ActionListener{
//
//	User currentUser = null;
//	Contract contract = null;
//	ArrayList<String> sessions = null;
//	
//	Container container = getContentPane();
//	
//	JLabel contractDetailsLabel = new JLabel("Details of the contract");
//	JLabel paymentInfoLabel = new JLabel("Payment to be made");
//	JLabel sessionListLabel = new JLabel("Sessions");
//	JLabel adjustSessionLabel = new JLabel("Add a session info");
//	JLabel dayInputLabel = new JLabel("Day: ");
//	JLabel timeInputLabel = new JLabel("Time: ");
//	JLabel colonLabel = new JLabel(":");
//	
//	JTextArea contractDetails = new JTextArea();
//	JTextArea paymentDetails = new JTextArea();
//    DefaultListModel<String> sessionListModel = new DefaultListModel<String>();
//    JList<String> sessionsList = new JList<String>(sessionListModel);  
//    
//    String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
//    
//    JComboBox<String> dayInput = new JComboBox<String>(days);
//    JComboBox<String> hoursInput;
//    JComboBox<String> minuteInput;
//
//    JButton addSessionBtn = new JButton("Add Session");
//    
//    public ContractDetailLayout(User currentUser, Contract contract) {
//		this.currentUser = currentUser;
//		this.contract = contract;
//		
//		String[] hours = new String[24];
//		for(int i = 0; i < hours.length; i++) {hours[i] = String.format("%02d", i);}
//		hoursInput = new JComboBox<String>(hours);
//		String[] minutes = new String[60];
//		for(int i = 0; i < minutes.length; i++) { minutes[i] = String.format("%02d", i);;}
//		minuteInput = new JComboBox<String>(minutes);
//		
//		refresh();
//		
//		container.setLayout(null);
//		
//		contractDetailsLabel.setBounds(10, 10, 300, 30);
//		contractDetails.setBounds(10, 50, 470, 150);
//		sessionListLabel.setBounds(10, 210, 300, 30);
//		sessionsList.setBounds(10, 250, 470, 150);
//		adjustSessionLabel.setBounds(10, 410, 300, 30);
//		dayInputLabel.setBounds(10, 450, 50, 30);
//		dayInput.setBounds(60, 450, 50, 30);
//		timeInputLabel.setBounds(150, 450, 50, 30);
//		hoursInput.setBounds(200, 450, 50, 30);
//		colonLabel.setBounds(260, 450, 5, 30);
//		minuteInput.setBounds(270, 450, 50, 30);
//		addSessionBtn.setBounds(350, 450, 100, 30);
//		
//		addSessionBtn.addActionListener(this);
//		
//		
//		container.add(contractDetailsLabel);
//		container.add(contractDetails);
//		container.add(sessionListLabel);
//		container.add(sessionsList);
//		container.add(adjustSessionLabel);
//		container.add(dayInputLabel);
//		container.add(dayInput);
//		container.add(timeInputLabel);
//		container.add(hoursInput);
//		container.add(colonLabel);
//		container.add(minuteInput);
//		container.add(addSessionBtn);
//	}
//    
//    
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == addSessionBtn) {
//			if(contract.getSessions().size() < Integer.parseInt(contract.getSessionsPerWeek())) {
//				String session = dayInput.getSelectedItem().toString() + " " + hoursInput.getSelectedItem().toString() +":" + minuteInput.getSelectedItem().toString(); 
//				refresh();
//			}
//		}
//		
//	}
//	
//	private void refresh() {
//		try {
//			
//			contract = ContractsAPI.getInstance().getContract(contract.getId());
//			contractDetails.setText(contract.toString());
//			sessions = contract.getSessions();
//			sessionListModel.clear();
//			sessionListModel.addAll(sessions);
//		} catch (Exception e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(this, "Error getting subjects");
//		}
//	}
//    
//    
//
//	
//
//}
