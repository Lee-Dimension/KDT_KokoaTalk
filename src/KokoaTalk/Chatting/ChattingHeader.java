package KokoaTalk.Chatting;

import java.awt.*;
import javax.swing.*;

public class ChattingHeader extends JPanel { 
	
	private String chatTitle; //채팅방 제목
	
	public ChattingHeader(String chattingTitle) {
		this.chatTitle = chattingTitle;
		
		setBackground(new Color(220, 214, 247));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(400, 70));

		//뒤로가기 버튼
		JButton back = new JButton();
		ImageIcon backImage = new ImageIcon("src/KokoaTalkImg/back.png");
		Image backImageScaled = backImage.getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH);
		back.setIcon(new ImageIcon(backImageScaled));
		back.setPreferredSize(new Dimension(40, 100));
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		
		//타이틀
		JLabel title = new JLabel(this.chatTitle);
		title.setForeground(new Color(140, 120, 192));
		title.setPreferredSize(new Dimension(320, 100));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		
		//채팅방 설정 버튼
		JButton chatSetting = new JButton();
		ImageIcon chatSettingImage = new ImageIcon("src/KokoaTalkImg/list.png");
		Image chatSettingImageScaled = chatSettingImage.getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH);
		chatSetting.setIcon(new ImageIcon(chatSettingImageScaled));
		chatSetting.setPreferredSize(new Dimension(40, 100));
		chatSetting.setBorderPainted(false);
		chatSetting.setContentAreaFilled(false);
		chatSetting.setFocusPainted(false);
		
		add(back);
		add(title);
		add(chatSetting);
	}
}
