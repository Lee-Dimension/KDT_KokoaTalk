package KokoaTalk.Chatting;

import java.awt.*;
import javax.swing.*;

public class ChattingFooter extends JPanel { // JFrame → JPanel으로 변경
	
	public ChattingFooter() {
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(400, 50));
		
		//텍스트 바로 앞 부분에 빈 공간 세팅
		JLabel emptySpace = new JLabel();
		emptySpace.setBackground(Color.WHITE);
		emptySpace.setPreferredSize(new Dimension(10, 50));
		emptySpace.setBorder(null);
		
		//채팅 텍스트 입력창
		JTextField inputText = new JTextField();
		inputText.setBackground(Color.WHITE);
		inputText.setPreferredSize(new Dimension(350, 50));
		inputText.setBorder(null);
		
		//이모티콘 버튼
		JButton emoticon = new JButton();
		ImageIcon emoImage = new ImageIcon("src/KokoaTalkImg/sticker.png");
		Image emoImageScaled = emoImage.getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH);
		emoticon.setIcon(new ImageIcon(emoImageScaled));
		emoticon.setPreferredSize(new Dimension(40, 50));
		emoticon.setBorderPainted(false);
		emoticon.setContentAreaFilled(false);
		emoticon.setFocusPainted(false);
		
		add(emptySpace);
		add(inputText);
		add(emoticon);
	}
}
