package client.gui.friendlist;
//FriendListPanel에 뜰 친구 한 명의 UI패널
import javax.swing.*;

import client.gui.style.Colors;

import java.awt.*;

public class FriendItemPanel extends JPanel {
    public JPanel detailPanel;
    private String name;
    private String status;
    private final static int IMG_SIZE = 40;

    public FriendItemPanel(String name, String status, String id) {
        this.name = name;
        this.status = status;
        // 패널 기본 세팅
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Colors.BGROUND); // 배경색 통일
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        setAlignmentX(Component.LEFT_ALIGNMENT); // 왼쪽 정렬

        // --- 이미지 불러오기 ---
        String imagePath = "src/data/profile_img/"+id+".jpg";
        ImageIcon profileImg = new ImageIcon(imagePath);

        if (profileImg.getIconWidth() == -1) { //이미지가 없으면 기본 이미지로.
            profileImg = new ImageIcon("src/data/profile_img/default.jpg");
        }

        Image scaledImage = profileImg.getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel friendImg = new JLabel(scaledIcon);
        friendImg.setAlignmentY(Component.CENTER_ALIGNMENT); // 세로 중앙정렬(옵션)
        add(friendImg);

        add(Box.createRigidArea(new Dimension(10, 0)));

        // 친구정보 패널
        JPanel info = new JPanel();
        info.setBackground(Colors.BGROUND); // 배경색 통일
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setAlignmentY(Component.CENTER_ALIGNMENT); // 세로 중앙정렬

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
        detailPanel.setBackground(Colors.BGROUND);
        
        add(detailPanel);
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
