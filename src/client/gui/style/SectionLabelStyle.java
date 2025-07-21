package client.gui.style;
// 친구목록 화면에서 "즐겨찾기", "친구" 글자 디자인.
import javax.swing.*;
import java.awt.*;

public class SectionLabelStyle extends JLabel {
    public SectionLabelStyle(String text) {
        super(text);
        setBackground(Colors.BGROUND);
        setOpaque(true);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        setForeground(Colors.TEXT);
        setFont(new Font("SansSerif", Font.BOLD, 15));
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
