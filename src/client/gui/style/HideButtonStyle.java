package client.gui.style;
// 프로필 누르면 나오는 숨겨진 버튼들의 디자인.
import java.awt.*;
import javax.swing.*;

public class HideButtonStyle {
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
