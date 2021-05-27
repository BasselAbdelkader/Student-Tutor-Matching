package model;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Contract class
 * @author Andrew Pang
 * This class is used to represent a Contract that is created from a bid request.
 * For the purposes of this project, an unsigned contract is a bid for a bid request.
 * A signed contract is a successful bid that is closed by the requestor. 
 */
public class Contract implements Subscription {
	
	private String firstPartyId;
	private String tutorName;
	public String getTutorName() {
		return tutorName;
	}

	private String secondPartyId;
	private String studentName;
	public String getStudentName() {
		return studentName;
	}

	private String id;
	private String subjectId;
	private String subjectName;
	private String dateCreated;
	private String expiryDate;
	private String hoursPerSession;
	private String sessionsPerWeek;
	private String ratePerSession;
	private String contractDuration;
	

	private String initialRequestId;
	private String dateSigned;
	
	private boolean subscribed = false;
	
	/**
	 * This constructor creates a new unsigned contract (i.e. a bid) instance and sets the date created to the time the instance was created 
	 * It also sets the expiry date based on the type of bid request the initial request was (30 minutes for open request, 7 dyas for closed)
	 * @param firstParty The bidder's user instance
	 * @param fromBid The bid request the bidder is interested in
	 */
	public Contract(User firstParty, Request fromBid) {
		this.firstPartyId = firstParty.getId();
		this.tutorName = firstParty.getGivenName() + " " + firstParty.getFamilyName();
		this.secondPartyId = fromBid.getInitiatorId();
		this.studentName = fromBid.getInitiatorName();
		this.subjectId = fromBid.getSubjectId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.dateCreated = timestamp.toInstant().toString();
		Timestamp expTimestamp = null;
		if (fromBid.getType().contentEquals("closed")) {
			expTimestamp = new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));
		}else if (fromBid.getType().contentEquals("open")) {
			expTimestamp = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));
		}
		this.expiryDate = expTimestamp.toInstant().toString();;
		this.hoursPerSession = fromBid.getHoursPerSession();
		this.sessionsPerWeek = fromBid.getSessionsPerWeek();
		this.ratePerSession = fromBid.getRatePerSession();
		this.contractDuration = fromBid.getContractDuration();
		this.initialRequestId = fromBid.getId();
	}
	
	public Contract(User firstParty, Contract fromContract) {
		this.firstPartyId = firstParty.getId();
		this.tutorName = firstParty.getGivenName() + " " + firstParty.getFamilyName();
		this.secondPartyId = fromContract.getSecondPartyId();
		this.studentName = fromContract.getStudentName();
		this.subjectId = fromContract.getSubjectId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.dateCreated = timestamp.toInstant().toString();
		this.initialRequestId = fromContract.getInitialRequestId();
		this.hoursPerSession = fromContract.getHoursPerSession();
		this.sessionsPerWeek = fromContract.getSessionsPerWeek();
		this.ratePerSession = fromContract.getRatePerSession();
		this.contractDuration = fromContract.getContractDuration();
		this.sign();	
	}
	
	public Contract(Contract fromContract) {
		this.firstPartyId = fromContract.getFirstPartyId();
		this.tutorName = fromContract.getTutorName();
		this.secondPartyId = fromContract.getSecondPartyId();
		this.studentName = fromContract.getStudentName();
		this.subjectId = fromContract.getSubjectId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.dateCreated = timestamp.toInstant().toString();
		this.initialRequestId = fromContract.getInitialRequestId();
		this.hoursPerSession = fromContract.getHoursPerSession();
		this.sessionsPerWeek = fromContract.getSessionsPerWeek();
		this.ratePerSession = fromContract.getRatePerSession();
		this.contractDuration = fromContract.getContractDuration();
	}
	
	/**
	 * This constructor constructs a Contract instance based on a JSON string that it is given
	 * @param jsonString The JSON String 
	 * @throws Exception There is an erorr in parsing the JSON string
	 */
	public Contract(String jsonString) throws Exception
	{
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.firstPartyId = jsonNode.get("firstParty").get("id").textValue();
		this.tutorName = jsonNode.get("firstParty").get("givenName").textValue() + " " + jsonNode.get("firstParty").get("familyName").textValue();
		this.secondPartyId = jsonNode.get("secondParty").get("id").textValue();
		this.studentName = jsonNode.get("secondParty").get("givenName").textValue() + " " + jsonNode.get("secondParty").get("familyName").textValue();
		this.subjectId = jsonNode.get("subject").get("id").textValue();
		this.subjectName = jsonNode.get("subject").get("name").textValue();
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
		
		if (jsonNode.get("lessonInfo").get("contractDuration") != null) {
			this.contractDuration = jsonNode.get("lessonInfo").get("contractDuration").textValue();
		}else {
			this.contractDuration = "6";
		}
		
		if (jsonNode.get("additionalInfo").get("initialRequestId") != null) {
			this.initialRequestId = jsonNode.get("additionalInfo").get("initialRequestId").textValue();
		}
		
		if (jsonNode.get("additionalInfo").get("subscribed") != null) {
			this.subscribed = jsonNode.get("additionalInfo").get("subscribed").booleanValue();
		}
		
	}
	
	/**
	 * Get the user id for the first party (Bidder or Tutor)
	 * @return id of the first party user
	 */
	public String getFirstPartyId() {
		return firstPartyId;
	}

	/**
	 * Get the user id for the second party (Requestor or Student)
	 * @return id of the second party user
	 */
	public String getSecondPartyId() {
		return secondPartyId;
	}
	
	/**
	 * Get the  id of the contract
	 * @return id of the current contract
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the id of the subject that the contract is involved in
	 * @return subject id of the current contract
	 */
	public String getSubjectId() {
		return subjectId;
	}
	
	/**
	 * Get the date the contract was created 
	 * @return ISO8601 date that that the contract is created
	 */
	public String getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * Get the expiry date of the contract
	 * Usually 30 minutes pass the created date for open bid request
	 * 7 days pass the created date for closed  bid request 
	 * @return ISO8601 date that that the contract expires
	 */
	public String getExpiryDate() {
		return expiryDate;
	}
	
	/**
	 * Get the agreed upon hours per session in the contract
	 * @return number of hours per session agreed in the contract
	 */
	public String getHoursPerSession() {
		return hoursPerSession;
	}
	
	/**
	 * Get the agreed upon sessions per week in the contract
	 * @return number of sessions per week agreed in the contract
	 */
	public String getSessionsPerWeek() {
		return sessionsPerWeek;
	}
	
	/**
	 * Get the agreed upon rate per session in the contract
	 * @return rate per session agreed in the contract
	 */
	public String getRatePerSession() {
		return ratePerSession;
	}
	
	/**
	 * Get the id of the initial bid request that led to this contract
	 * @return id of initial request
	 */
	public String getInitialRequestId() {
		return initialRequestId;
	}

	/**
	 * Get the date the contract was signed
	 * @return ISO 8601 format of the sugned date
	 */
	public String getDateSigned() {
		return dateSigned;
	}
	
	public String getContractDuration() {
		return contractDuration;
	}

	
	
	public void sign() {
		if(this.dateSigned == null) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.dateSigned = timestamp.toInstant().toString();
			Timestamp exptimestamp = new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(Integer.parseInt(this.contractDuration) * 30));
			this.expiryDate = exptimestamp.toInstant().toString(); 
		}
	}
	
	/**
	 * Amend the number of hours per session in the UNSIGNED contract
	 * @return null
	 */
	public void setHoursPerSession(String hoursPerSession) {
		this.hoursPerSession = hoursPerSession;
	}
	
	/**
	 * Amend the number of sessions per session in the UNSIGNED contract
	 * @return null
	 */
	public void setSessionsPerWeek(String sessionsPerWeek) {
		this.sessionsPerWeek = sessionsPerWeek;
	}
	
	/**
	 * Amend the number rate per session in the UNSIGNED contract
	 * @return null
	 */
	public void setRatePerSession(String ratePerSession) {
		this.ratePerSession = ratePerSession;
	}
	
	public void setContractDuration(String contractDuration) {
		this.contractDuration = contractDuration;
	}
	
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
	
	public String toString() {
		String out  = "";
		out = out + this.subjectName + " - ";
		out = out + this.studentName + "(S) and ";
	    out = out + this.tutorName + "(T)";
	    return out;
	}

	public String getContractDetails() {
		String out  = "";
		out = out + "Subject: " + this.subjectName + "\n";
		out = out + "Student: " + this.studentName + "\n";
	    out = out + "Tutor: " + this.tutorName + "\n";
	    out = out + "Date Created: " + this.dateCreated + "\n";
	    if(dateSigned != null) {
	    	out = out + "Date Signed: " + this.dateSigned + "\n";
	    	out = out + "Contract Expiry: " + this.expiryDate + "\n";
	    }
	    out = out + "Contract duration: " + this.contractDuration + "\n";
	    out = out + "Hours Per Session: " + this.hoursPerSession + "\n";
	    out = out + "Sessions Per Week: " + this.sessionsPerWeek + "\n";
	    out = out + "Rate Per Session: " + this.ratePerSession + "\n";
	    return out;
	}
	
	@Override
	public boolean isSubscribed() {
		return this.subscribed;
	}

	@Override
	public void subscribe() {
		this.subscribed = true;
	}

	@Override
	public void unsubscribe() {
		this.subscribed = false;
	}

	@Override
	public String getNotification() {
		long now = System.currentTimeMillis();
		long expiry = Instant.parse(getExpiryDate()).toEpochMilli();
		long diff = expiry - now ;
		if(getDateSigned() != null && diff > 0 && diff < TimeUnit.DAYS.toMillis(30) ) {
			return "Contract : " + this.toString() + " will expire in less than a month!";
		}else {
			return null;
		}
	}
	

	

}
