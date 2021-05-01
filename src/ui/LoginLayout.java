package ui;
import javax.swing.*;

import apiservices.UserAPI;
import model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    
    @Override
    protected void initElements() {
    	
    	userLabel = new JLabel("USERNAME");
    	passwordLabel = new JLabel("PASSWORD");
    	
    	userTextField = new JTextField();
    	passwordField = new JPasswordField();
    	
    	loginButton = new JButton("LOGIN");
    	
    }

	@Override
	protected void setElementBounds() {
		container.setLayout(null);
    	userLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 100, 100, 30);
        userTextField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 100, 150, 30);
        loginButton.setBounds(200, 150, 100, 30);
	}

	@Override
	protected void addToContainer() {
		container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(loginButton);
	}

	@Override
	protected void bindActionListeners() {
		loginButton.addActionListener(this);
	}
	
	@Override
	protected void init() {
		 userTextField.setText("nishp94");
		 passwordField.setText("nishp94");
	}
	
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

