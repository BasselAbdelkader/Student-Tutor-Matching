
public class MessagesWindow {
	
	MessagesWindow(User currentUser, String bidId, String contractId){
		MessagesLayout window = new MessagesLayout(currentUser, bidId, contractId);
        window.setTitle("Messages For Request " + bidId);
        window.setVisible(true);
        window.setBounds(100, 100, 500, 840);
        window.setResizable(false);
	}
	
}
