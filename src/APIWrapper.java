import java.io.IOException;
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

	    if (response.statusCode() != 200) {
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

	    if (response.statusCode() != 200 && response.statusCode() != 201) {
	    	System.out.print(response.statusCode());
	    	throw new Exception("Bad Request");
	    }

	    return response.body();
	}
	

}
