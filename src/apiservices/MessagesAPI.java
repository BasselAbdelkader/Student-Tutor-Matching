package apiservices;
import model.Message;
/**
 * MessagesAPI Class
 * @author Andrew Pang
 * This is the API class responsible for connecting to the API and performing operation for messages requests.
 * This class contain the methods to send messages in a controlled manner. 
 */
public class MessagesAPI extends APIWrapper {

	private static MessagesAPI instance;
	
	/**
	 * Only ONE MessagesAPI class can exist as we only need one connection for messages to the API Service
	 */
	public static MessagesAPI getInstance() {
		if(instance == null) { 
			instance = new MessagesAPI();
		}
		return instance;
	}

	private MessagesAPI() {
		super(rootUrl + "/message");
	}
	
	/**
	 * Sends a message for a bid in closed bidding
	 * This method converts a Message instance into a JSON string and posts it to the API
	 * @param message The Message instance to be posted to the API
	 * @throws Exception There is an error with the HTTP request
	 */
	public void sendMessage(Message message) throws Exception {
		String  bidId = message.getRequestId();
		String userId = message.getPosterId();
		String contractId = message.getContractId();
		String content = message.getContent();
		String posted = message.getDatePosted();
		
		String jsonString = "{" +
				"\"bidId\":\"" + bidId + "\"," +
				"\"posterId\":\"" + userId + "\"," +
				"\"datePosted\":\"" + posted + "\"," +
				"\"content\":\"" + content + "\"," +
			    "\"additionalInfo\":{" + "\"contractId\":\"" + contractId  + "\"}" +
			 "}";
		
		super.postHttpRequest(jsonString,url);
		
	}

}
