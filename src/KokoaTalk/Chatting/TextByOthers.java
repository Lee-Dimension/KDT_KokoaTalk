package KokoaTalk.Chatting;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TextByOthers extends JPanel {

	public TextByOthers(String name, String text) {
		setLayout(new FlowLayout(FlowLayout.LEFT)); // 상대 채팅은 왼쪽 정렬
		//setBackground(Color.LIGHT_GRAY); //구분을 위해 배경 임시 지정.
		setOpaque(false); //JPanel 배경 제거
		
		// 프로필 사진
		JButton face = new JButton();
		face.setContentAreaFilled(false);
		ImageIcon faceImage = new ImageIcon("src/KokoaTalkImg/face.jpg");
		Image backImageScaled = faceImage.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		face.setIcon(new ImageIcon(backImageScaled));
		face.setPreferredSize(new Dimension(28, 28));
		face.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		face.setMargin(new Insets(0, 0, 0, 0)); //이미지 공백 제거
		
		
		// 이름 및 텍스트 패널
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		textPanel.setOpaque(false);
		
		//이름
		JLabel nameLabel = new JLabel(name);
		nameLabel.setForeground(Color.GRAY);
		nameLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//텍스트 대화내용
		JLabel textLabel = new JLabel(text);
		textLabel.setOpaque(true);
		textLabel.setBackground(Color.WHITE);
		textLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
		textLabel.setPreferredSize(new Dimension(300, 30));
		textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		textLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		//이름 및 텍스트 패널에 추가
		textPanel.add(nameLabel);
		textPanel.add(textLabel);
		
		add(Box.createRigidArea(new Dimension(3, 0)));
		add(face);
		add(Box.createRigidArea(new Dimension(3, 0)));
		add(textPanel);
	}
	
	@Override
	public Dimension getMaximumSize() {
	    return getPreferredSize();
	}

}
