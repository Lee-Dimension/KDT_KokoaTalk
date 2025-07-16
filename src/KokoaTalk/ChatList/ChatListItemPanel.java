package KokoaTalk.ChatList;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class ChatListItemPanel extends JPanel {
    public ChatListItemPanel(String chatNameStr, String lastMsgStr, String timeStr, ImageIcon profileIcon) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Colors.BGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // 프로필 이미지
        JLabel profileLabel = new JLabel(new ImageIcon(profileIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        profileLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(profileLabel);

        add(Box.createRigidArea(new Dimension(10, 0)));

        // 이름/메시지
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Colors.BGROUND);
        infoPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel chatName = new JLabel(chatNameStr);
        chatName.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel lastMsg = new JLabel(lastMsgStr);
        lastMsg.setFont(new Font("SansSerif", Font.PLAIN, 12));

        infoPanel.add(chatName);
        infoPanel.add(lastMsg);

        add(infoPanel);

        add(Box.createHorizontalGlue());

        // 시간
        JLabel timeLabel = new JLabel(timeStr);
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        add(timeLabel);
    }
}