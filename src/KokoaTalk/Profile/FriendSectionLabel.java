package KokoaTalk.Profile;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class FriendSectionLabel extends JLabel {
    public FriendSectionLabel(String text) {
        super(text);
        setBackground(Colors.BGROUND);
        setOpaque(true);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        setForeground(Colors.TEXT);
        setFont(new Font("SansSerif", Font.BOLD, 15));
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
