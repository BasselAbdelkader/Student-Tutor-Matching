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
public class LoginLayout extends WindowLayout  {

	private static final long serialVersionUID = 1L;
	
	//Labels
    JLabel userLabel;
    JLabel passwordLabel;
    
    //Inputs
    JTextField userTextField;
   

	JPasswordField passwordField;
    
    //Buttons
    JButton loginButton;

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
	
	 public JTextField getUserTextField() {
			return userTextField;
	}

	public JPasswordField getPasswordField() {
			return passwordField;
	}

	public JButton getLoginButton() {
			return loginButton;
	}

}

