package KokoaTalk.Profile;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import KokoaTalk.ScrollbarUI;
import KokoaTalk.Colors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FriendPanel extends JPanel {

	public FriendPanel(String id) {
	    setLayout(new BorderLayout());
	    setBackground(Colors.BGROUND);

	    JPanel friendListPanel = new JPanel();
	    friendListPanel.setBackground(Colors.BGROUND);
	    friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
	    friendListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

	    // ✅ 1. userList.txt 에서 id로 name과 statusMsg 가져오기
	    String name = "이름 없음";
	    String statusMsg = "";

	    try (BufferedReader br = new BufferedReader(new FileReader("src/KokoaTalk/UserList/userList.txt"))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] tokens = line.split(",");
	            if (tokens.length >= 3) {
	                String userId = tokens[0].trim();
	                if (userId.equals(id)) {
	                    name = tokens[1].trim();
	                    statusMsg = tokens[2].trim();
	                    break;
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // ✅ 2. 읽어온 문자열로 MyProfilePanel 생성
	    MyProfilePanel myProfilePanel = new MyProfilePanel(name, statusMsg);
	    friendListPanel.add(myProfilePanel);
	    friendListPanel.add(myProfilePanel.getDetailPanel());
	    myProfilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

	    // -------------------- 파일에서 친구정보 불러오기 -----------------------
	    ArrayList<Friend> friendList = FriendLoader.loadFriendsFromFile("src/KokoaTalk/FriendList/" + id + ".txt");

	    ArrayList<Friend> favoriteFriends = new ArrayList<>();
	    ArrayList<Friend> normalFriends = new ArrayList<>();
	    for (Friend f : friendList) {
	        if (f.isFavorite())
	            favoriteFriends.add(f);
	        else
	            normalFriends.add(f);
	    }

	    // ------------------- 즐겨찾기 구분 라벨 & 목록 출력 ----------------------
	    friendListPanel.add(new FriendSectionLabel("즐겨찾기"));
	    for (Friend f : favoriteFriends) {
	        FriendItemPanel fip = new FriendItemPanel(f.getName(), f.getStatusMsg());
	        friendListPanel.add(fip);
	        friendListPanel.add(createDetailPanel(fip, true));
	    }

	    // ------------------- 친구 구분 라벨 & 목록 출력 ----------------------
	    friendListPanel.add(new FriendSectionLabel("친구"));
	    for (Friend f : friendList ) {
	        FriendItemPanel fip = new FriendItemPanel(f.getName(), f.getStatusMsg());
	        friendListPanel.add(fip);
	        friendListPanel.add(createDetailPanel(fip, false));
	    }

	    JScrollPane scrollPane = new JScrollPane(friendListPanel);
	    friendListPanel.add(Box.createVerticalGlue());
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
