package model;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * Request Class
 * @author Andrew Pang
 * This class represents a subject and contain detailed info on the subject including its active requests. 
 *
 */
public class Subject {
	
	private String id;
	private String name;
	private String description;
	private ArrayList<String> allRequestsIds = new ArrayList<String>();
	private ArrayList<String> openRequestIds = new ArrayList<String>();
	private String competencies;
	
	/**
	 * This constructor construct a subject instance from a JSON String
	 * @param jsonString JSON String
	 * @throws Exception There is a n error parsing the JSON String
	 */
	public Subject(String jsonString) throws Exception{
		
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.name = jsonNode.get("name").textValue();
		this.description = jsonNode.get("description").textValue();
		
		
		this.competencies = jsonNode.get("competencies").textValue();
		
		JsonNode bidsNode =  jsonNode.get("bids");
		if (bidsNode != null) {
			for (JsonNode b : bidsNode) {
				if(b.get("dateClosedDown").isNull()) {
					this.openRequestIds.add(b.get("id").textValue());
				}
				this.allRequestsIds.add(b.get("id").textValue());
			}
		}
	}
	
	/**
	 * Get the request id for the subject that are not yet closed 
	 * @return list of request IDs
	 */
	public ArrayList<String> getOpenRequestIds() {
		return openRequestIds;
	}

	public String toString() {
		String out = "";
		out = out + getName() + "\n";
		out = out + getDescription() + "\n";
		return out;
	}
	
	/**
	 * Get the id of the subject
	 * @return subject ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the name of the subject
	 * @return subject name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the description fo the subject
	 * @return subject description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get all the request IDs for the subject (including closed request)
	 * @return List of request id of all requests
	 */
	public ArrayList<String> getAllRequestIds() {
		return allRequestsIds;
	}


	public String getCompetencies() {
		return competencies;
	}

}
