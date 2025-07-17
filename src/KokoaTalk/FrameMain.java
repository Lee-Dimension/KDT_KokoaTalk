package KokoaTalk;

import java.awt.*;
import javax.swing.*;
import KokoaTalk.ChatList.ChatListPanel;

import KokoaTalk.Profile.FriendPanel;

public class FrameMain extends JFrame{
	private CardLayout cardLayout;
	private JPanel cardPanel;
	
    public static void main(String[] args) {
    	
    	//메인 프레임
        JFrame f = new JFrame("코코아톡");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400,800);
        f.setMinimumSize(new Dimension(400,800));
        
        	//그리드백 패널(1단 레이아웃)
        	JPanel screenPanel = new JPanel(new GridBagLayout());
        	GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.BOTH;
        	c.gridx = 0;
        	c.weightx = 1.0;
	        
		        //박스 패널의 첫 번째 박스(헤더)
        		Header header = new Header("친구");         	
		        c.gridy   = 0;
		        c.weighty = 0.1;
		        screenPanel.add(header.getPanel(), c);
		        	        
		        //박스 패널의 두 번째 박스(메인화면, 카드)
		        JPanel mainPanel = new JPanel(new CardLayout());
		        mainPanel.setBackground(Color.LIGHT_GRAY);
		        
			        // 각 화면별 패널 객체 생성
			        mainPanel.add(new FriendPanel(),   "FRIEND");
			        mainPanel.add(new ChatListPanel(), "CHAT");
			    
			    c.gridy   = 1;
			    c.weighty = 9.8;
			    screenPanel.add(mainPanel,c);
		       		    
		        //박스 패널의 세 번째 박스(버튼)
		        JPanel btnPanel = new JPanel(new GridLayout(1,2,5,5));
		        btnPanel.setBackground(Colors.BGROUNDDEPP);
		        
		        	//이미지 스케일링
		        	// 원본 이미지 로드
	        		ImageIcon imgFriendOrigin = new ImageIcon("src/KokoaTalkImg/account_circle.png"); 
		        	// 이미지 객체 획득
		        	Image originalImage = imgFriendOrigin.getImage();
		        	// 스케일링된 Image 생성 (너비, 높이, 부드러운 스케일링)
		        	Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		        	// 스케일된 Image로 새로운 ImageIcon 생성
		        	ImageIcon imgFriend = new ImageIcon(scaledImage);
		        	
			        JButton btnFriend = new JButton(imgFriend);
			        btnFriend.setBackground(Colors.BGROUNDDEPP);
			        btnFriend.setBorderPainted(false);      // 버튼 테두리 제거
			        btnFriend.setFocusPainted(false);       // 포커스 표시 제거
			        btnPanel.add(btnFriend);
			        
			        //이미지 스케일링
		        	// 원본 이미지 로드
	        		ImageIcon imgChatOrigin = new ImageIcon("src/KokoaTalkImg/chat_bubble.png"); 
		        	// 이미지 객체 획득
		        	Image originalImage2 = imgChatOrigin.getImage();
		        	// 스케일링된 Image 생성 (너비, 높이, 부드러운 스케일링)
		        	Image scaledImage2 = originalImage2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		        	// 스케일된 Image로 새로운 ImageIcon 생성
		        	ImageIcon imgChat = new ImageIcon(scaledImage2);

			        JButton btnChat = new JButton(imgChat);
			        btnChat.setBackground(Colors.BGROUNDDEPP);
			        btnChat.setBorderPainted(false);      // 버튼 테두리 제거
			        btnChat.setFocusPainted(false);       // 포커스 표시 제거
			        btnPanel.add(btnChat);
	
		        c.gridy   = 2;
		        c.weighty = 0.1;
		        screenPanel.add(btnPanel, c);
		        
		        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
		        
		        btnFriend.addActionListener(e -> {
		            cardLayout.show(mainPanel, "FRIEND");
		            header.setHeaderText("친구");
		        });
		        btnChat.addActionListener(e -> {
		        	cardLayout.show(mainPanel, "CHAT");
		            header.setHeaderText("채팅");
		        });
		       
	        f.getContentPane().add(screenPanel);		          
        f.setVisible(true);
    }
}
