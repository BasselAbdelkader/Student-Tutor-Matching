import java.sql.Timestamp;
import java.time.Instant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Contract {
	private String firstPartyId;
	private String bidderName;
	private String secondPartyId;
	private String id;
	private String subjectId;
	private String dateCreated;
	private String expiryDate;
	private String hoursPerSession;
	private String sessionsPerWeek;
	private String ratePerSession;
	private String initialRequestId;
	private String dateSigned;
	
	public Contract(User firstParty, Bid fromBid) {
		this.firstPartyId = firstParty.getId();
		this.bidderName = firstParty.getGivenName() + " " + firstParty.getFamilyName();
		this.secondPartyId = fromBid.getInitiatorId();
		this.subjectId = fromBid.getSubjectId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		this.dateCreated = instant.toString();
		this.hoursPerSession = fromBid.getHoursPerSession();
		this.sessionsPerWeek = fromBid.getSessionsPerWeek();
		this.ratePerSession = fromBid.getRatePerSession();
		this.initialRequestId = fromBid.getId();
	}
	
	public Contract(User firstParty, Bid fromBid, String hoursPerSession, String sessionsPerWeek, String ratePerSession) {
		this.firstPartyId = firstParty.getId();
		this.bidderName = firstParty.getGivenName() + " " + firstParty.getFamilyName();
		this.secondPartyId = fromBid.getInitiatorId();
		this.subjectId = fromBid.getSubjectId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		this.dateCreated = instant.toString();
		this.hoursPerSession = hoursPerSession;
		this.sessionsPerWeek = sessionsPerWeek;
		this.ratePerSession = ratePerSession;
		this.initialRequestId = fromBid.getId();
	}
	
	public Contract(String jsonString) throws Exception
	{
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.firstPartyId = jsonNode.get("firstParty").get("id").textValue();
		this.bidderName = jsonNode.get("firstParty").get("givenName").textValue() + " " + jsonNode.get("firstParty").get("familyName").textValue();
		this.secondPartyId = jsonNode.get("secondParty").get("id").textValue();
		this.subjectId = jsonNode.get("subject").get("id").textValue();
		this.dateCreated = jsonNode.get("dateCreated").textValue();
		this.expiryDate = jsonNode.get("expiryDate").textValue();
		
		if (jsonNode.get("dateSigned") != null) {
			this.dateSigned = jsonNode.get("dateSigned").textValue();
		}
		
		if (jsonNode.get("lessonInfo").get("hoursPerSession") != null) {
			this.hoursPerSession = jsonNode.get("lessonInfo").get("hoursPerSession").textValue();
		}
		
		if (jsonNode.get("lessonInfo").get("sessionsPerWeek") != null) {
			this.sessionsPerWeek = jsonNode.get("lessonInfo").get("sessionsPerWeek").textValue();
		}
		
		if (jsonNode.get("lessonInfo").get("ratePerSession") != null) {
			this.ratePerSession = jsonNode.get("lessonInfo").get("ratePerSession").textValue();
		}
		
		if (jsonNode.get("additionalInfo").get("initialBidId") != null) {
			this.initialRequestId = jsonNode.get("additionalInfo").get("initialRequestId").textValue();
		}
		
	}

	public String getFirstPartyId() {
		return firstPartyId;
	}

	public String getSecondPartyId() {
		return secondPartyId;
	}

	public String getId() {
		return id;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public String getExpiryDate() {
		return expiryDate;
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

	public String initialRequestId() {
		return initialRequestId;
	}

	public String getDateSigned() {
		return dateSigned;
	}
	
	public String toString() {
		String out  = "";
	    out = out + "Bidder: " + this.bidderName + "\n";
	    out = out + "Date Created: " + this.dateCreated + "\n";
	    out = out + "Hours Per Session: " + this.hoursPerSession + "\n";
	    out = out + "Sessions Per Week: " + this.sessionsPerWeek + "\n";
	    out = out + "Rate Per Session: " + this.ratePerSession + "\n";
	    return out;
	}

	

}
