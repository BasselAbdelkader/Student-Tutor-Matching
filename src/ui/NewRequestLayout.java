package ui;
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

import apiservices.BidsAPI;
import apiservices.SubjectAPI;
import model.Bid;
import model.Subject;
import model.User;

public class NewRequestLayout extends WindowLayout implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//Instance Vars
	private User currentUser;
	private ArrayList<Subject> subjects;
	
	//Labels
	private JLabel requestTypeLabel ;
	private JLabel subjectLabel;
	private JLabel competencyLabel;
	private JLabel hoursPerSessionLabel;
	private JLabel sessionsPerWeekLabel;
	private JLabel ratePerSessionLabel;
	
	//Inputs
	private JComboBox<String> requestTypeInput;
	private JComboBox subjectInput;
	private JComboBox<String> competencyInput;
	private JComboBox<String> hoursPerSessionInput;
	private JComboBox<String> sessionsPerWeekInput;
	private JTextField ratePerSessionInput;
	
	//Buttons
	private JButton createRequestBtn;
	
	public NewRequestLayout(User currentUser) {
		super();
		this.currentUser = currentUser;
	}
	
	@Override
	protected void initElements() {

		//Labels
		requestTypeLabel = new JLabel("Bid Type:");
		subjectLabel = new JLabel("Subject :");
		competencyLabel = new JLabel("Competency :");
		hoursPerSessionLabel = new JLabel("Hours per session :");
		sessionsPerWeekLabel = new JLabel("Sessions per week :");
		ratePerSessionLabel = new JLabel("Rate per session :");
		
		//Constants
		String[] type = {"open","closed"};
		String[] numbers = {"1","2","3","4","5","6","7"};
		
		//Inputs
		requestTypeInput = new JComboBox<String>(type) ;
		subjectInput = new JComboBox<>();
		competencyInput = new JComboBox<String>(numbers);
		hoursPerSessionInput = new JComboBox<String>(numbers);
		sessionsPerWeekInput = new JComboBox<String>(numbers);
		ratePerSessionInput = new JTextField();
		
		//Buttons
		createRequestBtn = new JButton("Create Request");
	}

	@Override
	protected void setElementBounds() {
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
	}


	@Override
	protected void addToContainer() {
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
	protected void bindActionListeners() {
		createRequestBtn.addActionListener(this);
	}
	
	
	@Override
	protected void init() {
		try {
			subjects = SubjectAPI.getInstance().getAllSubjects();
			for (Subject s : subjects) {
				subjectInput.addItem(s.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error getting subjects");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createRequestBtn) {
			Bid b = new Bid(currentUser,
					requestTypeInput.getSelectedItem().toString(),
					subjects.get(subjectInput.getSelectedIndex()).getId(),
					Integer.parseInt(competencyInput.getSelectedItem().toString()),
					hoursPerSessionInput.getSelectedItem().toString(),
					sessionsPerWeekInput.getSelectedItem().toString(),
					ratePerSessionInput.getText()
					);
			try {
				String id = BidsAPI.getInstance().addBid(b);
				new RequestWindow(currentUser, BidsAPI.getInstance().getBid(id));
				dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}



	

}
