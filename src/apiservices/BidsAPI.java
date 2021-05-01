package apiservices;
import java.sql.Timestamp;
import java.time.Instant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Request;

/**
 * BidsAPI class
 * @author Andrew Pang
 * This is the API class responsible for connecting to the API and performing operation for bid requests.
 * This class contain the methods to retrieve, modify, delete and close bids in a controlled manner. 
 */
public class BidsAPI extends APIWrapper {
	
	private static BidsAPI instance;
	
	/**
	 * Only ONE BidsAPI class can exist as we only need one connection to the API Service
	 */
	public static BidsAPI getInstance() {
		if(instance == null) { 
			instance = new BidsAPI();
		}
		return instance;
	}
	
	private BidsAPI() {
		super(rootUrl + "/bid");
	}
	
	/**
	 * Add a new bid method
	 * This method takes in a Bid object, convert it to jsonString and post it to the API service. 
	 * @param bid Bid instance to be added to the API
	 * @return id of the new bid added in the API
	 * @throws Exception There is an error with the HTTP request.
	 */ 
	public String addBid(Request bid) throws Exception{
		
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
	
	/**
	 * Get bid method
	 * This method gets the bid from the API using the id provided as an argument
	 * @param id id of the Bid
	 * @return Bid instance that mirrors the bid in the API
	 * @throws Exception There is an error with the HTTP request.
	 */
	public Request getBid(String id) throws Exception {

		String response =  super.getHttpRequest(url + "/" + id + "?fields=messages");

		return new Request(response);
	}
	
	/**
	 * Delete bid method
	 * This method deletes the bid in the api using the id of the provided bid
	 * @param b Bid instance that is to be deleted
	 * @throws Exception There is an error with the HTTP request.
	 */
	public void deleteBid(Request b) throws Exception{
		super.deleteHttpRequest(url + "/" + b.getId());
	}
	
	/**
	 * Close bid method
	 * This method closes the bid request, preventing further bids to be made on the bid request
	 * This method is typically called after the signContract method int he ContractsAPI
	 * @param bid Bid instance to be closed
	 * @return the response string for the server
	 * @throws Exception There is an error with the HTTP request.
	 */
	public String closeBid(Request bid) throws Exception{
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