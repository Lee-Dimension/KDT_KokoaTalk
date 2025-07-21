package server;

import java.net.*;
import java.io.*;
import java.util.*;

import client.service.UserManager;
import client.service.chat.ChatRoomService;
import model.ChatRoom;
import model.User;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final MainServer server;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private String currentChatRoomId;
    private String currentUserId;

    // 생성자: MainServer 인스턴스 주입
    public ClientHandler(Socket socket, MainServer server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.out    = new ObjectOutputStream(socket.getOutputStream());
        this.in     = new ObjectInputStream(socket.getInputStream());
    }

    // notifyNewRoom 게터
    public String getCurrentUserId() {
        return currentUserId;
    }
    public ObjectOutputStream getOut() {
        return out;
    }

    // 메시지 브로드캐스트
    private void broadcastMessage(String fromUserId, String chatRoomId, String msg) {
        String fromUserName = UserManager.loadUser(fromUserId).getName();
        for (ClientHandler client : MainServer.clients) {
            if (chatRoomId.equals(client.currentChatRoomId)) {
                try {
                    client.getOut().writeObject("NEW_MSG");
                    client.getOut().writeObject(fromUserId);
                    client.getOut().writeObject(fromUserName);
                    client.getOut().writeObject(chatRoomId);
                    client.getOut().writeObject(msg);
                    client.getOut().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void createRoom(List<String> memberIds, String title) throws IOException {
        out.writeObject("CREATE_ROOM");
        out.writeObject(memberIds);
        out.writeObject(title);
        out.flush();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String cmd;
                try {
                    cmd = (String) in.readObject();
                } catch (EOFException eof) {
                    System.out.println("클라이언트 연결 종료: " + socket);
                    break;
                }
                switch (cmd) {
                    case "SIGNUP": {
                        User newUser = (User) in.readObject();
                        String id = newUser.getId();
                        if (!UserManager.userExists(id)) {
                        	UserManager.saveUser(newUser);
                            out.writeObject("OK");
                        } else {
                            out.writeObject("FAIL");
                        }
                        out.flush();
                        break;
                    }
                    case "LOGIN": {
                        String id = (String) in.readObject();
                        User user = UserManager.loadUser(id);
                        out.writeObject(user);
                        out.flush();
                        break;
                    }
                    case "LOAD_USER": {
                        String id = (String) in.readObject();
                        User user = UserManager.loadUser(id);
                        out.writeObject(user);
                        out.flush();
                        break;
                    }
                    case "GET_FRIEND": {
                        String id = (String) in.readObject();
                        User user = UserManager.loadUser(id);
                        List<String> friends = (user != null) ? user.getFriends() : new ArrayList<>();
                        out.writeObject(friends);
                        out.flush();
                        break;
                    }
                    case "ADD_FRIEND": {
                        String myId = (String) in.readObject();
                        String friendId = (String) in.readObject();
                        User me = UserManager.loadUser(myId);
                        User fr = UserManager.loadUser(friendId);
                        if (me != null && fr != null && !me.getFriends().contains(friendId)) {
                            me.getFriends().add(friendId);
                            UserManager.saveUser(me);
                            out.writeObject("OK");
                        } else {
                            out.writeObject("FAIL");
                        }
                        out.flush();
                        break;
                    }
                    case "REMOVE_FRIEND": {
                        String myId = (String) in.readObject();
                        String friendId = (String) in.readObject();
                        User me = UserManager.loadUser(myId);
                        if (me != null && me.getFriends().remove(friendId)) {
                            UserManager.saveUser(me);
                            out.writeObject("OK");
                        } else {
                            out.writeObject("FAIL");
                        }
                        out.flush();
                        break;
                    }
                    case "ADD_FAVORITE": {
                        String myId = (String) in.readObject();
                        String friendId = (String) in.readObject();
                        User me = UserManager.loadUser(myId);
                        if (me != null && !me.getFavoriteFriends().contains(friendId)) {
                            me.getFavoriteFriends().add(friendId);
                            UserManager.saveUser(me);
                            out.writeObject("OK");
                        } else {
                            out.writeObject("ALREADY");
                        }
                        out.flush();
                        break;
                    }
                    case "REMOVE_FAVORITE": {
                        String myId = (String) in.readObject();
                        String friendId = (String) in.readObject();
                        User me = UserManager.loadUser(myId);
                        if (me != null && me.getFavoriteFriends().remove(friendId)) {
                            UserManager.saveUser(me);
                            out.writeObject("OK");
                        } else {
                            out.writeObject("NOT_FOUND");
                        }
                        out.flush();
                        break;
                    }
                    case "JOIN_ROOM": {
                        String joiningUserId = (String) in.readObject();
                        String joiningRoomId = (String) in.readObject();
                        this.currentUserId = joiningUserId;
                        this.currentChatRoomId = joiningRoomId;
                        System.out.println("[Server] " + joiningUserId + " joined " + joiningRoomId);
                        out.writeObject("OK");
                        out.flush();
                        break;
                    }
                    case "SEND_MSG": {
                        String fromUserId = (String) in.readObject();
                        String chatRoomId = (String) in.readObject();
                        String msg = (String) in.readObject();
                        this.currentChatRoomId = chatRoomId;

                        ChatRoomService chatRoomService = ChatRoomService.getInstance();
                        chatRoomService.addMessageToRoom(chatRoomId,
                            new model.Message(
                                fromUserId,
                                UserManager.loadUser(fromUserId).getName(),
                                chatRoomId,
                                msg,
                                java.time.LocalDateTime.now()
                            )
                        );


                        // 메시지 브로드캐스트
                        broadcastMessage(fromUserId, chatRoomId, msg);

                        // 채팅방 동기화 이벤트 발생
                        ChatRoom room = chatRoomService.getChatRoom(chatRoomId);
                        if (room != null) {
                            MainServer.notifyNewRoom(room);
                        }

                        out.writeObject("OK");
                        out.flush();
                        break;
                    }
                    case "CREATE_ROOM": {
                        List<String> memberIds = (List<String>) in.readObject();
                        String title = (String) in.readObject();

                        ChatRoomService chatRoomService = ChatRoomService.getInstance();
                        ChatRoom room = chatRoomService.createOrGetChatRoom(memberIds, title);

                        // 참여자들에게 NEW_ROOM 알림 전송
                        for (ClientHandler client : MainServer.clients) {
                            if (room.getMemberIds().contains(client.getCurrentUserId())) {
                                client.getOut().writeObject("NEW_ROOM");
                                client.getOut().writeObject(room.getChatRoomId());
                                client.getOut().writeObject(room.getTitle());
                                client.getOut().writeObject(room.getMemberIds());
                                client.getOut().flush();
                            }
                        }
                        break;
                    }

                    case "GET_CHATROOMS": {
                        String userId = (String) in.readObject();
                        ChatRoomService chatRoomService = ChatRoomService.getInstance();
                        List<ChatRoom> rooms = chatRoomService.getAllChatRoomsForUser(userId);
                        out.writeObject("CHATROOM_LIST");
                        out.writeObject(rooms);
                        out.flush();
                        System.out.println("[서버] " + userId + "의 채팅방 목록 요청");
                        break;
                    }
                    case "LEAVE_ROOM": {
                        String userId = (String) in.readObject();
                        String chatRoomId = (String) in.readObject();

                        ChatRoomService chatRoomService = ChatRoomService.getInstance();
                        ChatRoom room = chatRoomService.getChatRoom(chatRoomId);

                        if (room != null && room.getMemberIds().contains(userId)) {
                            room.removeMemberAndUpdateTitle(userId);
                            
                            if (room.getMemberIds().isEmpty()) {
                                // 참여자가 없으면 파일 삭제 및 맵에서 제거
                                chatRoomService.deleteChatRoom(chatRoomId);
                            } else {
                                // 남아있는 경우 저장만 갱신
                                chatRoomService.saveChatRoomToFile(room);
                            }

                            out.writeObject("OK");
                        } else {
                            out.writeObject("FAIL");
                        }
                        
                        out.flush();
                        break;
                    }
                    default:
                        System.out.println("[서버] 알 수 없는 명령: " + cmd);
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { in.close(); } catch (IOException ignored) {}
            try { out.close(); } catch (IOException ignored) {}
            try { socket.close(); } catch (IOException ignored) {}
            MainServer.clients.remove(this);
        }
    }
}
