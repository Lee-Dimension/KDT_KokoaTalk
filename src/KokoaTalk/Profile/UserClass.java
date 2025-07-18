package KokoaTalk.Profile;

import java.io.*;
import java.util.ArrayList;

public class UserClass implements Serializable {
    private String id;
    private String name;
    private String statusMsg;
    private ArrayList<String> friends = new ArrayList<>();    // 친구 id 리스트
    private ArrayList<String> favoriteFriends = new ArrayList<>();     // ★ 즐겨찾기 친구 id 리스트

    // 생성자
    public UserClass(String id, String name, String statusMsg) {
        this.id = id;
        this.name = name;
        this.statusMsg = statusMsg;
    }
    public UserClass(String id, String name) {
        this(id, name, "");
    }

    // Getter
    public String getId() { return id; }
    public String getName() { return name; }
    public String getStatusMsg() { return statusMsg; }
    public ArrayList<String> getFriends() { return friends; }
    public ArrayList<String> getFavoriteFriends() { return favoriteFriends; }

    // Setter
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStatusMsg(String statusMsg) { this.statusMsg = statusMsg; }
    public void setFriends(ArrayList<String> friends) { this.friends = friends; }
    public void setFavoriteFriends(ArrayList<String> favoriteFriends) { this.favoriteFriends = favoriteFriends; }

}
