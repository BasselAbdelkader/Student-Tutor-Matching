import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Subject {
	
	private String id;
	private String name;
	private String description;
	private ArrayList<String> bidIds = new ArrayList<String>();
	

	private String competencies;
	
	public Subject(String jsonString) throws Exception{
		
		ObjectNode jsonNode = new ObjectMapper().readValue(jsonString, ObjectNode.class);
		this.id = jsonNode.get("id").textValue();
		this.name = jsonNode.get("name").textValue();
		this.description = jsonNode.get("description").textValue();
		
		
		this.competencies = jsonNode.get("competencies").textValue();
		
		JsonNode bidsNode =  jsonNode.get("bids");
		if (bidsNode != null) {
			for (JsonNode b : bidsNode) {
				this.bidIds.add(b.get("id").textValue());
			}
		}
		
	}
	
	public String toString() {
		String out = "";
		out = out + this.name + "\n";
		out = out + this.description + "\n";
		return out;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getBidIds() {
		return bidIds;
	}


	public String getCompetencies() {
		return competencies;
	}

	public void setCompetencies(String competencies) {
		this.competencies = competencies;
	}

}
