import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message {
	private String bidId;
	private String posterId;
	private String posterUserName;
	private String datePosted;
	private String dateLastEdited;
	private String content;
	
	public Message(String bidId, User poster, String datePosted, String dateLastEdited, String content) {
		this.bidId = bidId;
		this.posterId = poster.getId();
		this.posterUserName = poster.getUserName();
		this.datePosted = datePosted;
		this.dateLastEdited = dateLastEdited;
		this.content = content;
	}
	
	public Message(String jsonString) throws Exception {
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
//		this.bidId = jsonNode.get("bidId").textValue();
		this.posterId = jsonNode.get("poster").get("id").textValue();
		this.posterUserName = jsonNode.get("poster").get("userName").textValue();
		this.datePosted = jsonNode.get("datePosted").textValue();
		if (jsonNode.get("dateLastEdited") != null) {
			this.dateLastEdited = jsonNode.get("dateLastEdited").textValue();
		}
		
		this.content = jsonNode.get("content").textValue();
		
	}
	
	public String toString() {
		String out = datePosted + " (" + posterUserName + "): " + content;
		return out;
	}

	public String getBidId() {
		return bidId;
	}

	public String getPosterId() {
		return posterId;
	}

	public String getDatePosted() {
		return datePosted;
	}

	public String getDateLastEdited() {
		return dateLastEdited;
	}

	public String getContent() {
		return content;
	}
	

}
