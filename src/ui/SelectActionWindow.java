package ui;
import model.User;

public class SelectActionWindow {
	
	SelectActionWindow(User currentUser){
		
		SelectActionLayout window = new SelectActionLayout(currentUser);
        window.setTitle("Welcome Back");
        window.setVisible(true);
        window.setBounds(100, 100, 420, 270);
        window.setResizable(false);
	}
}
