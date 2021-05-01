package apiservices;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.Request;
import model.Contract;
import model.User;
/**
 * Contracts API Class
 * Contract play a vital role in this system. 
 * It is worth noting for the purposes of this system, an unsigned contract is a bid while a signed contract is a valid contract. 
 * This is the API class responsible for connecting to the API and performing operation for contracts.
 * This class contain the methods to retrieve, modify, delete and sign contracts in a controlled manner. 
 * @author Andrew Pang
 *
 */
public class ContractsAPI extends APIWrapper {
	
	private static ContractsAPI instance;
	
	/**
	 * Only ONE ContractsAPI class can exist as we only need one connection to the API Service
	 */
	public static ContractsAPI getInstance() {
		if(instance == null) { 
			instance = new ContractsAPI();
		}
		return instance;
	}

	private ContractsAPI() {
		super(rootUrl + "/contract");
	}
	
	
	/**
	 * Get contracts belonging to user function
	 * This method gets the contracts that the user is involved in as either the first (tutor) or second (student) party. 
	 * @param user User instance to get the contracts for
	 * @return A list of contract involving the user
	 * @throws Exception There is an error with the HTTP request.
	 */
	public ArrayList<Contract> getContractsForUser(User user) throws Exception{
		String userID = user.getId();
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
	
	/**
	 * Get contract based on the contractId
	 * This method gets the contract from the API based on the contract ID provided
	 * @param id ID of the contract
	 * @return the Contract instance from the API
	 * @throws Exception There is an error with the HTTP request.
	 */
	public Contract getContract(String id) throws Exception{
		String response =  super.getHttpRequest(url + "/" + id );
		return new Contract(response);
	}
	
	/**
	 * Add a contract to the API
	 * This methods converts a Contract instance to JSON String and posts it to the API
	 * @param contract Contract instance
	 * @return the new Contract constructed from the response of the API
	 * @throws Exception There is an error with the HTTP request.
	 */
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
			      		"\"initialRequestId\":\""+ contract.getInitialRequestId() + "\"" + 
		      	   "}" + 
			    "}";
		String response = super.postHttpRequest(jsonString, url);
		
		return new Contract(response);
	}
	
	/**
	 * Get all the unsigned contracts (i.e. Bids) from the API
	 * This methods gets all the bids for a bid request from the API. 
	 * Remember, a current bid is essentially an unsigned contract. 
	 * @param request The bid request to get the current bids on it
	 * @return a list of Unsigned Contracts (or Bids) bided on the bid request
	 * @throws Exception There is an error with the HTTP request.
	 */
	public ArrayList<Contract> getUnsignedContracts(Request request) throws Exception{
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
	
	/**
	 * Get the signed contract (i.e. Successful Bid) of a bid request from the API
	 * This methods gets the signed contract (i.e. successful bid that turned into a contract) for a bid request. 
	 * Remember, a current bid is essentially an unsigned contract. 
	 * @param request The bid request to get the signed contract (successful bid ) for
	 * @return The signed contract (or successful bid) for that bid
	 * @throws Exception There is an error with the HTTP request.
	 */
	public Contract getSignedContract(Request request) throws Exception {
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
	
	/**
	 * Sign a contract and close the bid
	 * This method closes a bid request by signing a contract based on the bid and closes the bid request
	 * After a contract has been signed for a bid, the method also deletes all the other bids (i.e. unsigned contract) on the bid request
	 * Remember, a current bid is essentially an unsigned contract. If a bid clsoes we have to delete all teh open bids to save space in the API Server
	 * @param bid The bid request to be closed
	 * @param contract The unsigned contract (i.e. a current bid) to be signed
	 * @return boolean, true if there is no error, false if an error occurred
	 * @throws Exception There is an error with the HTTP request.
	 */
	public boolean signContract(Request bid, Contract contract) throws Exception {
		//TODO : update expiry
		String contractId = contract.getId();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant instant = timestamp.toInstant(); 
		
		String jsonString = "{" +
				"\"dateSigned\":\"" + instant.toString() + "\"" +
			  "}";
		if( getSignedContract(bid) == null) {
			super.postHttpRequest(jsonString, url + "/" + contractId + "/sign");
			deleteUnsignedContracts(bid);
			BidsAPI.getInstance().closeBid(bid);
			return true;
		}
		return false;
	}
	
	/**
	 * Update an unsigned contract
	 * This method enables the user to amend details of an unsigned contract (a bid) in closed bidding.
	 * Remember, a current bid is essentially an unsigned contract. 
	 * @param contract The unsigned contract instance (i.e. a bid) to be amended
	 * @return The contract instance of the amended unsigned contract
	 * @throws Exception There is an error with the HTTP request.
	 */
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
	
	/**
	 * Deletes all unsigned contract (i.e. current bids) of a bid request
	 * This method deletes all the unsigned  contracts (i.e. unclosed bids) for a bid request
	 * Remember, a current bid is essentially an unsigned contract. If a bid closes we have to delete all the open bids to save space in the API Server
	 * @param bid The bid request in which we want to close all bids for it. 
	 * @return null
	 * @throws Exception There is an error with the HTTP request.
	 */
	public void deleteUnsignedContracts(Request bid) throws Exception {
		ArrayList<Contract> unsigned = this.getUnsignedContracts(bid);
		
		for(Contract c : unsigned) {
			super.deleteHttpRequest( url + "/" + c.getId());
		}
	}


}
