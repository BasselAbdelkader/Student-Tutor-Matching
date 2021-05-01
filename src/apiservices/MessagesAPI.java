package apiservices;
import java.sql.Timestamp;
import java.time.Instant;

import model.Message;

public class MessagesAPI extends APIWrapper {

	private static MessagesAPI instance;
	
	public static MessagesAPI getInstance() {
		if(instance == null) { 
			instance = new MessagesAPI();
		}
		return instance;
	}

	private MessagesAPI() {
		super(rootUrl + "/message");
	}
	
	public void sendMessage(Message message) throws Exception {
		String  bidId = message.getBidId();
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
