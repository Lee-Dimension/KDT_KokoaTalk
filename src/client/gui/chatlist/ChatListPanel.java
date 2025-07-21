package client.gui.chatlist;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import model.ChatRoom;
import client.ClientNetworkManager;
import client.gui.MainFrame;
import client.gui.style.Colors;
import client.service.chat.ChatWindowManager;

public class ChatListPanel extends JPanel {
    private static final String PROFILE_IMG_PATH = "src/data/profile_img/default.jpg";
    private static final int PANEL_HEIGHT = 60;

    private final String currentUserId;
    private final MainFrame mainFrame;

    public ChatListPanel(String currentUserId, MainFrame mainFrame) {
        this.currentUserId = currentUserId;
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("광고 받습니다~");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 16, 25, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Colors.BGROUND);
        
        // 서버에서 전체 채팅방 불러오기
        List<ChatRoom> allRooms = ClientNetworkManager.getInstance().getChatRoomsForUser(currentUserId);
        System.out.println("여기는 ChatListPanel");
        // 내 ID가 포함된 채팅방만 표시
        List<ChatRoom> myRooms = allRooms.stream()
                .filter(room -> room.getMemberIds().contains(currentUserId))
                .collect(Collectors.toList());

        for (ChatRoom room : myRooms) {
            ChatListItemPanel itemPanel = new ChatListItemPanel(
                    room.getTitle(),
                    room.getLastMessage(),
                    room.getTime(),
                    new ImageIcon(PROFILE_IMG_PATH)
            );
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));

            itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                private long pressTime;

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    pressTime = System.currentTimeMillis();
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    long elapsed = System.currentTimeMillis() - pressTime;

                    if (elapsed > 500 || SwingUtilities.isRightMouseButton(e)) {
                        int confirm = JOptionPane.showConfirmDialog(
                            itemPanel,
                            "채팅방에서 나가시겠습니까?",
                            "채팅방 나가기",
                            JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            // 서버에 나가기 요청
                            boolean success = ClientNetworkManager.getInstance()
                                    .leaveChatRoom(currentUserId, room.getChatRoomId());

                            if (success) {
                                JOptionPane.showMessageDialog(itemPanel, "채팅방에서 나갔습니다.");
                                mainFrame.refreshChatListPanel();
                            } else {
                                JOptionPane.showMessageDialog(itemPanel, "나가기 실패");
                            }
                        }
                    } else if (SwingUtilities.isLeftMouseButton(e) && elapsed <= 500) {
                        ChatWindowManager.openChat(
                            currentUserId,
                            room.getChatRoomId(),
                            room.getTitle()
                        );
                    }
                }
            });

            listPanel.add(itemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }
}
