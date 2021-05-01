package apiservices;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.Subject;

/**
 * SubjectAPI Class
 * @author Andrew Pang
 * This is the API class responsible for connecting to the API and performing operation for subjects requests.
 * This class contain the methods to get subjects and its details in a controlled manner. 
 */
public class SubjectAPI extends APIWrapper {
	
	private static SubjectAPI instance;
	
	/**
	 * Only ONE SubjectAPI class can exist as we only need one connection for subejcts to the API Service
	 */
	public static SubjectAPI getInstance() {
		if(instance == null) { 
			instance = new SubjectAPI();
		}
		return instance;
	}

	private SubjectAPI() {
		super(rootUrl + "/subject");
	}
	
	/**
	 * Get all available subjects
	 * This function gets all the subjects available in teh API and converts them to Subject instances
	 * @return A list of Subject instance 
	 * @throws Exception There is an error with the HTTP request
	 */
	public ArrayList<Subject> getAllSubjects() throws Exception {

	    String response = super.getHttpRequest(url + "?fields=bids&fields=competencies");
	    
	    ObjectNode[] jsonNodes = new ObjectMapper().readValue(response, ObjectNode[].class);
	    ArrayList<Subject> names = new ArrayList<Subject>();
	    
	    for (ObjectNode node: jsonNodes) {
	    	names.add(new Subject(node.toString()));
	    }
		return names;
	}
	
	/**
	 * Get all the bid request IDs
	 * This function gets all the Bid requests that are currently open for the subject.
	 * Remember a bid request is not a bid. A bid is an unsigned contract. A bidder places bids on a bid request. 
	 * @param subjectID the subject ID fo the subject
	 * @return A list of bid request IDs for the subject
	 * @throws Exception There is an error with the HTTP request
	 */
	public ArrayList<String> getRequestIDsForSubject(String subjectID) throws Exception {

	    String response = super.getHttpRequest(url + "/" + subjectID + "?fields=bids&fields=competencies");
	    System.out.println(response);
	    ObjectNode jsonNode = new ObjectMapper().readValue(response, ObjectNode.class);
	    System.out.println(jsonNode.get("bids"));
	    
	    JsonNode jsonNodeBids = jsonNode.get("bids");
	    ArrayList<String> bidIds = new ArrayList<String>();
	    
	    for (JsonNode node: jsonNodeBids) {
	    	bidIds.add(node.get("id").textValue());
	    }
		return bidIds;
	}
	
	

}
