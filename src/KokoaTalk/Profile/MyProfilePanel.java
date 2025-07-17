// FriendPanel에 들어가는 내 프로필 구역.
package KokoaTalk.Profile;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class MyProfilePanel extends JPanel {
    private JPanel  detailPanel;
    private String name;
    private String stateMessage;

    public MyProfilePanel(String name, String stateMessage) {
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
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel statusLabel = new JLabel(stateMessage);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        infoPanel.add(nameLabel);
        infoPanel.add(statusLabel);
        
        add(infoPanel);

        // Detail Panel
        detailPanel = new JPanel();
        detailPanel.setVisible(false);
        detailPanel.setMaximumSize(new Dimension(380, 60));
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        detailPanel.setBackground(Colors.BGROUNDDEEP); 
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(Box.createHorizontalGlue());
	    JButton talkBtn = new JButton("이름변경");
		    HideBtnDesign.apply(talkBtn);
		    talkBtn.addActionListener(e -> {
		        // 버튼별 기능 분기
		    });
		    detailPanel.add(talkBtn);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
	    JButton stateChange = new JButton("상태메시지");
		    HideBtnDesign.apply(stateChange);
		    stateChange.addActionListener(e -> {
		        // 버튼별 기능 분기
		    });
		    detailPanel.add(stateChange);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
	    JButton profile =  new JButton("프로필사진");
		    HideBtnDesign.apply(profile);
		    profile.addActionListener(e -> {
		        // 버튼별 기능 분기
		    });
		    detailPanel.add(profile);;
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));      

        detailPanel.add(Box.createHorizontalGlue());

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
