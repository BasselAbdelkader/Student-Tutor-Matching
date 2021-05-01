package model;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message {
	private String bidId;
	private String posterId;
	private String contractId;
	
	private String posterUserName;
	private String datePosted;
	private String dateLastEdited;
	private String content;
	
	public Message(Bid b, User poster, Contract c, String content) {
		this.bidId = b.getId();
		this.posterId = poster.getId();
		this.contractId = c.getId();
		this.posterUserName = poster.getUserName();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.datePosted = timestamp.toInstant().toString();
		this.dateLastEdited = this.datePosted;
		this.content = content;
	}
	
	public Message(String jsonString) throws Exception {
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.posterId = jsonNode.get("poster").get("id").textValue();
		this.posterUserName = jsonNode.get("poster").get("userName").textValue();
		this.datePosted = jsonNode.get("datePosted").textValue();
		if (jsonNode.get("dateLastEdited") != null) {
			this.dateLastEdited = jsonNode.get("dateLastEdited").textValue();
		}
		if (jsonNode.get("additionalInfo").get("contractId") != null) {
			this.contractId = jsonNode.get("additionalInfo").get("contractId").textValue();
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
	
	public String getContractId() {
		return contractId;
	}

}
