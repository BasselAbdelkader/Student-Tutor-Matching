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
	
	public String addContract(Contract contract) throws Exception{
		
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
			      		"\"ratePerSession\":\""+ contract.getRatePerSession()+ "\"" +
			      	"}" + "," +
			      "\"additionalInfo\":{" + 
			      		"\"initialRequestId\":\""+ contract.initialRequestId() + "\"" + 
		      	   "}" + 
			    "}";
		String response = super.postHttpRequest(jsonString, url);
		ObjectNode jsonNode = new ObjectMapper().readValue(response, ObjectNode.class);
		
		return jsonNode.get("id").textValue();
	}
	
	public ArrayList<Contract> getUnsignedContracts(String requestId) throws Exception{
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
	
	public Contract getSignedContract(String requestId) throws Exception {
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
	
	public boolean signContract(String contractId) throws Exception {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		
		String jsonString = "{" +
				"\"dateSigned\":\"" + instant.toString() + "\"" +
			  "}";

		if( getSignedContract(contractId) == null) {
			String response = super.postHttpRequest(jsonString, url + "/" + contractId + "/sign");
			return true;
		}
		return false;
	}


}
