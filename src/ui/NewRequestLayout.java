package ui;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * This is the layout for a new request window. 
 * This window is opened when the user selects the new request option and want to input his/her requirements for the request
 * @author Andrew Pang
 *
 */
public class NewRequestLayout extends WindowLayout{
	
	private static final long serialVersionUID = 1L;
	
	//Labels
	private JLabel requestTypeLabel ;
	private JLabel subjectLabel;
	private JLabel competencyLabel;
	private JLabel hoursPerSessionLabel;
	private JLabel sessionsPerWeekLabel;
	private JLabel ratePerSessionLabel;
	private JLabel contractDurationLabel;
	
	//Inputs
	private JComboBox<String> requestTypeInput;
	private JComboBox subjectInput;
	private JComboBox<String> competencyInput;
	private JComboBox<String> hoursPerSessionInput;
	private JComboBox<String> sessionsPerWeekInput;
	private JTextField ratePerSessionInput;
	private JComboBox<String> contractDurationInput;
	
	//Buttons
	private JButton createRequestBtn;
	
	public NewRequestLayout() {
		super();
	}
	
	/**
     * Instantiate the View Elements to be added to the Layout
     */
	@Override
	protected void initElements() {

		//Labels
		requestTypeLabel = new JLabel("Bid Type:");
		subjectLabel = new JLabel("Subject :");
		competencyLabel = new JLabel("Competency :");
		hoursPerSessionLabel = new JLabel("Hours per session :");
		sessionsPerWeekLabel = new JLabel("Sessions per week :");
		ratePerSessionLabel = new JLabel("Rate per session :");
		contractDurationLabel = new JLabel("Contract Duration (Months):");
		
		//Constants
		String[] type = {"open","closed"};
		String[] numbers = {"1","2","3","4","5","6","7"};
		String[] months = {"6","3","12","24"};
		
		//Inputs
		requestTypeInput = new JComboBox<String>(type) ;
		subjectInput = new JComboBox<>();
		competencyInput = new JComboBox<String>(numbers);
		hoursPerSessionInput = new JComboBox<String>(numbers);
		sessionsPerWeekInput = new JComboBox<String>(numbers);
		ratePerSessionInput = new JTextField();
		contractDurationInput =  new JComboBox<String>(months);
		
		//Buttons
		createRequestBtn = new JButton("Create Request");
	}
	
	/**
    * Set the positions of the View elements to be added
    */
	@Override
	protected void setElementBounds() {
		requestTypeLabel.setBounds(10, 10, 150, 30);
		subjectLabel.setBounds(10, 50, 150, 30);
		competencyLabel.setBounds(10, 90, 150, 30);
		hoursPerSessionLabel.setBounds(10, 130, 150, 30);
		sessionsPerWeekLabel.setBounds(10, 170, 150, 30);
		ratePerSessionLabel.setBounds(10, 210, 150, 30);
		contractDurationLabel.setBounds(10, 250, 150, 30);
		requestTypeInput.setBounds(160, 10, 250, 30);
		subjectInput.setBounds(160, 50, 250, 30);
		competencyInput.setBounds(160, 90, 250, 30);
		hoursPerSessionInput.setBounds(160, 130, 250, 30);
		sessionsPerWeekInput.setBounds(160, 170, 250, 30);
		ratePerSessionInput.setBounds(160, 210, 250, 30);
		contractDurationInput.setBounds(160, 250, 150, 30);
		createRequestBtn.setBounds(10, 290, 400, 30);
	}

	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		container.add(requestTypeLabel);
        container.add(subjectLabel);
        container.add(competencyLabel);
        container.add(hoursPerSessionLabel);
        container.add(sessionsPerWeekLabel);
        container.add(hoursPerSessionLabel);
        container.add(ratePerSessionLabel);
        container.add(contractDurationLabel);
        container.add(requestTypeInput);
        container.add(subjectInput);
        container.add(competencyInput);
        container.add(hoursPerSessionInput);
        container.add(sessionsPerWeekInput);
        container.add(ratePerSessionInput);
        container.add(contractDurationInput);
        container.add(createRequestBtn);
	}


	public JComboBox<String> getRequestTypeInput() {
		return requestTypeInput;
	}

	public JComboBox getSubjectInput() {
		return subjectInput;
	}

	public JComboBox<String> getCompetencyInput() {
		return competencyInput;
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

	public JButton getCreateRequestBtn() {
		return createRequestBtn;
	}
	
	
}
