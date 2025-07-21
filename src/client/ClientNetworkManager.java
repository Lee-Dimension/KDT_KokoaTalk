package client;

import model.ChatRoom;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientNetworkManager {
    private static ClientNetworkManager instance;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private ClientNetworkManager() {
        try {
            this.socket = new Socket("localhost", 7777); // 서버 주소와 포트
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in  = new ObjectInputStream(socket.getInputStream());
            System.out.println("여기는 ClientNetworkManager");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 싱글톤 인스턴스 반환
    public static ClientNetworkManager getInstance() {
        if (instance == null) {
            instance = new ClientNetworkManager();
        }
        return instance;
    }

    // 특정 유저의 채팅방 목록 요청
    public List<ChatRoom> getChatRoomsForUser(String userId) {
        List<ChatRoom> result = new ArrayList<>();
        try {
            out.writeObject("GET_CHATROOMS");
            System.out.println("여기는 ClientNetworkManager");
            out.writeObject(userId);
            out.flush();

            String response = (String) in.readObject();
            if ("CHATROOM_LIST".equals(response)) {
                result = (List<ChatRoom>) in.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //채팅방 나가기 요청
    public boolean leaveChatRoom(String userId, String chatRoomId) {
        try {
            out.writeObject("LEAVE_ROOM");
            out.writeObject(userId);
            out.writeObject(chatRoomId);
            out.flush();

            String response = (String) in.readObject();
            return "OK".equals(response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 필요 시 소켓 종료 메서드 추가
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
