
public class RequestWindow {
	
	RequestWindow(User currentUser, String requestId){
		RequestLayout window = new RequestLayout(currentUser,requestId);
        window.setTitle("Request " + requestId);
        window.setVisible(true);
        window.setBounds(100, 100, 510, 700);
        window.setResizable(false);
	}
}
