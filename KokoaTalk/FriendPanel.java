package KokoaTalk;

import java.awt.*;
import javax.swing.*;

public class FriendPanel extends JPanel {
    public FriendPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // 친구 목록
        JPanel friendListPanel = new JPanel();
        friendListPanel.setBackground(Colors.BGROUND);
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
                
	        // 내 프로필 패널
	        JButton myProfilePanel = new JButton();
	        myProfilePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
	        myProfilePanel.setBackground(Colors.BGROUND);
	        myProfilePanel.setLayout(new BoxLayout(myProfilePanel, BoxLayout.X_AXIS));
	        myProfilePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
	        
	        //내 프로필 이미지
	        ImageIcon profileImg = new ImageIcon("src/KokoaTalkImg/free_people.png");
	        JLabel imgLabel = new JLabel(new ImageIcon(profileImg.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
	        myProfilePanel.add(imgLabel);
	
	        //내 이름/상태메시지
	        JPanel infoPanel = new JPanel();
	        infoPanel.setBackground(Colors.BGROUND);
	        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	        JLabel nameLabel = new JLabel("이차원");
	        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
	        JLabel statusLabel = new JLabel("상태메시지");
	        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
	        infoPanel.add(nameLabel);
	        infoPanel.add(statusLabel);

	        myProfilePanel.add(Box.createRigidArea(new Dimension(10,0)));
	        myProfilePanel.add(infoPanel);

	        friendListPanel.add(myProfilePanel);
	        
	        // 즐겨찾기	        
	        JLabel Favorites = new JLabel("즐겨찾기");
	        Favorites.setBackground(Colors.BGROUND);
	        Favorites.setOpaque(true);
	        Favorites.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
	        Favorites.setForeground(Colors.TEXT);
	        Favorites.setFont(new Font("SansSerif", Font.BOLD, 15));
	        Favorites.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	        friendListPanel.add(Favorites);
	        
	        for(int i=0; i<5; i++) {
	            JButton friendItem = new JButton();
	            friendItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
	            friendItem.setBackground(Colors.BGROUND);
	            friendItem.setLayout(new BoxLayout(friendItem, BoxLayout.X_AXIS));
	            friendItem.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
	
	            // 프로필 사진
	            JLabel friendImg = new JLabel(new ImageIcon(profileImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
	            friendItem.add(friendImg);
	            // 이름/상태메시지
	            JPanel info = new JPanel();
	            info.setBackground(Colors.BGROUND);
	            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		            JLabel fname = new JLabel("친구 " + (i+1));
		            fname.setFont(new Font("SansSerif", Font.BOLD, 14));
		            JLabel fstatus = new JLabel("상태메시지");
		            fstatus.setFont(new Font("SansSerif", Font.PLAIN, 10));
		            info.add(fname);
		            info.add(fstatus);
		            
	            friendItem.add(Box.createRigidArea(new Dimension(10,0)));
	            friendItem.add(info);
	            friendItem.setAlignmentX(Component.LEFT_ALIGNMENT);
	            friendListPanel.add(friendItem);
	        }
	       
	        // 친구       
	        JLabel friends = new JLabel("친구");
	        friends.setBackground(Colors.BGROUND);
	        friends.setOpaque(true);
	        friends.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
	        friends.setForeground(Colors.TEXT);
	        friends.setFont(new Font("SansSerif", Font.BOLD, 15));
	        friends.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	        friendListPanel.add(friends);
	        
	        for(int i=0; i<5; i++) {
	            JButton friendItem2 = new JButton();
	            friendItem2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
	            friendItem2.setBackground(Colors.BGROUND);
	            friendItem2.setLayout(new BoxLayout(friendItem2, BoxLayout.X_AXIS));
	            friendItem2.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
	
	            // 프로필 사진
	            JLabel friendImg = new JLabel(new ImageIcon(profileImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
	            friendItem2.add(friendImg);
	            // 이름/상태메시지
	            JPanel info = new JPanel();
	            info.setBackground(Colors.BGROUND);
	            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		            JLabel fname = new JLabel("친구 " + (i+1));
		            fname.setFont(new Font("SansSerif", Font.BOLD, 14));
		            JLabel fstatus = new JLabel("상태메시지");
		            fstatus.setFont(new Font("SansSerif", Font.PLAIN, 10));
		            info.add(fname);
		            info.add(fstatus);
		            
		            friendItem2.add(Box.createRigidArea(new Dimension(10,0)));
		            friendItem2.add(info);
		            friendItem2.setAlignmentX(Component.LEFT_ALIGNMENT);
	            friendListPanel.add(friendItem2);
	        }
	        
	        
	        
	        

        JScrollPane scrollPanel = new JScrollPane(friendListPanel);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);

        // 레이아웃 배치
        add(scrollPanel, BorderLayout.CENTER);
    }
}
