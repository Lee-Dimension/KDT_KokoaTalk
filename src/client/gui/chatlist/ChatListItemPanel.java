package client.gui.chatlist;
// ChatListPanel에 뜨는 채팅방 하나의 GUI
import javax.swing.*;

import client.gui.style.Colors;

import java.awt.*;

public class ChatListItemPanel extends JPanel {
	
	/*--------------------필드----------------------*/
	private static final int PROFILE_IMG_SIZE = 40;
	private static final int PANEL_HEIGHT = 60;
	
	/*--------------------생성자----------------------*/
    public ChatListItemPanel(String chatNameStr, String lastMsgStr, String timeStr, ImageIcon profileIcon) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Colors.BGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // 프로필 이미지
        JLabel profileLabel = new JLabel(new ImageIcon(profileIcon.getImage().getScaledInstance(
        		PROFILE_IMG_SIZE, PROFILE_IMG_SIZE, Image.SCALE_SMOOTH)));
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