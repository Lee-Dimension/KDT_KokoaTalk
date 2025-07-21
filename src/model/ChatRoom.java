package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 채팅방 한 개의 정보를 담는 객체
public class ChatRoom implements Serializable {
    private String chatRoomId;             // 고유 id
    private List<String> memberIds;        // 참여자 id 리스트

    private String title;
    private String lastMessage;
    private String time;
    private String profileImagePath;

    // 메시지 기록
    private List<Message> messageHistory;

    // 기본 생성자
    public ChatRoom(String chatRoomId, List<String> memberIds) {
        this.chatRoomId = chatRoomId;
        this.memberIds = memberIds;
        this.title = String.join(", ", memberIds); //파일명은 인원 id로 만듦.
        this.lastMessage = "";
        this.time = "";
        this.profileImagePath = "";
        this.messageHistory = new ArrayList<>();
    }

    // 오버로딩 생성자
    public ChatRoom(String chatRoomId, List<String> memberIds,
                    String title, String lastMessage, String time, String profileImagePath) {
        this.chatRoomId = chatRoomId;
        this.memberIds = memberIds;
        this.title = title;
        this.lastMessage = lastMessage;
        this.time = time;
        this.profileImagePath = profileImagePath;
        this.messageHistory = new ArrayList<>();
    }

    // 게터
    public String getChatRoomId() { return chatRoomId; }
    public List<String> getMemberIds() { return memberIds; }
    public String getTitle() { return title; }
    public String getLastMessage() { return lastMessage; }
    public String getTime() { return time; }
    public String getProfileImagePath() { return profileImagePath; }
    public List<Message> getMessageHistory() { return messageHistory; }

    // 메시지 추가
    public void addMessage(Message message) {
        messageHistory.add(message);
        this.lastMessage = message.getContent();
        this.time = message.getTimestamp().toString(); // 시간도 갱신
    }

    // 특정 시점 이후의 메시지 반환
    public List<Message> getMessagesAfter(LocalDateTime time) {
        return messageHistory.stream()
                .filter(m -> m.getTimestamp().isAfter(time))
                .collect(Collectors.toList());
    }
    public void removeMemberAndUpdateTitle(String userId) {
        memberIds.remove(userId);
        this.title = String.join(", ", memberIds);
    }
}
