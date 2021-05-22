package ui;
import model.User;
/**
 * This is boot-strapper class for the Select Action Layout 
 * it specifies the properties of the select action window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class SelectActionWindow {
	
	SelectActionWindow(User currentUser){
		
		SelectActionLayout window = new SelectActionLayout(currentUser);
        window.setTitle("Welcome Back");
        window.setVisible(true);
        window.setBounds(100, 100, 420, 330);
        window.setResizable(false);
	}
}
