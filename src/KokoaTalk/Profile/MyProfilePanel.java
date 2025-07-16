package KokoaTalk.Profile;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class MyProfilePanel extends JPanel {
    private JPanel detailPanel;

    public MyProfilePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Colors.BGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        ImageIcon profileImg = new ImageIcon("src/KokoaTalkImg/free_people.png");
        JLabel imgLabel = new JLabel(new ImageIcon(profileImg.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        add(imgLabel);

        add(Box.createRigidArea(new Dimension(10,0)));

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Colors.BGROUND);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("이차원");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel statusLabel = new JLabel("상태메시지");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        infoPanel.add(nameLabel);
        infoPanel.add(statusLabel);
        
        add(infoPanel);

        // Detail Panel
        detailPanel = new JPanel();
        detailPanel.add(new JLabel("여기에 각 버튼 추가"));
        detailPanel.setVisible(false);
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 클릭 이벤트 (여기서는 패널 전체에)
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detailPanel.setVisible(!detailPanel.isVisible());
                revalidate();
                repaint();
            }
        });
    }

    public JPanel getDetailPanel() {
        return detailPanel;
    }
}
