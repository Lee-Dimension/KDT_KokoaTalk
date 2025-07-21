package client.service.chat;
// 채팅창 중복 열림 방지.
import java.util.*;

import client.gui.chatting.ChattingMainFrame;

public class ChatWindowManager {
    private static final Map<String, ChattingMainFrame> chatFrames = new HashMap<>();

    // 채팅방 이름도 파라미터로 추가
    public static void openChat(String userId,String chatRoomId, String chatRoomName) {
        ChattingMainFrame frame = chatFrames.get(chatRoomId);
        if (frame != null && frame.isDisplayable()) {
            frame.toFront();
            frame.requestFocus();
        } else {
            // 생성자에 chatRoomName 넘김
            frame = new ChattingMainFrame(userId,chatRoomId, chatRoomName);
            chatFrames.put(chatRoomId, frame);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    chatFrames.remove(chatRoomId);
                }
            });
        }
    }
}
