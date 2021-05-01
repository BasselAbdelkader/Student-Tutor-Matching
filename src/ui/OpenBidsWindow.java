package ui;
import model.User;
/**
 * This is boot-strapper class for the Open Bids Layout
 * it specifies the properties of the open bids window window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class OpenBidsWindow {
	
	OpenBidsWindow(User currentUser) {
		OpenBidsLayout window = new OpenBidsLayout(currentUser);
        window.setTitle("See Requests");
        window.setVisible(true);
        window.setBounds(100, 100, 500, 750);
        window.setResizable(false);
    }
    
    

}