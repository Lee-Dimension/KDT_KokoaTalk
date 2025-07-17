package KokoaTalk.ChatList;

public class ChatRoom {
    private String title;
    private String lastMessage;
    private String time;
    private String profileImagePath;

    public ChatRoom(String title, String lastMessage, String time, String profileImagePath) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.time = time;
        this.profileImagePath = profileImagePath;
    }

    public String getTitle() { return title; }
    public String getLastMessage() { return lastMessage; }
    public String getTime() { return time; }
    public String getProfileImagePath() { return profileImagePath; }
}
