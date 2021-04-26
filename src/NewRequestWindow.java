
public class NewRequestWindow {


	public NewRequestWindow(User currentUser) {
		NewRequestLayout window = new NewRequestLayout(currentUser);
        window.setTitle("Create new request");
        window.setVisible(true);
        window.setBounds(100, 100, 440, 340);
        window.setResizable(false);
	}
	
}
