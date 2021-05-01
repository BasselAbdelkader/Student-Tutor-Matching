package ui;
import model.User;

public class OpenBidsWindow {
	
	OpenBidsWindow(User currentUser) {
		OpenBidsLayout window = new OpenBidsLayout(currentUser);
        window.setTitle("See Requests");
        window.setVisible(true);
        window.setBounds(100, 100, 500, 750);
        window.setResizable(false);
    }
    
    

}