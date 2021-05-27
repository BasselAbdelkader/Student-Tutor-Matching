package model;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message {
	
	private String requestId;
	private String posterId;
	private String contractId;
	private String posterUserName;
	private String datePosted;
	private String dateLastEdited;
	private String content;
	
	/**
	 * Create a new message 
	 * This method automatically sets the posted date of the message to the system date when the instance once it is created
	 * @param request The bid request it is involved in
	 * @param poster The poster's user instance of the message
	 * @param contract The contract ( or bid if unsigned) that the message is involved in
	 * @param content The content of the message
	 */
	public Message(Request request, User poster, Contract contract, String content) {
		this.requestId = request.getId();
		this.posterId = poster.getId();
		this.contractId = contract.getId();
		this.posterUserName = poster.getUserName();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.datePosted = timestamp.toInstant().toString();
		this.dateLastEdited = this.datePosted;
		this.content = content;
	}
	
	/**
	 * This method constructs a message from a JSON string
	 * @param jsonString JSON String
	 * @throws Exception There is an error parsing the JSON String
	 */
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

	/**
	 * Get the bid request id that the message is involved in
	 * @return request id of the message
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * Get the poster's user id that the message is involved in
	 * @return user id of the message poster
	 */
	public String getPosterId() {
		return posterId;
	}
	
	/**
	 * Get the date that the message is posted
	 * @return ISO 8601 date of the date the request is posted
	 */
	public String getDatePosted() {
		return datePosted;
	}
	
	/**
	 * Get the date that the message is last edited
	 * @return ISO 8601 date of the date the request is last edited
	 */
	public String getDateLastEdited() {
		return dateLastEdited;
	}
	
	/**
	 * Get the content of the message
	 * @return Contents of the message
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Get the contract id that the message is involved in
	 * @return Contract id of the message
	 */
	public String getContractId() {
		return contractId;
	}
	
	public String toString() {
		String out = getDatePosted() + " (" + posterUserName + "): " + getContent();
		return out;
	}
}
