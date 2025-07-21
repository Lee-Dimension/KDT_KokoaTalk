// MainServer.java: 채팅 서버 로직을 담당
package server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import model.ChatRoom;
import model.User;

public class MainServer {
    public static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    /** 서버 시작 */
    public void start(int port) throws IOException {
        client.service.chat.ChatRoomService.getInstance(); 
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("서버 시작! 포트: " + port);
        while (true) {
            Socket sock = serverSocket.accept();
            ClientHandler handler = new ClientHandler(sock, this);
            clients.add(handler);
            new Thread(handler).start();
        }
    }
    

    //채팅방 생성/참여 시 멤버들에게 NEW_ROOM 이벤트 브로드캐스트 
    public static void notifyNewRoom(ChatRoom room) {
        for (ClientHandler client : clients) {
            try {
                if (room.getMemberIds().contains(client.getCurrentUserId())) {
                    ObjectOutputStream out = client.getOut();
                    out.writeObject("NEW_ROOM");
                    out.writeObject(room.getChatRoomId());
                    out.writeObject(room.getTitle());
                    out.writeObject(room.getMemberIds());
                    out.flush();
                    System.out.println("[서버] " + client.getCurrentUserId() + " 에게 NEW_ROOM 전송");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
