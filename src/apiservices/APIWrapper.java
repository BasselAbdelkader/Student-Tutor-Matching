package apiservices;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * API Wrapper Class
 * Authored by: Andrew Pang
 * This is the parent class for all API Services.
 * It contains code required for HTTP GET, POST, PUT, PATCH, DELETE requests
 */
public abstract class APIWrapper {
	
	private final static String api_key = "fktLgQzBGKQMrR9prwMTRqBzjKk6F8";
	protected final static String rootUrl = "https://fit3077.com/api/v2";
	protected String url;
	
	public APIWrapper(String url) {
		this.url = url;
	}
	
	/**
	 * GET HTTP Request.
	 * This function performs a get request to the url specified and returns a JSON string response
	 * @param url to perform GET request
	 * @return JSON string response
	 * @throws Exception when the status code does not begin in 2xx
	 */
	protected String getHttpRequest(String url) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request;
		HttpResponse<String> response;
		
	    request = HttpRequest.newBuilder(URI.create(url)) // Return a JWT so we can use it in Part 5 later.
	      .setHeader("Authorization", api_key)
	      .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
	      .GET()
	      .build();

	    response = client.send(request, HttpResponse.BodyHandlers.ofString());

	    if (response.statusCode() < 200 || response.statusCode() > 299) {
	    	System.out.println("BAD GET: " + url);
	    	System.out.println(response.statusCode());
	    	System.out.println(response.body());
	    	return null;
	    }

	    return response.body();
	}
	
	/**
	 * POST HTTP Request.
	 * This function performs a POST request to the url specified and returns a JSON string response
	 * @param url to perform POST request
	 * @param jsonString payload data in the form of JSON string
	 * @return JSON string response
	 * @throws Exception when the status code does not begin in 2xx
	 */
	protected String postHttpRequest(String jsonString,String url) throws Exception{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request;
		HttpResponse<String> response;
		
	    request = HttpRequest.newBuilder(URI.create(url)) 
	      .setHeader("Authorization", api_key)
	      .header("Content-Type","application/json") 
	      .POST(HttpRequest.BodyPublishers.ofString(jsonString))
	      .build();

	    response = client.send(request, HttpResponse.BodyHandlers.ofString());

	    if (response.statusCode() < 200 || response.statusCode() > 299) {
	    	System.out.println("BAD POST: " + jsonString);
	    	System.out.println(response.statusCode());
	    	System.out.println(response.body());
	    	throw new Exception("Bad Request");
	    }

	    return response.body();
	}
	
	/**
	 * DELETE HTTP Request
	 * This function performs a DELETE request to the url specified and returns a JSON string response
	 * @param url to perform DELETE request
	 * @return jsonString returned data in the form of JSON string
	 * @throws Exception when the status code does not begin in 2xx
	 */
	protected String deleteHttpRequest(String url) throws Exception{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request;
		HttpResponse<String> response;
		
	    request = HttpRequest.newBuilder(URI.create(url)) // Return a JWT so we can use it in Part 5 later.
	      .setHeader("Authorization", api_key)
	      .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
	      .DELETE()
	      .build();

	    response = client.send(request, HttpResponse.BodyHandlers.ofString());

	    if (response.statusCode() < 200 || response.statusCode() > 299) {
	    	System.out.println("BAD DELETE: " + url);
	    	System.out.println(response.statusCode());
	    	System.out.println(response.body());
	    	throw new Exception("Bad delete");
	    }

	    return response.body();
	}
	
	/**
	 * UPDATE HTTP Request.
	 * This function performs a UPDATE request to the url specified and returns a JSON string response
	 * @param url to perform UPDATE request
	 * @param jsonString payload data in the form of JSON string
	 * @return JSON string response
	 * @throws Exception when the status code does not begin in 2xx
	 */
	protected String updateHttpRequest(String jsonString, String url) throws Exception{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request;
		HttpResponse<String> response;
		
	    request = HttpRequest.newBuilder(URI.create(url)) // Return a JWT so we can use it in Part 5 later.
	      .setHeader("Authorization", api_key)
	      .header("Content-Type","application/json")
	      .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))// This header needs to be set when sending a JSON request body.)
	      .build();

	    response = client.send(request, HttpResponse.BodyHandlers.ofString());

	    if (response.statusCode() < 200 || response.statusCode() > 299) {
	    	System.out.println("BAD UPDATE: " + jsonString);
	    	System.out.println(response.statusCode());
	    	System.out.println(response.body());
	    	throw new Exception("Bad update");
	    }

	    return response.body();
	}
	
	

}
