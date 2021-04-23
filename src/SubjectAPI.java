import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class SubjectAPI extends APIWrapper {
	
	public SubjectAPI(String api_key, String rootUrl) {
		super(api_key, rootUrl + "/subject");
	}
	
	public ArrayList<Subject> getAllSubjects() throws Exception {

	    String response = super.getHttpRequest(url + "?fields=bids&fields=competencies");
	    
	    ObjectNode[] jsonNodes = new ObjectMapper().readValue(response, ObjectNode[].class);
	    ArrayList<Subject> names = new ArrayList<Subject>();
	    
	    for (ObjectNode node: jsonNodes) {
	    	names.add(new Subject(node.toString()));
	    }
		return names;
	}
	
	public ArrayList<String> getBidIDsForSubject(String subjectID) throws Exception {

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
