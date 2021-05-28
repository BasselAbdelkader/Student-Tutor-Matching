package model;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * Request Class
 * @author Andrew Pang
 * This class represents a bid request. 
 * Whenever a student posts a request, a Request instance is created.
 * IMPORTANT: A request is sent to the APi as a "Bid" when it really should mean a bid request. 
 * This is done because the API do not have a dedicated end point for requests. 
 *
 */
public class Request {
	
	private String initiatorId;
	private String inititatorName;
	private String id;
	private String type;
	private String dateCreated;
	private String dateClosedDown;
	private String subjectId;
	private ArrayList<Message> messages = new ArrayList<Message>();
	private int competency =0 ;
	
	private LessonInfo lessonInfo;
	
	

	private ArrayList<String> contractIds = new ArrayList<String>();
	
	/**
	 * This constructor creates a new bid request.
	 * It sets the dateCreated of the request to the date it is created and sets the expiry date based on the type of request. (30 minutes for open request, 7 dyas for closed)
	 * @param user User instance of the requestor
	 * @param type Type of the request, open or closed
	 * @param subjectId id of the subject that the requestor is interested in
	 * @param competency Minimum competency level constraint of the bidder
	 * @param hrsPerSession number of hours per session that the requestor needs
	 * @param sessionsPerWeek number of sessions per week that the requestor needs
	 * @param ratePerSession rate per session that the requestor offers the tutor
	 */
	public Request(User user, String type, String subjectId, int competency, String hrsPerSession, String sessionsPerWeek, String ratePerSession,String contractDuration) {

		this.initiatorId = user.getId();
		this.inititatorName = user.getGivenName() + " " + user.getFamilyName();
		this.type = type;
		this.subjectId = subjectId;
		this.competency = competency;
		this.lessonInfo = new LessonInfo(hrsPerSession,sessionsPerWeek,ratePerSession,contractDuration);
	}

	

	/**
	 * This constructor constructs a Request instance from a JSON string
	 * @param jsonString JSON String
	 * @throws Exception There is an error parsing the JSONS String
	 */
	public Request(String jsonString) throws Exception {
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.type = jsonNode.get("type").textValue();
		this.dateCreated = jsonNode.get("dateCreated").textValue();
		this.initiatorId = jsonNode.get("initiator").get("id").textValue();
		this.inititatorName = jsonNode.get("initiator").get("givenName").textValue() + " " + jsonNode.get("initiator").get("familyName").textValue();
		this.subjectId = jsonNode.get("subject").get("id").textValue();
		if (jsonNode.get("dateClosedDown") != null) {
			this.dateClosedDown = jsonNode.get("dateClosedDown").textValue();
		}
		
		if (jsonNode.get("additionalInfo").get("hoursPerSession") != null && jsonNode.get("additionalInfo").get("sessionsPerWeek") != null && jsonNode.get("additionalInfo").get("ratePerSession") != null) {
			String hoursPerSession = jsonNode.get("additionalInfo").get("hoursPerSession").textValue();
			String sessionsPerWeek = jsonNode.get("additionalInfo").get("sessionsPerWeek").textValue();
			String ratePerSession = jsonNode.get("additionalInfo").get("ratePerSession").textValue();
			String contractDuration = "6";
			
			if (jsonNode.get("lessonInfo").get("contractDuration") != null) {
				contractDuration = jsonNode.get("lessonInfo").get("contractDuration").textValue();
			}
			
			this.lessonInfo = new LessonInfo(hoursPerSession,sessionsPerWeek,ratePerSession, contractDuration);
		}

		if (jsonNode.get("additionalInfo").get("competency") != null) {
			this.competency = jsonNode.get("additionalInfo").get("competency").intValue();
		}
		if (jsonNode.get("additionalInfo").get("contractIds") != null) {
			JsonNode contracts = jsonNode.get("additionalInfo").get("contractIds");
			for (JsonNode c :contracts ) {
				this.contractIds.add(c.textValue());
			}
		}	
		
		JsonNode bidsNode =  jsonNode.get("messages");
		for (JsonNode b : bidsNode) {
			this.messages.add(new Message(b.toString()));
		}
	}
	
	/**
	 * Get the user id of the request initiator
	 * @return Requestor's user id
	 */
	public String getInitiatorId() {
		return initiatorId;
	}
	
	/**
	 * Get the name of the request initiator
	 * @return Requestor's name
	 */
	public String getInitiatorName() {
		return inititatorName;
	}
	
	/**
	 * Get the id of the request 
	 * @return Request's ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Get the type of the request
	 * @return "open" or "closed", depending on the request type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Get the date that the request was created 
	 * @return ISO 8601 date string of the request creation date
	 */
	public String getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * Get the date that the request was closed down
	 * @return ISO 8601 date string of the request closed date
	 */
	public String getDateClosedDown() {
		return dateClosedDown;
	}
	
	/**
	 * Get the subject id that the request is involved in
	 * @return Subject ID of the subject involved in the Request
	 */
	public String getSubjectId() {
		return subjectId;
	}
	
	/** 
	 * Get the messages instances for the contracts ( i.e. bids on the reqeust) based on the request
	 * @param contractId ID of the contract 
	 * @return List of messages of the contract
	 */
	public ArrayList<Message> getMessagesForContract(String contractId) {
		ArrayList<Message> out = new ArrayList<Message>();
		for  (Message m : messages) {
			if  (m.getContractId().contentEquals(contractId)) {
				out.add(m);
			}
		}
		return out;
	}

	public String toString() {
		String out  = "";
	    out = out + getType() + " request \n";
	    out = out + "Initiator: " + getInitiatorName() + "\n";
	    out = out + "Date Created: " + getDateCreated() + "\n";
	    out = out + "Date Closed: " + getDateClosedDown() + "\n";
	    out = out + "Minimum Competency: " + getCompetency() + "\n";
	    out = out + getLessonInfo().toString();
	    return out;
	}
	
	/**
	 * Get the minimum competency level of the request
	 * @return minimum competency level of the request
	 */
	public int getCompetency() {
		return this.competency;
	}
	
	public LessonInfo getLessonInfo() {
		return lessonInfo;
	}
}
