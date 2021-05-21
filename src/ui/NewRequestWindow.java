package ui;
import model.User;
/**
 * This is boot-strapper class for the New Request Layout
 * it specifies the properties of the new request window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class NewRequestWindow {


	public NewRequestWindow(User currentUser) {
		NewRequestLayout window = new NewRequestLayout(currentUser);
        window.setTitle("Create new request");
        window.setVisible(true);
        window.setBounds(100, 100, 440, 390);
        window.setResizable(false);
	}
	
}
