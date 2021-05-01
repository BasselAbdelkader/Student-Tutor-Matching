package ui;
import javax.swing.*;

import apiservices.UserAPI;
import model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the layout for a login window
 * @author Andrew Pang
 *
 */
public class LoginLayout extends WindowLayout implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//Labels
    JLabel userLabel;
    JLabel passwordLabel;
    
    //Inputs
    JTextField userTextField;
    JPasswordField passwordField;
    
    //Buttons
    JButton loginButton;


    LoginLayout() {
    	super();
    }    
    
    /**
     * Instantiate the View Elements to be added to the Layout
     */
    @Override
    protected void initElements() {
    	
    	userLabel = new JLabel("USERNAME");
    	passwordLabel = new JLabel("PASSWORD");
    	
    	userTextField = new JTextField();
    	passwordField = new JPasswordField();
    	
    	loginButton = new JButton("LOGIN");
    	
    }

    /**
     * Set the positions of the View elements to be added
     */
	@Override
	protected void setElementBounds() {
		container.setLayout(null);
    	userLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 100, 100, 30);
        userTextField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 100, 150, 30);
        loginButton.setBounds(200, 150, 100, 30);
	}
	
	/**
	 * Add the elements to the view container
	 */
	@Override
	protected void addToContainer() {
		container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(loginButton);
	}
	/**
	 * Bid elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		loginButton.addActionListener(this);
	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		 userTextField.setText("nishp94");
		 passwordField.setText("nishp94");
	}
	
	/**
	 * Actions to be performed in the case of a user induced events
	 * @param e The action event
	 */
	@Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = new String(passwordField.getPassword());
            
            User currentUser = null;
			try {
				currentUser = UserAPI.getInstance().login(userText, pwdText);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Some error coccured when connecting to t he API");
			}
            		
            if (currentUser != null) {
            	new SelectActionWindow(currentUser);
                dispose();
                JOptionPane.showMessageDialog(this, "Login Successful");
                
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }

        }
    }

	

}

