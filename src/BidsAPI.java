import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BidsAPI extends APIWrapper {

	public BidsAPI(String api_key,String rootUrl) {
		super(api_key, rootUrl + "/bid");
	}
	
	public Bid getBid(String id) throws Exception {

		String response =  super.getHttpRequest(url + "/" + id + "?fields=messages");

		return new Bid(response);
	}
	
	public ArrayList<Message> getBidMessages(String id) throws Exception{
		String response = super.getHttpRequest(url + "/" + id + "?fields=messages");
		ObjectNode jsonNode = new ObjectMapper().readValue(response, ObjectNode.class);
		JsonNode jsonNodeMsg = jsonNode.get("messages");
		ArrayList<Message> messages = new ArrayList<Message>();
		for (JsonNode each : jsonNodeMsg) {
			messages.add(new Message(each.textValue()));
		}
		return messages;
		
	}
	
	public String closeBid(Bid bid) throws Exception{
		String id = bid.getId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		
		String closebidurl = url +"/" + id + "/close-down";
		
		String jsonString = "{" +
	      "\"dateClosedDown\":\"" + instant + "\"" +
	      "}";
		
		String response =  super.postHttpRequest(jsonString, closebidurl);
		return response;
		
	}

	

}