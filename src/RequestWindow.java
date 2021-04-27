
public class RequestWindow {
	
	RequestWindow(User currentUser, Bid request){
		RequestLayout window = new RequestLayout(currentUser,request);
        window.setTitle("Request " + request.getId());
        window.setVisible(true);
        window.setBounds(100, 100, 510, 700);
        window.setResizable(false);
	}
}
