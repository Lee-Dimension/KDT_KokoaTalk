package KokoaTalk.Profile;

import java.io.*;

public class UserClass implements Serializable {
	private String id;
	private String name;
	private String statusMsg;
	
	public UserClass(String id, String name, String statusMsg) {
		this.id = id;
		this.name = name;
		this.statusMsg = statusMsg;
	}
    public UserClass(String id, String name) {
        this(id, name, ""); // 기본값을 ""(빈 문자열)로
    }
	// Getter
	public String getId() {return id;}
	public String getName() {return name;}
	public String getStatusMsg() { return statusMsg; }
	
	//Setter
	public void setId(String id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setStatusMsg(String statusMsg) {this.statusMsg = statusMsg;}
}
