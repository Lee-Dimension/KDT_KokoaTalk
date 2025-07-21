package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public class Message implements Serializable {
    private String senderId;
    private String senderName;
    private String roomId;
    private String content;
    private LocalDateTime timestamp;

    // 생성자
    public Message(String senderId, String senderName,String roomId, String content, LocalDateTime timestamp) {
        this.senderId   = senderId;
        this.senderName = senderName;
        this.roomId     = roomId;
        this.content    = content;
        this.timestamp  = timestamp;
    }

    // 게터
    public String getSenderId()   { return senderId; }
    public String getSenderName() { return senderName; }
    public String getRoomId()     { return roomId; }
    public String getContent()    { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // UI에 넘기기 편하도록
    public String getDisplayName(Map<String,String> nameMap) {
        return nameMap.getOrDefault(senderId, senderId);
    }
}
