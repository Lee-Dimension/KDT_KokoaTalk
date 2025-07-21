package client.gui.chatting;
// 상대가 보낸 채팅. 왼쪽 정렬된 말풍선.
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
		ImageIcon faceImage = new ImageIcon("src/data/profile_img/default.jpg");
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
		JLabel textLabel = new JLabel("<html>" + text + "</html>"); //자동 줄바꿈 되게끔.
		textLabel.setOpaque(true);
		textLabel.setBackground(Color.WHITE);
//		textLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
		textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		textLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        textLabel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE)); //메시지가 길어서 침범하지 않게
		
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
