package KokoaTalk.Chatting;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class TextByMe extends JPanel {

    public TextByMe(String text) {
    	
    	//텍스트 바로 앞 부분에 빈 공간 세팅
    	JLabel emptySpace = new JLabel();
    	emptySpace.setBackground(Color.WHITE);
    	emptySpace.setPreferredSize(new Dimension(30, 40));
    	emptySpace.setBorder(null);
    	
    	setPreferredSize(new Dimension(400, 40));
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setOpaque(false); //JPanel 배경 제거
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JLabel textLabel = new JLabel(text);
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.WHITE);
        textLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        add(textLabel);
        add(emptySpace);
    }

    @Override
    public Dimension getMaximumSize() {
        // preferred size 기반으로 최대 크기 제한: 너비는 JLabel 크기 + 패딩 만큼만
        Dimension pref = getPreferredSize();
        return new Dimension(pref.width, pref.height);
    }
}

