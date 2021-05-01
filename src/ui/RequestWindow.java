package ui;
import model.Request;
import model.User;
/**
 * This is boot-strapper class for the Request Layout
 * it specifies the properties of the request window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class RequestWindow {
	
	RequestWindow(User currentUser, Request request){
		RequestLayout window = new RequestLayout(currentUser,request);
        window.setTitle("Request " + request.getId());
        window.setVisible(true);
        window.setBounds(100, 100, 510, 700);
        window.setResizable(false);
	}
}
