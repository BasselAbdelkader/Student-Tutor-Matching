
public class Application {
	
	private static final String myApiKey = "nwPqJThKp7jwCtf8McjrgTfWkdFmnJ";

	// Provide the root URL for the web service. All web service request URLs start with this root URL.
	private static final String rootUrl = "https://fit3077.com/api/v1";
	
	public static final UserAPI users= new UserAPI(myApiKey,rootUrl);
	public static final BidsAPI bids= new BidsAPI(myApiKey,rootUrl);
	public static final SubjectAPI subjects= new SubjectAPI(myApiKey,rootUrl);
	public static final MessagesAPI messages= new MessagesAPI(myApiKey,rootUrl);
	public static final ContractsAPI contracts= new ContractsAPI(myApiKey,rootUrl);
	public static void main(String[] a) {
        LoginWindow Auth = new LoginWindow();
        

    }


}
