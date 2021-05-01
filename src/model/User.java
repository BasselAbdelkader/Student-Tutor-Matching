package model;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * User class
 * @author Andrew Pang
 * This class represents a user. It contains basic details about the user such as competency and inititated bids. 
 */
public class User {
	
	private String id;
	private String userName;
	private String givenName;
	private String familyName;
	private boolean isTutor;
	private boolean isStudent;
	ArrayList<String> userBidIds = new ArrayList<String>();
	ArrayList<Competency> competencies =  new ArrayList<Competency>();
	
	/**
	 * This constructors creates a USer instance from a JSON string
	 * @param jsonString JSON String
	 * @throws Exception There is an error parsing the JSON string
	 */
	public User(String jsonString) throws Exception{
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.userName = jsonNode.get("userName").textValue();
		this.givenName = jsonNode.get("givenName").textValue();
		this.familyName = jsonNode.get("familyName").textValue();
		this.isStudent = jsonNode.get("isStudent").booleanValue();
		this.isTutor = jsonNode.get("isTutor").booleanValue();
		
		JsonNode bidsNode =  jsonNode.get("initiatedBids");
		if(bidsNode != null) {
			for (JsonNode b : bidsNode) {
				this.userBidIds.add(b.get("id").textValue());
			}
		}
		
		JsonNode compNode =  jsonNode.get("competencies");
		if(compNode != null) {
			for (JsonNode c : compNode) {
				this.competencies.add(new Competency(c.toString()));
			}
		}
		
	}	
	
	/**
	 * Returns the ID of the user
	 * @return user ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Returns the username of the user
	 * @return username
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Returns the user's given name
	 * @return user's given name
	 */
	public String getGivenName() {
		return givenName;
	}
	
	/**
	 * Returns the family name of the user
	 * @return user's family name
	 */
	public String getFamilyName() {
		return familyName;
	}
	
	/**
	 * Check if user is a tutor
	 * @return true if user is tutor, false otherwise
	 */
	public boolean isTutor() {
		return isTutor;
	}
	
	/**
	 * Check if user is a student
	 * @return true if user is student, false otherwise
	 */
	public boolean isStudent() {
		return isStudent;
	}
	
	/**
	 * Gets the request Ids of requests initiated by the user
	 * @return list of request Ids
	 */
	public ArrayList<String> getUserBidIds(){
		return userBidIds;
	}
	
	/**
	 * Gets the competency level of a user in a specific subject
	 * @param subjectId the subject ID to get the competency level of
	 * @return list of request IDs
	 */
	public int getCompetencyLevel(String subjectId) {
		for(Competency c : competencies) {
			if (c.getSubjectId().contentEquals(subjectId)) {
				return c.getLevel();
			}
		}
		return 0;
	}
	
	
	


}
