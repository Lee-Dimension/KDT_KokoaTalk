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
        for(int i=0; i<3; i++) {
            FriendItemPanel ff = new FriendItemPanel("친구 " + (i+1), "상태메시지", profileImg, true);
            friendListPanel.add(ff);
            friendListPanel.add(ff.getDetailPanel());
            JPanel detailPanel = ff.getDetailPanel();
            detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailPanel.setMaximumSize(new Dimension(380, 60));
            detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
            detailPanel.setBackground(Colors.BGROUNDDEEP); 
            
            detailPanel.add(Box.createHorizontalGlue());
		    JButton talkBtn = new JButton("대화하기");
			    HideBtnDesign.apply(talkBtn);
			    talkBtn.addActionListener(e -> {
			        // 버튼별 기능 분기
			    });
			    detailPanel.add(talkBtn);
	            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		    JButton nickChange = new JButton("별명변경");
			    HideBtnDesign.apply(nickChange);
			    nickChange.addActionListener(e -> {
			        // 버튼별 기능 분기
			    });
			    detailPanel.add(nickChange);
	            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		    JButton delFf = new JButton("즐찾제거");
			    HideBtnDesign.apply(delFf);
			    delFf.addActionListener(e -> {
			        // 버튼별 기능 분기
			    });
			    detailPanel.add(delFf);;
	            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));      

            detailPanel.add(Box.createHorizontalGlue());
        }

        // 친구 구분
        friendListPanel.add(new FriendSectionLabel("친구"));
        
        ImageIcon profileImg2 = new ImageIcon("src/KokoaTalkImg/free_people.png");
        
        // 친구 5명 (예시)
        for(int i=0; i<5; i++) {
            FriendItemPanel f = new FriendItemPanel("친구 " + (i+1), "상태메시지", profileImg, true);
            friendListPanel.add(f);
            friendListPanel.add(f.getDetailPanel());
            JPanel detailPanel = f.getDetailPanel();
            detailPanel.setMaximumSize(new Dimension(380, 60));
            detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
            detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailPanel.setBackground(Colors.BGROUNDDEEP);
            detailPanel.add(Box.createHorizontalGlue());
		    JButton talkBtn = new JButton("대화하기");
			    HideBtnDesign.apply(talkBtn);
			    talkBtn.addActionListener(e -> {
			        // 버튼별 기능 분기
			    });
			    detailPanel.add(talkBtn);
	            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		    JButton nickChange = new JButton("별명변경");
			    HideBtnDesign.apply(nickChange);
			    nickChange.addActionListener(e -> {
			        // 버튼별 기능 분기
			    });
			    detailPanel.add(nickChange);
	            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		    JButton plusFf = new JButton("즐찾추가");
			    HideBtnDesign.apply(plusFf);
			    plusFf.addActionListener(e -> {
			        // 버튼별 기능 분기
			    });
			    detailPanel.add(plusFf);;
	            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		    JButton delF = new JButton("친구삭제");
			    HideBtnDesign.apply(delF);
			    delF.addActionListener(e -> {
			        // 버튼별 기능 분기
			    });
			    detailPanel.add(delF);

            detailPanel.add(Box.createHorizontalGlue());
        }

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ScrollbarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollbarUI());
        add(scrollPane, BorderLayout.CENTER);
    }
}