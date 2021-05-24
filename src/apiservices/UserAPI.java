package apiservices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.User;

/**
 * UserAPI Class
 * @author Andrew Pang
 * This is the API class responsible for connecting to the API and performing operation for user related requests.
 * This class contain the methods to log users in, in a controlled manner. 
 */
public class UserAPI extends APIWrapper {
	
	private User currentUser = null;
	private String currentUserJWT = null;
	
	private static UserAPI instance;
	
	/**
	 * Only ONE UserAPI class can exist as we only need one connection for users to the API Service
	 */
	public static UserAPI getInstance() {
		if(instance == null) { 
			instance = new UserAPI();
		}
		return instance;
	}

	private UserAPI() {
		super(rootUrl + "/user");
	}
	
	/**
	 * Login method
	 * This method allows the user to log in and returns a user instance token that can be used to identify the user
	 * @param uname Username of the user
	 * @param pwrd Password of the user
	 * @return User instance that can be used to identify the current Logged in user
	 * @throws Exception There is an error wioth the HTTP request.
	 */
	public User login(String uname, String pwrd) throws Exception {
		
		String loginUrl = url + "/login?jwt=true";
		
		String jsonString = "{" +
	      "\"userName\":\"" + uname + "\"," +
	      "\"password\":\"" + pwrd + "\"" +
	      "}";
		
		currentUserJWT = super.postHttpRequest(jsonString, loginUrl);
		
		if (currentUserJWT != null) {
			String response = super.getHttpRequest(url + "?fields=initiatedBids&fields=competencies.subject");

		    ObjectNode[] jsonNodes = new ObjectMapper().readValue(response, ObjectNode[].class);

		    for (ObjectNode node: jsonNodes) {
		      if( node.get("userName").textValue().contentEquals(uname)) {
		    	  System.out.println(node.asText());
		    	  currentUser = new User(node.toString());
		    	  break;
		      }
		    }
		}

	    return  currentUser;

	}

	public User getUserByID(String id) throws Exception{
		String response = super.getHttpRequest(url + "/" + id + "?fields=initiatedBids&fields=competencies.subject");
		return new User(response);
	}
	




	
//	public User getCurrentUser() {
//		return currentUser;
//	}
	
	
	

}
