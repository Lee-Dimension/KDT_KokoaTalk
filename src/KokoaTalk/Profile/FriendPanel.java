package KokoaTalk.Profile;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import KokoaTalk.ScrollbarUI;
import KokoaTalk.Colors;
import java.io.*;

public class FriendPanel extends JPanel {

    public FriendPanel(String id) {
        setLayout(new BorderLayout());
        setBackground(Colors.BGROUND);

        // 리스트 전체를 담을 패널(세로)
        JPanel friendListPanel = new JPanel();
        friendListPanel.setBackground(Colors.BGROUND);
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
        friendListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- 내 프로필 ---
        UserClass user = UserFileManager.loadUser("src/UserList/"+id + ".ser");
        String name = "이름 없음";
        String statusMsg = "";

        if (user != null) {
            name = user.getName();
            statusMsg = user.getStatusMsg();
        }

        MyProfilePanel myProfilePanel = new MyProfilePanel(name, statusMsg);
        friendListPanel.add(myProfilePanel);
        friendListPanel.add(myProfilePanel.getDetailPanel());
        myProfilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- 친구 txt파일에서 id, 즐겨찾기 여부 읽기 ---
     // 친구목록 파일에서 전체 친구와 즐겨찾기 분류
        ArrayList<String> allFriendIds = new ArrayList<>();
        HashSet<String> favoriteSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/FriendList/" + id + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String friendId = tokens[0].trim();
                    boolean isFavorite = tokens[1].trim().equalsIgnoreCase("TRUE");
                    allFriendIds.add(friendId);
                    if (isFavorite) favoriteSet.add(friendId);
                }
            }
        } catch (FileNotFoundException e) {
            // 파일이 없으면 무시
        } catch (IOException e) {
            e.printStackTrace();
        }

        // --- 즐겨찾기 친구 표시 ---
        friendListPanel.add(new FriendSectionLabel("즐겨찾기"));
        for (String friendId : allFriendIds) {
            if (favoriteSet.contains(friendId)) {
                UserClass friendUser = UserFileManager.loadUser("src/UserList/" + friendId + ".ser");
                if (friendUser != null) {
                    FriendItemPanel fip = new FriendItemPanel(friendUser.getName(), friendUser.getStatusMsg());
                    friendListPanel.add(fip);
                    friendListPanel.add(createDetailPanel(fip, true));
                }
            }
        }

        // --- 모든 친구(=일반+즐찾) 표시 ---
        friendListPanel.add(new FriendSectionLabel("친구"));
        for (String friendId : allFriendIds) {
            UserClass friendUser = UserFileManager.loadUser("src/UserList/" + friendId + ".ser");
            if (friendUser != null) {
                boolean isFavorite = favoriteSet.contains(friendId);
                FriendItemPanel fip = new FriendItemPanel(friendUser.getName(), friendUser.getStatusMsg());
                friendListPanel.add(fip);
                friendListPanel.add(createDetailPanel(fip, isFavorite));
            }
        }

        friendListPanel.add(Box.createVerticalGlue());

        // --- 스크롤바로 감싸기 ---
        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ScrollbarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollbarUI());
        add(scrollPane, BorderLayout.CENTER);
    }

    // 디테일 패널(버튼들) 생성 함수
    public JPanel createDetailPanel(FriendItemPanel fip, boolean isFavorite) {
        JPanel detailPanel = fip.getDetailPanel();
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.setMaximumSize(new Dimension(380, 60));
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        detailPanel.setBackground(Colors.BGROUNDDEEP);
        detailPanel.add(Box.createHorizontalGlue());

        JButton talkBtn = new JButton("대화하기");
        HideBtnDesign.apply(talkBtn);
        detailPanel.add(talkBtn);
        detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton nickChange = new JButton("별명변경");
        HideBtnDesign.apply(nickChange);
        detailPanel.add(nickChange);
        detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        if(isFavorite) {
            JButton delFf = new JButton("즐찾제거");
            HideBtnDesign.apply(delFf);
            detailPanel.add(delFf);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        } else {
            JButton plusFf = new JButton("즐찾추가");
            HideBtnDesign.apply(plusFf);
            detailPanel.add(plusFf);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

            JButton delF = new JButton("친구삭제");
            HideBtnDesign.apply(delF);
            detailPanel.add(delF);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        detailPanel.add(Box.createHorizontalGlue());
        return detailPanel;
    }
}
