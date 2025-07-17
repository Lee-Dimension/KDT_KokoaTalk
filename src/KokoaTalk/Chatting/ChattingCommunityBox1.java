package KokoaTalk.Chatting;

import javax.swing.*;
import java.awt.*;

public class ChattingCommunityBox1 extends JPanel {

    public ChattingCommunityBox1() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(220,214,247));

        add(new TextByOthers("친구1", "안녕~ 잘 지냈어?"));
        add(new TextByMe("응응 잘 지냈지~"));
        add(new TextByOthers("친구1", "이번 주에 영화 볼래?"));
        add(new TextByMe("좋아! 무슨 영화?"));
        add(new TextByMe("대답안해??? 무슨 영화보자니까??"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(new TextByOthers("친구2", "미안미안 ㅋㅋㅋㅋ"));
        add(Box.createVerticalGlue());
        
        
    }
    
    
    
    private JPanel wrapLeft(Component comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }
    
    @Override
    public Component add(Component comp) {
        // TextByOthers일 경우만 wrapLeft로 감싸서 추가
        if (comp instanceof TextByOthers) {
            return super.add(wrapLeft(comp));
        } else {
            return super.add(comp);
        }
    }
    
    
}
