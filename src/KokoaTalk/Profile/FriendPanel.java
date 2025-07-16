package KokoaTalk.Profile;

import java.awt.*;
import javax.swing.*;
import KokoaTalk.ScrollbarUI;
import KokoaTalk.Colors;

public class FriendPanel extends JPanel {
    public FriendPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel friendListPanel = new JPanel();
        friendListPanel.setBackground(Colors.BGROUND);
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
        friendListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // 내 프로필
        MyProfilePanel myProfilePanel = new MyProfilePanel();
        friendListPanel.add(myProfilePanel);
        friendListPanel.add(myProfilePanel.getDetailPanel());
        myProfilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        // 즐겨찾기 구분
        friendListPanel.add(new FriendSectionLabel("즐겨찾기"));

        ImageIcon profileImg = new ImageIcon("src/KokoaTalkImg/free_people.png");
        // 즐겨찾기 5명 (예시)
        for(int i=0; i<5; i++) {
            FriendItemPanel f = new FriendItemPanel("친구 " + (i+1), "상태메시지", profileImg, true);
            friendListPanel.add(f);
            friendListPanel.add(f.getDetailPanel());
        }

        // 친구 구분
        friendListPanel.add(new FriendSectionLabel("친구"));
        // 친구 5명 (예시)
        for(int i=0; i<5; i++) {
            FriendItemPanel f = new FriendItemPanel("친구 " + (i+1), "상태메시지", profileImg, false);
            friendListPanel.add(f);
        }

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ScrollbarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollbarUI());
        add(scrollPane, BorderLayout.CENTER);
    }
}
