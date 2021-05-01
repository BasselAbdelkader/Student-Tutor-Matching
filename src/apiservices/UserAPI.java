package apiservices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.User;

public class UserAPI extends APIWrapper {
	
	private User currentUser = null;
	private String currentUserJWT = null;
	
	private static UserAPI instance;
	
	public static UserAPI getInstance() {
		if(instance == null) { 
			instance = new UserAPI();
		}
		return instance;
	}

	private UserAPI() {
		super(rootUrl + "/user");
	}
	
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
	
//	public User getUserById(String id) throws Exception {
//		String response = super.getHttpRequest(url + "/" + id + "?fields=inititatedBids&fields=competencies.subject");
//		return new User(response);
//	}
	public User getCurrentUser() {
		return currentUser;
	}
	
	
	

}
