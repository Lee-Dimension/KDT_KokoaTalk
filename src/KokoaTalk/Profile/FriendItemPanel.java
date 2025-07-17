package KokoaTalk.Profile;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class FriendItemPanel extends JPanel {
    public JPanel detailPanel;

    public FriendItemPanel(String name, String status, ImageIcon icon, boolean hasDetailPanel) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Colors.BGROUND);
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel friendImg = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        add(friendImg);

        add(Box.createRigidArea(new Dimension(10,0)));

        JPanel info = new JPanel();
        info.setBackground(Colors.BGROUND);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel fname = new JLabel(name);
        fname.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel fstatus = new JLabel(status);
        fstatus.setFont(new Font("SansSerif", Font.PLAIN, 10));
        info.add(fname);
        info.add(fstatus);

        add(info);

        if (hasDetailPanel) {
            detailPanel = new JPanel();
            detailPanel.setVisible(false);
            detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    detailPanel.setVisible(!detailPanel.isVisible());
                    revalidate();
                    repaint();
                }
            });
        }
    }

    public JPanel getDetailPanel() {
        return detailPanel;
    }
}
