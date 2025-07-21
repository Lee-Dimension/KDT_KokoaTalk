package client.gui.friendlist;

import java.awt.*;
import java.util.List;
import java.util.*;
import javax.swing.*;

import client.gui.MainFrame;
import client.gui.style.Colors;
import client.gui.style.HideButtonStyle;
import client.gui.style.ScrollbarStyle;
import client.gui.style.SectionLabelStyle;
import client.service.chat.ChatRoomService;
import client.service.chat.ChatWindowManager;
import client.service.friend.FriendService;
import model.ChatRoom;
import model.User;
import server.ClientHandler;
import server.MainServer;

/**
 * 친구 목록 전체 UI 패널
 */
public class FriendListPanel extends JPanel {
    private String currentUserId;
    private String currentUserName; 
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private MainFrame mainFrame;

    public FriendListPanel(String id, MainFrame mainFrame, JPanel mainPanel, CardLayout cardLayout) {
        this.currentUserId = id;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Colors.BGROUND);

        JPanel friendListPanel = new JPanel();
        friendListPanel.setBackground(Colors.BGROUND);
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
        friendListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- 내 프로필 ---
        User user = FriendService.getUser(id);
        this.currentUserName = (user != null ? user.getName() : "이름 없음");
        String statusMsg = "";
        List<String> favoriteSet = new ArrayList<>();

        if (user != null) {
            statusMsg = user.getStatusMsg();
            if (user.getFavoriteFriends() != null)
                favoriteSet = user.getFavoriteFriends();
        }

        MyProfilePanel myProfilePanel = new MyProfilePanel(currentUserName, statusMsg);
        myProfilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        friendListPanel.add(myProfilePanel);
        friendListPanel.add(myProfilePanel.getDetailPanel());

        // --- 친구 목록 ---
        ArrayList<String> allFriendIds = FriendService.getFriendList(id);

        // 즐겨찾기
        friendListPanel.add(new SectionLabelStyle("즐겨찾기"));
        for (String friendId : allFriendIds) {
            if (favoriteSet.contains(friendId)) {
                User friendUser = FriendService.getUser(friendId);
                if (friendUser != null) {
                    FriendItemPanel fip = new FriendItemPanel(friendUser.getName(), friendUser.getStatusMsg(), friendId);
                    friendListPanel.add(fip);
                    friendListPanel.add(createDetailPanel(fip, true, currentUserId, friendId));
                }
            }
        }

        // 전체 친구
        friendListPanel.add(new SectionLabelStyle("친구"));
        for (String friendId : allFriendIds) {
            User friendUser = FriendService.getUser(friendId);
            if (friendUser != null) {
                boolean isFavorite = favoriteSet.contains(friendId);
                FriendItemPanel fip = new FriendItemPanel(friendUser.getName(), friendUser.getStatusMsg(), friendId);
                friendListPanel.add(fip);
                friendListPanel.add(createDetailPanel(fip, isFavorite, currentUserId, friendId));
            }
        }

        friendListPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ScrollbarStyle());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollbarStyle());
        add(scrollPane, BorderLayout.CENTER);
    }

   
    // 버튼 패널 생성 함수
    public JPanel createDetailPanel(FriendItemPanel fip, boolean isFavorite, String currentUserId, String friendId) {
        JPanel detailPanel = fip.getDetailPanel();
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.setMaximumSize(new Dimension(380, 60));
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        detailPanel.setBackground(Colors.BGROUNDDEEP);
        detailPanel.add(Box.createHorizontalGlue());

        // --- 대화하기 버튼 ---
        JButton talkBtn = new JButton("대화하기");
        HideButtonStyle.apply(talkBtn);
        talkBtn.addActionListener(e -> {
            List<String> memberIds = Arrays.asList(currentUserId, friendId);
            Collections.sort(memberIds);
            String chatRoomId = String.join("_", memberIds);

            ChatRoomService chatRoomService = ChatRoomService.getInstance();
            ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);

            if (chatRoom != null) {
                // 채팅방이 이미 있으면 바로 열기
                ChatRoom finalRoom = chatRoom;
                SwingUtilities.invokeLater(() ->
                    ChatWindowManager.openChat(currentUserId, finalRoom.getChatRoomId(), finalRoom.getTitle()));
                System.out.println("여기는 FriendListPanel1");
            } else {
                // 채팅방이 없으면 이름 입력 → 생성 → 대화창 열기
                String title = JOptionPane.showInputDialog(this, "채팅방 이름을 입력하세요:", "채팅방 생성", JOptionPane.PLAIN_MESSAGE);
                if (title == null || title.trim().isEmpty()) return;
                System.out.println("여기는 FriendListPanel2");

                ChatRoom newRoom = chatRoomService.createOrGetChatRoom(memberIds, title);
                // 딜레이 주고 대화창 띄우기
                SwingUtilities.invokeLater(() -> {
                    ChatWindowManager.openChat(currentUserId, newRoom.getChatRoomId(), title);
                });              
            }
            mainFrame.refreshChatListPanel();
            System.out.println("여기는 FriendListPanel1");

            
        });

        detailPanel.add(talkBtn);
        detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        // --- 별명변경 버튼 (미구현) ---
        JButton nickChange = new JButton("별명변경");
        HideButtonStyle.apply(nickChange);
        detailPanel.add(nickChange);
        detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        // --- 즐겨찾기 / 제거 / 삭제 ---
        if (isFavorite) {
            JButton delFf = new JButton("즐찾제거");
            HideButtonStyle.apply(delFf);
            delFf.addActionListener(e -> {
                boolean success = FriendService.removeFavorite(currentUserId, friendId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "즐겨찾기에서 제거되었습니다.");
                    reloadFriendListUI();
                } else {
                    JOptionPane.showMessageDialog(this, "오류가 발생했습니다.");
                }
            });
            detailPanel.add(delFf);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        } else {
            JButton plusFf = new JButton("즐찾추가");
            HideButtonStyle.apply(plusFf);
            plusFf.addActionListener(e -> {
                boolean success = FriendService.addFavorite(currentUserId, friendId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "즐겨찾기에 추가되었습니다.");
                    reloadFriendListUI();
                } else {
                    JOptionPane.showMessageDialog(this, "오류가 발생했습니다.");
                }
            });
            detailPanel.add(plusFf);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

            JButton delF = new JButton("친구삭제");
            HideButtonStyle.apply(delF);
            delF.addActionListener(e -> {
                boolean success = FriendService.removeFriend(currentUserId, friendId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "친구가 삭제되었습니다.");
                    reloadFriendListUI();
                } else {
                    JOptionPane.showMessageDialog(this, "친구 삭제 중 오류가 발생했습니다.");
                }
            });
            detailPanel.add(delF);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        detailPanel.add(Box.createHorizontalGlue());
        return detailPanel;
    }

    /**
     * 친구 패널 전체 갱신
     */
    private void reloadFriendListUI() {
        mainPanel.remove(this);
        FriendListPanel refreshed = new FriendListPanel(currentUserId, mainFrame, mainPanel, cardLayout);
        mainPanel.add(refreshed, "FRIEND");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "FRIEND");
    }
}
