package client.service.chat;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import model.ChatRoom;
import model.Message;

public class ChatClientService {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Thread listenThread;
    private Consumer<ChatRoom> newRoomHandler; 
    private Consumer<Message> messageHandler;    // 메시지 수신 콜백 (UI쪽에서 설정)
    
    public ChatClientService(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        startListening();
        System.out.println("여기는 ChatClientService");
    }
    // 새로운 대화방 열면 초대된 사람들은 방목록 갱신
    public void setNewRoomHandler(Consumer<ChatRoom> handler) {
        System.out.println("[UI] 새 채팅방 추가됨");
        this.newRoomHandler = handler;
    }

    public void setMessageHandler(Consumer<Message> handler) {
        this.messageHandler = handler;
    }

    public void sendMessage(String fromUserId, String chatRoomId, String msg) throws IOException {
        out.writeObject("SEND_MSG");
        out.writeObject(fromUserId);
        out.writeObject(chatRoomId);
        out.writeObject(msg);
        out.flush();
    }

    public void startListening() {
        listenThread = new Thread(() -> {
            try {
                while (true) {
                    Object cmdObj;
                    try {
                    	cmdObj = in.readObject();
                    } catch (SocketException se) {
                        // 소켓 닫힘 → 루프 종료
                        break;
                    } catch (EOFException eof) {
                        // 스트림이 끝나면 루프 종료
                        break;
                    }
                    if (!(cmdObj instanceof String)) continue;
                    String cmd = (String) cmdObj;
                    
                    // **OK 응답 무시**
                    if ("OK".equals(cmd)) {
                        continue;
                    }
                    // 1) 새 방 생성 알림
                    if ("NEW_ROOM".equals(cmd) && newRoomHandler != null) {
                        String roomId      = (String) in.readObject();
                        String title       = (String) in.readObject();
                        @SuppressWarnings("unchecked")
                        java.util.List<String> members = (java.util.List<String>) in.readObject();
                        ChatRoom room = new ChatRoom(roomId, members, title, "", "", "");
                        newRoomHandler.accept(room);
                        System.out.println("[클라] NEW_ROOM 수신");
                    }
                    // 2) 메시지 수신 알림
                    if ("NEW_MSG".equals(cmd)) {
                        String fromUserId   = (String) in.readObject();
                        String fromUserName = (String) in.readObject(); 
                        String chatRoomId   = (String) in.readObject();
                        String msg          = (String) in.readObject();

                        Message m = new Message(
                        		  fromUserId, fromUserName, chatRoomId, msg, LocalDateTime.now()
                        );
                        if (messageHandler != null) {
                            messageHandler.accept(m);
                        }
                        System.out.println("서버에서 온 알림 : 새 메세지가 있어요!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        listenThread.start();
    }

    public void close() {
        try {
            if (listenThread != null) listenThread.interrupt();
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void joinRoom(String userId,String roomId) throws IOException {
        out.writeObject("JOIN_ROOM");
        out.writeObject(userId);
        out.writeObject(roomId);
        out.flush();
    }
}
