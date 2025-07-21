package client.gui.chatting;
// 내가 보낸 채팅. 오른쪽 정렬된 말풍선
import java.awt.*;
import javax.swing.*;

public class TextByMe extends JPanel {

    public TextByMe(String text) {
    	
    	//텍스트 바로 앞 부분에 빈 공간 세팅
    	JLabel emptySpace = new JLabel();
    	emptySpace.setBackground(Color.WHITE);
    	emptySpace.setPreferredSize(new Dimension(30, 40));
    	emptySpace.setBorder(null);
    	
    	setPreferredSize(new Dimension(400, 40));
        setLayout(new FlowLayout(FlowLayout.RIGHT)); //말풍선 오른쪽으로 정렬
        setOpaque(false); //JPanel 배경 제거
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		JLabel textLabel = new JLabel("<html>" + text + "</html>"); //자동 줄바꿈 되게끔.
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.WHITE);
        textLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        textLabel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE)); //메시지가 길어서 침범하지 않게
        add(textLabel);
        add(emptySpace); //말풍선이 화면 끝에 붙지 않도록 조정
    }

    @Override
    public Dimension getMaximumSize() {
        // preferred size 기반으로 최대 크기 제한: 너비는 JLabel 크기 + 패딩 만큼만
        Dimension pref = getPreferredSize();
        return new Dimension(pref.width, pref.height);
    }
}

