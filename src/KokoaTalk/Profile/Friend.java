package KokoaTalk.Profile;

public class Friend {
    private String id;
    private String name;
    private String statusMsg;
    private boolean isFavorite; // true: 즐겨찾기

    public Friend(String id, String name, String statusMsg, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.statusMsg = statusMsg;
        this.isFavorite = isFavorite;
    }

    // Getter
    public String getId() { return id; }
    public String getName() { return name; }
    public String getStatusMsg() { return statusMsg; }
    public boolean isFavorite() { return isFavorite; }
}

