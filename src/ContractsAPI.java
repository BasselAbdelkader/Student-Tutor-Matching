import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ContractsAPI extends APIWrapper {

	public ContractsAPI(String api_key, String url) {
		super(api_key, url+ "/contract");
	}
	
	public Contract getContract(String id) throws Exception{
		String response =  super.getHttpRequest(url + "/" + id );
		return new Contract(response);
	}
	
	public Contract addContract(Contract contract) throws Exception{
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Timestamp expTimestamp = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));
		Instant instant = timestamp.toInstant(); 
		Instant expInstant = expTimestamp.toInstant();
		String jsonString = "{" +
			      "\"firstPartyId\":\"" + contract.getFirstPartyId() + "\"" + "," +
			      "\"secondPartyId\":\"" + contract.getSecondPartyId() + "\"" + "," +
			      "\"subjectId\":\"" + contract.getSubjectId() + "\"" + "," +
			      "\"dateCreated\":\"" + instant.toString() + "\"" + "," +
			      "\"expiryDate\":\"" + expInstant.toString() + "\"" + "," +
			      "\"lessonInfo\":{" + 
			      		"\"hoursPerSession\":\""+ contract.getHoursPerSession() + "\"" + "," +
			      		"\"sessionsPerWeek\":\""+ contract.getSessionsPerWeek()+ "\"" + "," +
			      		"\"ratePerSession\":\""+ contract.getRatePerSession()+ "\"" + "," +
			      		"\"sessions\":[]" +
			      	"}" + "," +
			      "\"additionalInfo\":{" + 
			      		"\"initialRequestId\":\""+ contract.initialRequestId() + "\"" + 
		      	   "}" + 
			    "}";
		String response = super.postHttpRequest(jsonString, url);
		
		return new Contract(response);
	}
	
	public ArrayList<Contract> getUnsignedContracts(Bid request) throws Exception{
		String requestId = request.getId();
		String response = super.getHttpRequest(url);
		ObjectNode[] jsonNodes = new ObjectMapper().readValue(response, ObjectNode[].class);
		ArrayList<Contract> contracts = new ArrayList<Contract>();
	    for (ObjectNode node: jsonNodes) {
	    	
	    	if( node.get("additionalInfo").get("initialRequestId") != null) {
	    		if( node.get("additionalInfo").get("initialRequestId").textValue().contentEquals(requestId)) {
	   	    	  contracts.add(new Contract(node.toString()));
	   	      	}
	    	}
	     
	    }
	    return contracts;
	}
	
	public Contract getSignedContract(Bid request) throws Exception {
		String requestId = request.getId();
		String response = super.getHttpRequest(url);
		ObjectNode[] jsonNodes = new ObjectMapper().readValue(response, ObjectNode[].class);
	    for (ObjectNode node: jsonNodes) {
	    	
	      if( node.get("additionalInfo").get("initialRequestId") != null  && !node.get("dateSigned").isNull()) {
	    	  if (node.get("additionalInfo").get("initialRequestId").textValue().contentEquals(requestId)) {
	    		  System.out.println(node.get("dateSigned").isNull());
	    		  return new Contract(node.toString());
	    	  }
	      }
	    }
	    return null;
	}
	
	public boolean signContract(Bid b, Contract c) throws Exception {
		String contractId = c.getId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		
		String jsonString = "{" +
				"\"dateSigned\":\"" + instant.toString() + "\"" +
			  "}";
		if( getSignedContract(b) == null) {
			super.postHttpRequest(jsonString, url + "/" + contractId + "/sign");
			return true;
		}
		return false;
	}
	
	public boolean addSessionToContract(Contract c, String s) throws Exception {
		
		return false;
	}


}
