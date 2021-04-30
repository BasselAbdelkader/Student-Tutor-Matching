import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public abstract class APIWrapper {
	
	private String api_key;
	protected String url;
	
	

	public APIWrapper(String api_key, String url) {
		this.api_key = api_key;
		this.url = url;
	}
	
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
	    	return null;
	    }

	    return response.body();
	}
	
	protected String postHttpRequest(String jsonString,String url) throws Exception{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request;
		HttpResponse<String> response;
		
	    request = HttpRequest.newBuilder(URI.create(url)) // Return a JWT so we can use it in Part 5 later.
	      .setHeader("Authorization", api_key)
	      .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
	      .POST(HttpRequest.BodyPublishers.ofString(jsonString))
	      .build();

	    response = client.send(request, HttpResponse.BodyHandlers.ofString());

	    if (response.statusCode() < 200 || response.statusCode() > 299) {
	    	System.out.print("BAD REQUEST: " + jsonString);
	    	System.out.print(response.statusCode());
	    	throw new Exception("Bad Request");
	    }

	    return response.body();
	}
	
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
	    	System.out.print(response.statusCode());
	    	throw new Exception("Bad Request");
	    }

	    return response.body();
	}
	
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
	    	System.out.print("BAD UPDATE: " + jsonString);
	    	System.out.print(response.statusCode());
	    	throw new Exception("Bad Request");
	    }

	    return response.body();
	}
	
	

}
