package KokoaTalk;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import KokoaTalk.ChatList.ChatListPanel;

import KokoaTalk.Profile.FriendPanel;

public class FrameMain extends JFrame{
	private CardLayout cardLayout;
	private JPanel cardPanel;
	
	public FrameMain(String id) {
		
		//메인 프레임
        JFrame f = new JFrame("코코아톡");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400,800);
        f.setMinimumSize(new Dimension(400,800));
        f.setLocationRelativeTo(null);
        
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
			        mainPanel.add(new FriendPanel(id),   "FRIEND");
			        mainPanel.add(new ChatListPanel(), "CHAT");
			    
			    c.gridy   = 1;
			    c.weighty = 9.8;
			    screenPanel.add(mainPanel,c);
		       		    
		        //박스 패널의 세 번째 박스(버튼)
		        JPanel btnPanel = new JPanel(new GridLayout(1,2,5,5));
		        btnPanel.setBackground(Colors.BGROUNDDEEP);
		        
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
			        btnFriend.setBackground(Colors.BGROUNDDEEP);
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
			        btnChat.setBackground(Colors.BGROUNDDEEP);
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
		            JButton plusBtn = header.getPlusButton();
		            
		            // 기존 리스너 모두 제거 (중복 방지)
		            for (ActionListener al : plusBtn.getActionListeners()) {
		                plusBtn.removeActionListener(al);
		            }
		            
		            plusBtn.addActionListener(e2 -> {
		                String friendId = JOptionPane.showInputDialog(f, "추가할 친구의 ID를 입력하세요:");

		                if (friendId != null && !friendId.trim().isEmpty()) {
		                    friendId = friendId.trim();
		                    String filePath = "src/FriendList/" + id + ".txt";

		                    try {
		                        File file = new File(filePath);

		                        // 파일이 존재하지 않으면 새로 생성
		                        if (!file.exists()) {
		                            file.createNewFile();
		                        }

		                        // 친구목록 모두 읽어오기
		                        boolean alreadyExists = false;
		                        List<String> lines = new ArrayList<>();
		                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		                            String line;
		                            while ((line = br.readLine()) != null) {
		                                String[] tokens = line.split(",");
		                                if (tokens.length > 0 && tokens[0].trim().equals(friendId)) {
		                                    alreadyExists = true;
		                                }
		                                lines.add(line);
		                            }
		                        }

		                        if (alreadyExists) {
		                            JOptionPane.showMessageDialog(f, "이미 친구로 추가된 ID입니다.");
		                            return;
		                        }

		                        // 친구 .ser 파일 존재 확인 (없는 아이디 추가 방지)
		                        File friendSerFile = new File("src/UserList/" + friendId + ".ser");
		                        if (!friendSerFile.exists()) {
		                            JOptionPane.showMessageDialog(f, "존재하지 않는 아이디입니다.");
		                            return;
		                        }

		                        // 파일에 새 친구 추가 (기본은 FALSE: 일반 친구)
		                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
		                            if (lines.size() > 0) {
		                                bw.newLine(); // 기존 데이터가 있으면 줄바꿈
		                            }
		                            bw.write(friendId + ",FALSE");
		                        }

		                        JOptionPane.showMessageDialog(f, friendId + "님이 친구로 추가되었습니다.");
		                        // 기존 mainPanel에서 FRIEND 패널을 제거하고 새로 추가
		                        mainPanel.remove(mainPanel.getComponent(0)); // 또는 remove("FRIEND")
		                        mainPanel.add(new FriendPanel(id), "FRIEND");
		                        mainPanel.revalidate();
		                        mainPanel.repaint();

		                        // 그리고 친구 화면으로 다시 전환 (카드레이아웃인 경우)
		                        cardLayout.show(mainPanel, "FRIEND");
		                        		
		                    } catch (IOException ex) {
		                        ex.printStackTrace();
		                        JOptionPane.showMessageDialog(f, "파일 처리 중 오류 발생.");
		                    }
		                }
		            });

		        });
		            
		        btnChat.addActionListener(e -> {
		        	cardLayout.show(mainPanel, "CHAT");
		            header.setHeaderText("채팅");
		        });
		       
	        f.getContentPane().add(screenPanel);		          
        f.setVisible(true);
        btnFriend.doClick();
	}
	
    public static void main(String[] args) {
    	
    }
}
