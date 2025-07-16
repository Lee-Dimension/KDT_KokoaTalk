package KokoaTalk;

import javax.swing.*;
import java.awt.*;

public class ChatListPanel extends JPanel {
    public ChatListPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 1. 광고라벨
        JLabel titleLabel = new JLabel("광고 받습니다~");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 16, 25, 16));
        add(titleLabel, BorderLayout.NORTH);

        // 2. 채팅방 목록 패널
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(Colors.BGROUND);

        // 채팅방 예시 15개
        for (int i = 0; i < 15; i++) {
            JButton chatItem = new JButton();
            chatItem.setLayout(new BoxLayout(chatItem, BoxLayout.X_AXIS));
            chatItem.setBackground(Colors.BGROUND);
            chatItem.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

            // 채팅방 프로필 (그룹이면 여러 이미지 합성도 가능)
            ImageIcon profileIcon = new ImageIcon("src/KokoaTalkImg/my_profile.png");
            JLabel profileLabel = new JLabel(new ImageIcon(profileIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            chatItem.add(profileLabel);

            chatItem.add(Box.createRigidArea(new Dimension(10, 0)));

            // 채팅방 이름/최근 메시지/시간
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Colors.BGROUND);

            JLabel chatName = new JLabel("채팅방 " + (i + 1));
            chatName.setFont(new Font("SansSerif", Font.BOLD, 14));
            JLabel lastMsg = new JLabel("최근 메시지 예시 내용...");
            lastMsg.setFont(new Font("SansSerif", Font.PLAIN, 12));

            infoPanel.add(chatName);
            infoPanel.add(lastMsg);

            chatItem.add(infoPanel);

            // 우측에 시간 표시 (예: 오후 1:23)
            chatItem.add(Box.createHorizontalGlue());
            JLabel timeLabel = new JLabel("오후 " + (i+1) + ":00");
            timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
            timeLabel.setForeground(Color.GRAY);
            chatItem.add(timeLabel);

            chatListPanel.add(chatItem);
            chatListPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        }

        JScrollPane scrollPane = new JScrollPane(chatListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }
}
