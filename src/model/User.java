package model;
// 사용자 정보를 담는 객체
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String id;
    private String name;
    private String statusMsg;
    private List<String> friends = new ArrayList<>();
    private List<String> favoriteFriends = new ArrayList<>();
    
    // 생성자
    public User(String id, String name, String statusMsg) {
        this.id = id;
        this.name = name;
        this.statusMsg = statusMsg;
    }
    public User(String id, String name) {
        this(id, name, "");
    }
    // Getter
    public String getId() { return id; }
    public String getName() { return name; }
    public String getStatusMsg() { return statusMsg; }
    public List<String> getFriends() { return friends; }
    public List<String> getFavoriteFriends() { return favoriteFriends; }
    
    // Setter
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStatusMsg(String statusMsg) { this.statusMsg = statusMsg; }
    public void setFriends(List<String> friends) { this.friends = friends; }
    public void setFavoriteFriends(List<String> favoriteFriends) { this.favoriteFriends = favoriteFriends; }
}
