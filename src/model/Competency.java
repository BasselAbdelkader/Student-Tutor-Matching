package model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * Competency Class
 * @author Andrew Pang
 * This class is used to hold the competency level of a user is in a specific subject 
 */
public class Competency {
	
	private String id;
	private String ownerId;
	private String subjectId;
	private int level;
	
	/**
	 * This constructor constructs a competency instance from a jsonString
	 * @param jsonString JSON string
	 * @throws Exception There is an error parsing the JSON string
	 */
	public Competency(String jsonString) throws Exception {
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.subjectId = jsonNode.get("subject").get("id").textValue();
		this.level = jsonNode.get("level").intValue();
		JsonNode ownernode =  jsonNode.get("owner");
		if(ownernode != null) {
			this.ownerId = ownernode.get("id").textValue();
		}
	}
	
	/**
	 * Get the id of the competency
	 * @return the competency id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Get the id of the owner of the the competency
	 * @return competency owner's id
	 */
	public String getOwnerId() {
		return ownerId;
	}
	
	/**
	 * Get the id of the subject of the competency
	 * @return competency's subject's id
	 */
	public String getSubjectId() {
		return subjectId;
	}
	
	/**
	 * Get the competency level of the owner in the subject
	 * @return competentcy level of the owner in the subject
	 */
	public int getLevel() {
		return level;
	}

}
