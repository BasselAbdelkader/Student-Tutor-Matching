public class OpenBidsWindow {
	
	OpenBidsWindow(User currentUser) {
		OpenBidsLayout window = new OpenBidsLayout(currentUser);
        window.setTitle("See Bids");
        window.setVisible(true);
        window.setBounds(100, 100, 500, 800);
        window.setResizable(false);
    }
    
    

}