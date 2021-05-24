package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import apiservices.UserAPI;
import model.User;
import ui.LoginLayout;

/**
 * This is boot-strapper class for the Login Layout
 * it specifies the properties of the login window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class LoginWindow extends WindowController {
	
	LoginLayout window;
	
    public LoginWindow() {
    	super(new LoginLayout(),"Login",400,250);
    	this.window = (LoginLayout) super.window;
    	bindActionListeners();
        init();
    }
    
    @Override
    protected void bindActionListeners() {
    	window.getLoginButton().addActionListener(new ActionListener() {
    		@Override
    	    public void actionPerformed(ActionEvent e) {
    			String userText;
	            String pwdText;
	            userText = window.getUserTextField().getText();
	            pwdText = new String(window.getPasswordField().getPassword());
	            
	            User currentUser = null;
				try {
					currentUser = UserAPI.getInstance().login(userText, pwdText);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(window, "Some error coccured when connecting to t he API");
				}
	            		
	            if (currentUser != null) {
	            	new SelectActionWindow(currentUser);
	                closeWindow();
	                JOptionPane.showMessageDialog(window, "Login Successful");
	                
	            } else {
	                JOptionPane.showMessageDialog(window, "Invalid Username or Password");
	            }
    	    }
    	});
    }

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		 window.getUserTextField().setText("nishp94");
		 window.getPasswordField().setText("nishp94");
	}

}