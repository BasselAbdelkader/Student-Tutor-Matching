package apiservices;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.Bid;
import model.Contract;
import model.User;

public class ContractsAPI extends APIWrapper {
	
	private static ContractsAPI instance;
	
	public static ContractsAPI getInstance() {
		if(instance == null) { 
			instance = new ContractsAPI();
		}
		return instance;
	}

	private ContractsAPI() {
		super(rootUrl + "/contract");
	}
	
	
	public ArrayList<Contract> getContractsForUser(User u) throws Exception{
		String userID = u.getId();
		String response = super.getHttpRequest(url);
		ObjectNode[] jsonNodes = new ObjectMapper().readValue(response, ObjectNode[].class);
		ArrayList<Contract> contracts = new ArrayList<Contract>();
	    for (ObjectNode node: jsonNodes) {
	    	
	    	if( node.get("firstParty").get("id").textValue().contentEquals(userID) || node.get("secondParty").get("id").textValue().contentEquals(userID) ) {
	    		contracts.add(new Contract(node.toString()));
	    	}
	     
	    }
	    return contracts;
		
	}
	
	public Contract getContract(String id) throws Exception{
		String response =  super.getHttpRequest(url + "/" + id );
		return new Contract(response);
	}
	
	public Contract addContract(Contract contract) throws Exception{
		
		String jsonString = "{" +
			      "\"firstPartyId\":\"" + contract.getFirstPartyId() + "\"" + "," +
			      "\"secondPartyId\":\"" + contract.getSecondPartyId() + "\"" + "," +
			      "\"subjectId\":\"" + contract.getSubjectId() + "\"" + "," +
			      "\"dateCreated\":\"" + contract.getDateCreated() + "\"" + "," +
			      "\"expiryDate\":\"" + contract.getExpiryDate() + "\"" + "," +
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
	    	
	    	if( node.get("additionalInfo").get("initialRequestId") != null && node.get("dateSigned").isNull()) {
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
		//TODO : update expiry
		String contractId = c.getId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		
		String jsonString = "{" +
				"\"dateSigned\":\"" + instant.toString() + "\"" +
			  "}";
		if( getSignedContract(b) == null) {
			super.postHttpRequest(jsonString, url + "/" + contractId + "/sign");
			deleteUnsignedContracts(b);
			BidsAPI.getInstance().closeBid(b);
			return true;
		}
		return false;
	}
	
	public Contract updateContract(Contract contract) throws Exception {
		
		String jsonString = "{" +
			      "\"lessonInfo\":{" + 
			      		"\"hoursPerSession\":\""+ contract.getHoursPerSession() + "\"" + "," +
			      		"\"sessionsPerWeek\":\""+ contract.getSessionsPerWeek()+ "\"" + "," +
			      		"\"ratePerSession\":\""+ contract.getRatePerSession()+ "\"" + "," +
			      		"\"sessions\":[]" +
			      	"}" + 
			    "}";
		String response = super.updateHttpRequest(jsonString,url + "/" + contract.getId());
		
		return new Contract(response);
	}

	public void deleteUnsignedContracts(Bid bid) throws Exception {
		ArrayList<Contract> unsigned = this.getUnsignedContracts(bid);
		
		for(Contract c : unsigned) {
			super.deleteHttpRequest( url + "/" + c.getId());
		}
	}


}
