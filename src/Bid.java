import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Bid {
	private String initiatorId;
	private String inititatorName;
	private String id;
	private String type;
	private String dateCreated;
	private String dateClosedDown;
	private String subjectId;
	private ArrayList<Message> messages = new ArrayList<Message>();
	private int competency =0 ;
	private String hoursPerSession;
	private String sessionsPerWeek;
	private String ratePerSession;
	private ArrayList<String> contractIds = new ArrayList<String>();
	public Bid(User user, String type, String subjectId, int competency, String hrsPerSession, String sessionsPerWeek, String ratePerSession) {

		this.initiatorId = user.getId();
		this.inititatorName = user.getGivenName() + " " + user.getFamilyName();
		this.type = type;
		this.subjectId = subjectId;
		this.competency = competency;
		this.hoursPerSession = hrsPerSession;
		this.sessionsPerWeek = sessionsPerWeek;
		this.ratePerSession = ratePerSession;
		
	}

	public Bid(String jsonString) throws Exception {
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
		if (jsonNode.get("additionalInfo").get("hoursPerSession") != null) {
			this.hoursPerSession = jsonNode.get("additionalInfo").get("hoursPerSession").textValue();
		}
		if (jsonNode.get("additionalInfo").get("sessionsPerWeek") != null) {
			this.sessionsPerWeek = jsonNode.get("additionalInfo").get("sessionsPerWeek").textValue();
		}
		if (jsonNode.get("additionalInfo").get("ratePerSession") != null) {
			this.ratePerSession = jsonNode.get("additionalInfo").get("ratePerSession").textValue();
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
	
	public String getInitiatorId() {
		return initiatorId;
	}
	
	public String getInitiatorName() {
		return inititatorName;
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
	
	public ArrayList<Message> getMessagesForContract(String contractId) {
		ArrayList<Message> out = new ArrayList<Message>();
		for  (Message m : messages) {
			if  (m.getContractId().contentEquals(contractId)) {
				out.add(m);
			}
		}
		return out;
	}

	public String getHoursPerSession() {
		return hoursPerSession;
	}

	public String getSessionsPerWeek() {
		return sessionsPerWeek;
	}

	public String getRatePerSession() {
		return ratePerSession;
	}

	public String toString() {
		String out  = "";
	    out = out + type + " request \n";
	    out = out + "Initiator: " + this.inititatorName + "\n";
	    out = out + "Date Created: " + this.dateCreated + "\n";
	    out = out + "Date Closed: " + this.dateClosedDown + "\n";
	    out = out + "Minimum Competency: " + this.competency + "\n";
	    out = out + "Hours Per Session: " + this.hoursPerSession + "\n";
	    out = out + "Sessions Per Week: " + this.sessionsPerWeek + "\n";
	    out = out + "Rate Per Session: " + this.ratePerSession + "\n";
	    return out;
	}

	public int getCompetency() {
		return this.competency;
	}
	
}
