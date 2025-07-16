package KokoaTalk.ChatList;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class ChatListPanel extends JPanel {
    public ChatListPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 1. 광고라벨
        JLabel titleLabel = new JLabel("광고 받습니다~");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 16, 25, 16));
        add(titleLabel, BorderLayout.NORTH);

        // 2. 채팅방 목록 패널 (BoxLayout)
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(Colors.BGROUND);

        // 3. 채팅방 예시 데이터 (실제로는 리스트로 받아서 생성)
        ImageIcon profileIcon = new ImageIcon("src/KokoaTalkImg/my_profile.png");
        for (int i = 0; i < 15; i++) {
            ChatListItemPanel chatItem = new ChatListItemPanel(
                "채팅방 " + (i + 1),
                "최근 메시지 예시 내용...",
                "오후 " + (i+1) + ":00",
                profileIcon
            );
            chatListPanel.add(chatItem);
            chatListPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        }

        JScrollPane scrollPanel = new JScrollPane(chatListPanel);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(scrollPanel, BorderLayout.CENTER);
    }
}