package KokoaTalk.Profile;

public class UserClass {
	private String id;
	private String name;
	private String statusMsg;
	
	public UserClass(String id, String name, String statusMsg) {
		this.id = id;
		this.name = name;
		this.statusMsg = statusMsg;
	}

	// Getter
	public String getId() {return id;}
	public String getName() {return name;}
	public String getStatusMsg() { return statusMsg; }
	
}
