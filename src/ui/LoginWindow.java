package ui;

/**
 * This is boot-strapper class for the Login Window
 * it specifeis the properties of the login window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class LoginWindow {
	
    public LoginWindow() {
        LoginLayout window = new LoginLayout();
        window.setTitle("Login");
        window.setVisible(true);
        window.setBounds(10, 10, 400, 250);
        window.setResizable(false);
    }

}