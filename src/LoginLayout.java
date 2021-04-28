import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginLayout extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");


    LoginLayout() {
    	container.setLayout(null);
    	
    	userLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 100, 100, 30);
        userTextField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 100, 150, 30);
        loginButton.setBounds(200, 150, 100, 30);
        
        userTextField.setText("nishp94");
        passwordField.setText("nishp94");
        
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(loginButton);
        
        loginButton.addActionListener(this);
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
				currentUser = Application.users.login(userText, pwdText);
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

