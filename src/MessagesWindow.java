
public class MessagesWindow {
	
	MessagesWindow(User currentUser, String bidId){
		MessagesLayout window = new MessagesLayout(currentUser, bidId);
        window.setTitle("Messages For Request " + bidId);
        window.setVisible(true);
        window.setBounds(100, 100, 500, 450);
        window.setResizable(false);
	}
	
}
