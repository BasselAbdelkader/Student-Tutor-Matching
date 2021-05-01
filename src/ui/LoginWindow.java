package ui;

public class LoginWindow {
	
    public LoginWindow() {
        LoginLayout window = new LoginLayout();
        window.setTitle("Login");
        window.setVisible(true);
        window.setBounds(10, 10, 400, 250);
        window.setResizable(false);
    }

}