package KokoaTalk.Profile;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class FriendItemPanel extends JPanel {
    public JPanel detailPanel;
    private String name;
    private String status;

    public FriendItemPanel(String name, String status) {
        this.name = name;
        this.status = status;

        // 패널 기본 세팅
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Colors.BGROUND); // 배경색 통일
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        setAlignmentX(Component.LEFT_ALIGNMENT); // 왼쪽 정렬

        // --- 이미지 불러오기 ---
        String imagePath = "src/ProfileImage/"+this.name+".jpg";
        ImageIcon profileImg = new ImageIcon(imagePath);

        // 이미지가 없으면 기본 이미지로 fallback
        if (profileImg.getIconWidth() == -1) {
            profileImg = new ImageIcon("src/ProfileImage/default.jpg");
        }

        // 스케일링 (40x40)
        Image scaledImage = profileImg.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel friendImg = new JLabel(scaledIcon);
        friendImg.setAlignmentY(Component.CENTER_ALIGNMENT); // 세로 중앙정렬(옵션)
        add(friendImg);

        add(Box.createRigidArea(new Dimension(10, 0)));

        // info 패널
        JPanel info = new JPanel();
        info.setBackground(Colors.BGROUND); // 배경색 통일
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setAlignmentY(Component.CENTER_ALIGNMENT); // 세로 중앙정렬(옵션)

        JLabel fname = new JLabel(name);
        fname.setFont(new Font("SansSerif", Font.BOLD, 14));
        fname.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel fstatus = new JLabel(status);
        fstatus.setFont(new Font("SansSerif", Font.PLAIN, 10));
        fstatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(fname);
        info.add(fstatus);

        add(info);

        detailPanel = new JPanel();
        detailPanel.setVisible(false);
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.setBackground(Colors.BGROUND); // detailPanel도 배경 통일

        addMouseListener(new java.awt.event.MouseAdapter() {
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
