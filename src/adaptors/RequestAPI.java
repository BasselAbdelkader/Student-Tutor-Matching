package adaptors;
import java.sql.Timestamp;
import java.time.Instant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Request;

/**
 * RequestAPI class
 * @author Andrew Pang
 * This is the API class responsible for connecting to the API and performing operation for bid requests.
 * Remember, for the purposes on this assignment unsigned contracts are bids 
 * Bids in the API are treated as bid requests where bidders can bid on these bid requests.
 * This class contain the methods to retrieve, modify, delete and close bid requests in a controlled manner. 
 */
public class RequestAPI extends APIWrapper {
	
	private static RequestAPI instance;
	
	/**
	 * Only ONE BidsAPI class can exist as we only need one connection to the API Service
	 */
	public static RequestAPI getInstance() {
		if(instance == null) { 
			instance = new RequestAPI();
		}
		return instance;
	}
	
	private RequestAPI() {
		super(rootUrl + "/bid");
	}
	
	/**
	 * Add a new bid request method
	 * This method takes in a Request object, convert it to jsonString and post it to the API service. 
	 * @param request Request instance to be added to the API
	 * @return id of the new request added in the API
	 * @throws Exception There is an error with the HTTP request.
	 */ 
	public String addRequest(Request request) throws Exception{
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
	
		String jsonString = "{" +
			      "\"type\":\"" + request.getType() + "\"" + "," +
			      "\"initiatorId\":\"" + request.getInitiatorId() + "\"" + "," +
			      "\"dateCreated\":\"" + instant.toString() + "\"" + "," +
			      "\"subjectId\":\"" + request.getSubjectId() + "\"" + "," +
			      "\"additionalInfo\":{" + 
			      		"\"competency\":"+ request.getCompetency() + "," +
			      		"\"hoursPerSession\":\""+ request.getLessonInfo().getHoursPerSession() + "\"" + "," +
			      		"\"sessionsPerWeek\":\""+ request.getLessonInfo().getSessionsPerWeek()+ "\"" + "," +
			      		"\"ratePerSession\":\""+ request.getLessonInfo().getRatePerSession()+ "\"" + "," +
			      		"\"contractDuration\":\""+ request.getContractDuration()+ "\"" + "," +
			      		"\"contractIds\":[]" +
			      	"}" + 			      
			      "}";

		
		String response = super.postHttpRequest(jsonString, url);
		ObjectNode jsonNode = new ObjectMapper().readValue(response, ObjectNode.class);
		
		return jsonNode.get("id").textValue();
		
	}
	
	/**
	 * Get bid request method
	 * This method gets the request from the API using the id provided as an argument
	 * @param id id of the Request
	 * @return Request instance that mirrors the bid in the API
	 * @throws Exception There is an error with the HTTP request.
	 */
	public Request getRequest(String id) throws Exception {

		String response =  super.getHttpRequest(url + "/" + id + "?fields=messages");

		return new Request(response);
	}
	
	/**
	 * Delete a bid request method
	 * This method deletes the bid request in the api using the id of the provided bid
	 * @param request Request instance that is to be deleted
	 * @throws Exception There is an error with the HTTP request.
	 */
	public void deleteRequest(Request request) throws Exception{
		super.deleteHttpRequest(url + "/" + request.getId());
	}
	
	/**
	 * Close bid request method
	 * This method closes the bid request, preventing further bids to be made on the bid request
	 * This method is typically called after the signContract method in the ContractsAPI
	 * @param request Request instance to be closed
	 * @return the response string for the server
	 * @throws Exception There is an error with the HTTP request.
	 */
	public String closeRequest(Request request) throws Exception{
		String id = request.getId();
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