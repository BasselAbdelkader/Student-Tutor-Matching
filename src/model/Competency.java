package model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Competency {
	private String id;
	private String ownerId;
	private String subjectId;
	private int level;
	
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

	public String getId() {
		return id;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public int getLevel() {
		return level;
	}

}
