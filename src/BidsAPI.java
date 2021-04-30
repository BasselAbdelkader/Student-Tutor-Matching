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
	
	public String addBid(Bid bid) throws Exception{
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
	
		String jsonString = "{" +
			      "\"type\":\"" + bid.getType() + "\"" + "," +
			      "\"initiatorId\":\"" + bid.getInitiatorId() + "\"" + "," +
			      "\"dateCreated\":\"" + instant.toString() + "\"" + "," +
			      "\"subjectId\":\"" + bid.getSubjectId() + "\"" + "," +
			      "\"additionalInfo\":{" + 
			      		"\"competency\":"+ bid.getCompetency() + "," +
			      		"\"hoursPerSession\":\""+ bid.getHoursPerSession() + "\"" + "," +
			      		"\"sessionsPerWeek\":\""+ bid.getSessionsPerWeek()+ "\"" + "," +
			      		"\"ratePerSession\":\""+ bid.getRatePerSession()+ "\"" + "," +
			      		"\"contractIds\":[]" +
			      	"}" + 			      
			      "}";

		
		String response = super.postHttpRequest(jsonString, url);
		ObjectNode jsonNode = new ObjectMapper().readValue(response, ObjectNode.class);
		
		return jsonNode.get("id").textValue();
		
	}
	
	public Bid getBid(String id) throws Exception {

		String response =  super.getHttpRequest(url + "/" + id + "?fields=messages");

		return new Bid(response);
	}
	
	public void deleteBid(Bid b) throws Exception{
		super.deleteHttpRequest(url + "/" + b.getId());
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