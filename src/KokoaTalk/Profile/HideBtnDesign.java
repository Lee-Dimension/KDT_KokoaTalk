package KokoaTalk.Profile;

import java.awt.*;
import javax.swing.*;
import KokoaTalk.Colors;

public class HideBtnDesign {
    public static void apply(JButton btn) {
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(85, 36));
        btn.setMaximumSize(new Dimension(85, 36));
        btn.setMinimumSize(new Dimension(85, 36));
        btn.setBackground(Colors.TEXT);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(3,18,3,18));
        btn.setBorder(BorderFactory.createLineBorder(Colors.ICON, 2, true));
    }
}
