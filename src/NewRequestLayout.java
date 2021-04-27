import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class NewRequestLayout extends JFrame implements ActionListener {
	User currentUser = null;
	ArrayList<Subject> subjects = null;
	
	Container container = getContentPane();
	
	JLabel requestTypeLabel = new JLabel("Bid Type:");
	JLabel subjectLabel = new JLabel("Subject :");
	JLabel competencyLabel = new JLabel("Competency :");
	JLabel hoursPerSessionLabel = new JLabel("Hours per session :");
	JLabel sessionsPerWeekLabel = new JLabel("Sessions per week :");
	JLabel ratePerSessionLabel = new JLabel("Rate per session :");
	
	String[] type = {"open","closed"};
	String[] numbers = {"1","2","3","4","5","6","7"};
	
	JComboBox requestTypeInput = new JComboBox(type) ;
	JComboBox subjectInput = null;
	JComboBox competencyInput = new JComboBox(numbers);
	JComboBox hoursPerSessionInput = new JComboBox(numbers);
	JComboBox sessionsPerWeekInput = new JComboBox(numbers);
	
	JTextField ratePerSessionInput = new JTextField();
	JButton createRequestBtn = new JButton("Create Request");
	
	public NewRequestLayout(User currentUser) {
		this.currentUser = currentUser;
		try {
			subjects = Application.subjects.getAllSubjects();
			ArrayList<String> names = new ArrayList<String>();
			for (Subject s : subjects) {
				names.add(s.getName());
			}
			subjectInput = new JComboBox(names.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error getting subjects");
		}
		
		container.setLayout(null);
		
		requestTypeLabel.setBounds(10, 10, 150, 30);
		subjectLabel.setBounds(10, 50, 150, 30);
		competencyLabel.setBounds(10, 90, 150, 30);
		hoursPerSessionLabel.setBounds(10, 130, 150, 30);
		sessionsPerWeekLabel.setBounds(10, 170, 150, 30);
		ratePerSessionLabel.setBounds(10, 210, 150, 30);
		
		requestTypeInput.setBounds(160, 10, 250, 30);
		subjectInput.setBounds(160, 50, 250, 30);
		competencyInput.setBounds(160, 90, 250, 30);
		hoursPerSessionInput.setBounds(160, 130, 250, 30);
		sessionsPerWeekInput.setBounds(160, 170, 250, 30);
		ratePerSessionInput.setBounds(160, 210, 250, 30);
		
		createRequestBtn.setBounds(10, 250, 400, 30);

		
		createRequestBtn.addActionListener(this);

		
		container.add(requestTypeLabel);
        container.add(subjectLabel);
        container.add(competencyLabel);
        container.add(hoursPerSessionLabel);
        container.add(sessionsPerWeekLabel);
        container.add(hoursPerSessionLabel);
        container.add(ratePerSessionLabel);
        container.add(requestTypeInput);
        container.add(subjectInput);
        container.add(competencyInput);
        container.add(hoursPerSessionInput);
        container.add(sessionsPerWeekInput);
        container.add(ratePerSessionInput);
        container.add(createRequestBtn);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == createRequestBtn) {

			Bid b = new Bid(currentUser,
					requestTypeInput.getSelectedItem().toString(),
					subjects.get(subjectInput.getSelectedIndex()).getId(),
					competencyInput.getSelectedItem().toString(),
					hoursPerSessionInput.getSelectedItem().toString(),
					sessionsPerWeekInput.getSelectedItem().toString(),
					ratePerSessionInput.getText()
					);
			try {
				String id = Application.bids.addBid(b);
				new RequestWindow(currentUser, Application.bids.getBid(id));
				dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			
		}

	}

}
