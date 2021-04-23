import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Bid {
	private String initiatorId;
	private String initiatorUserName;
	private String id;
	private String type;
	private String dateCreated;
	private String dateClosedDown;
	private String subjectId;
	private ArrayList<Message> messages = new ArrayList<Message>();
	
	public Bid(String initiatorId, User user, String type, String dateCreated, String subjectId) {
		super();
		this.initiatorId = user.getId();
		this.initiatorUserName = user.getUserName();
		this.type = type;
		this.dateCreated = dateCreated;
		this.subjectId = subjectId;
	}

	public Bid(String jsonString) throws Exception {
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.type = jsonNode.get("type").textValue();
		this.dateCreated = jsonNode.get("dateCreated").textValue();
		this.initiatorId = jsonNode.get("initiator").get("id").textValue();
		this.initiatorUserName = jsonNode.get("initiator").get("userName").textValue();
		this.subjectId = jsonNode.get("subject").get("id").textValue();
		if (jsonNode.get("dateClosedDown") != null) {
			this.dateClosedDown = jsonNode.get("dateClosedDown").textValue();
		}
		
		JsonNode bidsNode =  jsonNode.get("messages");
		for (JsonNode b : bidsNode) {
			System.out.println(b);
			this.messages.add(new Message(b.toString()));
		}
	}
	
	public String getInitiatorId() {
		return initiatorId;
	}
	public String getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public String getDateClosedDown() {
		return dateClosedDown;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public ArrayList<Message> getMessageIds() {
		return messages;
	}
	
	public String toString() {
		String out  = "";
	    out = out + type + " bid \n";
	    out = out + "Initiator: " + this.initiatorUserName + "\n";
	    out = out + "Date Created: " + this.dateCreated + "\n";
	    out = out + "Date Closed: " + this.dateClosedDown + "\n";
	    return out;
	}
	
}
