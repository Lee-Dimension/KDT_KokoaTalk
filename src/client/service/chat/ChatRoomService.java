package client.service.chat;

import model.ChatRoom;
import model.Message;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//서버에서 채팅방을 저장 및 조회
public class ChatRoomService {
    private static final String BASE_DIR = "src/data/chatrooms/";
    private final Map<String, ChatRoom> chatRoomMap = new HashMap<>();

    // 싱글톤 인스턴스
    private static final ChatRoomService instance = new ChatRoomService();
    public static ChatRoomService getInstance() {
    	return instance;
    }

    private ChatRoomService() {
        loadAllChatRooms(); // 서버 시작 시 전체 불러오기
    }

    //채팅방 생성 또는 조회 (없으면 새로 생성)
    public ChatRoom createOrGetChatRoom(List<String> memberIds, String title) {
        List<String> sorted = new ArrayList<>(memberIds);
        Collections.sort(sorted);
        String chatRoomId = String.join("_", sorted);

        if (chatRoomMap.containsKey(chatRoomId)) {
            return chatRoomMap.get(chatRoomId);
        }
        ChatRoom room = new ChatRoom(chatRoomId, sorted, title, "", "", "");
        chatRoomMap.put(chatRoomId, room);
        saveChatRoomToFile(room);
        System.out.println("[방 생성] 멤버 리스트 = " + memberIds); 
        return room;
    }

    //채팅방 ID로 조회
    public ChatRoom getChatRoom(String chatRoomId) {
        return chatRoomMap.get(chatRoomId);
    }

    //특정 유저가 포함된 모든 채팅방 조회
    public List<ChatRoom> getAllChatRoomsForUser(String userId) {
    	System.out.println("[검색] userId = '" + userId + "'");
    	System.out.println("[검색] 모든 방 멤버들:");
    	for (ChatRoom room : chatRoomMap.values()) {
    	    System.out.println(" - " + room.getChatRoomId() + ": " + room.getMemberIds());
    	}
        File dir = new File(BASE_DIR);
        if (!dir.exists()) {
            System.out.println("[서버] 채팅방 디렉토리 없음");
            return Collections.emptyList();
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".ser"));
        if (files == null) {
            System.out.println("[서버] 채팅방 파일 없음");
            return Collections.emptyList();
        }

        List<ChatRoom> result = new ArrayList<>();

        for (File file : files) {
            System.out.println("[서버] 읽는 중: " + file.getName());
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                ChatRoom room = (ChatRoom) ois.readObject();
                System.out.println(" └─ 채팅방 ID: " + room.getChatRoomId() + " / 멤버: " + room.getMemberIds());
                
                // 메모리 맵에도 넣자
                if (!chatRoomMap.containsKey(room.getChatRoomId())) {
                    chatRoomMap.put(room.getChatRoomId(), room);
                }
                
                if (room.getMemberIds().contains(userId)) {
                    result.add(room);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(" └─ 파일 읽기 실패");
                e.printStackTrace();
            }
        }

        System.out.println("[서버] 최종 반환 채팅방 수: " + result.size());
        return result;
    }

    //메시지 추가 및 저장
    public void addMessageToRoom(String chatRoomId, Message message) {
        ChatRoom room = getChatRoom(chatRoomId);
        if (room == null) return;

        room.addMessage(message);
        saveChatRoomToFile(room);
    }

    //채팅방 1개를 파일로 저장
    public void saveChatRoomToFile(ChatRoom room) {
        try {
            File dir = new File(BASE_DIR);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(BASE_DIR + room.getChatRoomId() + ".ser");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(room);
            }
            chatRoomMap.put(room.getChatRoomId(), room);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 서버 실행 시 모든 채팅방 로딩
    public void loadAllChatRooms() {
        File dir = new File(BASE_DIR);
        if (!dir.exists()) return;

        File[] files = dir.listFiles((d, name) -> name.endsWith(".ser"));
        if (files == null) return;

        for (File file : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                ChatRoom room = (ChatRoom) ois.readObject();
                chatRoomMap.put(room.getChatRoomId(), room);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    //채팅방 삭제
    public void deleteChatRoom(String chatRoomId) {
        chatRoomMap.remove(chatRoomId);
        File file = new File(BASE_DIR + chatRoomId + ".ser");
        if (file.exists()) {
            file.delete();
        }
    }
    
}
