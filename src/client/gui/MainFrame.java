package client.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

import client.gui.chatlist.ChatListPanel;
import client.gui.friendlist.FriendListPanel;
import client.gui.style.Colors;
import client.service.chat.ChatClientService;
import client.service.friend.FriendService;

public class MainFrame extends JFrame {
    private static final String IMG_FRIEND = "src/data/icon/account_circle.png";
    private static final String IMG_CHAT   = "src/data/icon/chat_bubble.png";
    private static final int BTN_ICON_SIZE = 50;

    private final CardLayout cardLayout;
    private final JPanel   mainPanel;
    private final ChatClientService chatClientService;
    private final String    userId;

    public MainFrame(String id) {
        super("코코아톡");
        this.userId = id;

        // 1) ChatClientService를 한 번만 생성
        try {
            chatClientService = new ChatClientService("localhost", 7777);
            chatClientService.startListening(); 
            System.out.println("클라이언트가 서버의 말을 듣는중~");
        } catch (IOException e) {
            throw new RuntimeException("채팅 서비스 연결에 실패했습니다.", e);
        }

        // 2) NEW_ROOM 이벤트 핸들러: 방 목록 갱신
        chatClientService.setNewRoomHandler(room -> {
            if (!room.getMemberIds().contains(userId)) return;
            SwingUtilities.invokeLater(() -> refreshChatListPanel());
            System.out.println("MainFrame 에서 방목록 갱신"); //디버깅용
        });

        // UI 초기화
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 800);
        setMinimumSize(new Dimension(400, 800));
        setLocationRelativeTo(null);

        JPanel screenPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill    = GridBagConstraints.BOTH;
        c.gridx   = 0;
        c.weightx = 1.0;

        Header header = new Header("친구");
        c.gridy   = 0;
        c.weighty = 0.1;
        screenPanel.add(header.getPanel(), c);

        mainPanel  = new JPanel(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();
        mainPanel .setBackground(Color.LIGHT_GRAY);

        // 3) 패널 생성 시 서비스 인자 제거
        mainPanel.add(new FriendListPanel(id, this, mainPanel, cardLayout), "FRIEND");
        mainPanel.add(new ChatListPanel(id, this),"CHAT");

        c.gridy   = 1;
        c.weighty = 9.8;
        screenPanel.add(mainPanel, c);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        btnPanel.setBackground(Colors.BGROUNDDEEP);
        JButton btnFriend = createIconButton(IMG_FRIEND, BTN_ICON_SIZE);
        JButton btnChat   = createIconButton(IMG_CHAT,   BTN_ICON_SIZE);
        btnPanel.add(btnFriend);
        btnPanel.add(btnChat);

        c.gridy   = 2;
        c.weighty = 0.1;
        screenPanel.add(btnPanel, c);

        // 친구 버튼
        btnFriend.addActionListener(e -> {
            cardLayout.show(mainPanel, "FRIEND");
            header.setHeaderText("친구");
            refreshFriendListPanel();
            JButton plusBtn = header.getPlusButton();
            for (ActionListener al : plusBtn.getActionListeners())
                plusBtn.removeActionListener(al);
            plusBtn.addActionListener(e2 -> handleAddFriend());
        });
        // 채팅 버튼
        btnChat.addActionListener(e -> {
            cardLayout.show(mainPanel, "CHAT");
            refreshChatListPanel();
            System.out.println("여기는 MainFrame");

            header.setHeaderText("채팅");
        });

        getContentPane().add(screenPanel);
        setVisible(true);

        btnFriend.doClick(); // 기본 FRIEND 화면
    }

    public ChatClientService getChatClientService() {
        return chatClientService;
    }
    public void refreshFriendListPanel() {
        // 기존 FriendListPanel 제거
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof FriendListPanel) {
                mainPanel.remove(c);
                break;
            }
        }
        // 새 FriendListPanel 추가
        mainPanel.add(new FriendListPanel(userId, this, mainPanel, cardLayout), "FRIEND");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "FRIEND");
    }

    public void refreshChatListPanel() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof ChatListPanel) {
                mainPanel.remove(comp);
                break;
            }
        }
        mainPanel.add(new ChatListPanel(userId, this), "CHAT");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "CHAT");
    }

    private void handleAddFriend() {
        String friendId = JOptionPane.showInputDialog(
            this, "추가할 친구의 ID를 입력하세요:"
        );
        if (friendId == null || friendId.trim().isEmpty() || friendId.equals(userId)) 
            return;

        boolean result = FriendService.addFriend(userId, friendId.trim());
        if (result) {
            JOptionPane.showMessageDialog(
                this, friendId + "님이 친구로 추가되었습니다."
            );
            refreshFriendListPanel();
        } else {
            JOptionPane.showMessageDialog(
                this, "존재하지 않거나 이미 친구입니다."
            );
        }
    }

    private JButton createIconButton(String imgPath, int size) {
        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage()
                        .getScaledInstance(size, size, Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(img));
        btn.setBackground(Colors.BGROUNDDEEP);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }

}
