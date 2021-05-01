package model;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class User {
	
	private String id;
	private String userName;
	private String givenName;
	private String familyName;
	private boolean isTutor;
	private boolean isStudent;
	ArrayList<String> userBidIds = new ArrayList<String>();
	ArrayList<Competency> competencies =  new ArrayList<Competency>();
	
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


	public User(String id, String userName, String givenName, String familyName, boolean isTutor, boolean isStudent) {
		this.id = id;
		this.userName = userName;
		this.givenName = givenName;
		this.familyName = familyName;
		this.isTutor = isTutor;
		this.isStudent = isStudent;
	}
	
	
	public String getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getGivenName() {
		return givenName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public boolean isTutor() {
		return isTutor;
	}
	public boolean isStudent() {
		return isStudent;
	}
	public ArrayList<String> getUserBidIds(){
		return userBidIds;
	}
	public int getCompetencyLevel(String subjectId) {
		for(Competency c : competencies) {
			if (c.getSubjectId().contentEquals(subjectId)) {
				return c.getLevel();
			}
		}
		return 0;
	}
	
	
	


}
