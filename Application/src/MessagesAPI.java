import java.sql.Timestamp;
import java.time.Instant;

public class MessagesAPI extends APIWrapper {

	public MessagesAPI(String api_key, String url) {
		super(api_key, url+"/message");
		// TODO Auto-generated constructor stub
	}
	
	public void sendMessage(Bid bid, User user, String content) throws Exception {
		String  bidId = bid.getId();
		String userId = user.getId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		
		String jsonString = "{" +
				"\"bidId\":\"" + bidId + "\"," +
				"\"posterId\":\"" + userId + "\"," +
				"\"datePosted\":\"" + instant + "\"," +
				"\"content\":\"" + content + "\"," +
			    "\"additionalInfo\":{}" +
			 "}";
		
		super.postHttpRequest(jsonString,url);
		
	}

}
