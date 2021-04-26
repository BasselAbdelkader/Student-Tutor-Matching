
public class ContractsAPI extends APIWrapper {

	public ContractsAPI(String api_key, String url) {
		super(api_key, url+ "/contract");
	}
	
	public String getContract(String id) throws Exception{
		String response =  super.getHttpRequest(url + "/" + id + "?fields=messages");

		return null;
	}
	


}
