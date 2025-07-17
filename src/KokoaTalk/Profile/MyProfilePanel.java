package KokoaTalk.Profile;

import javax.swing.*;
import java.awt.*;
import KokoaTalk.Colors;

public class MyProfilePanel extends JPanel {
    private JPanel  detailPanel;

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
        detailPanel.setVisible(false);
        detailPanel.setMaximumSize(new Dimension(380, 60));
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        detailPanel.setBackground(Colors.BGROUNDDEEP); 
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(Box.createHorizontalGlue());
	    JButton talkBtn = new JButton("대화하기");
		    HideBtnDesign.apply(talkBtn);
		    talkBtn.addActionListener(e -> {
		        // 버튼별 기능 분기
		    });
		    detailPanel.add(talkBtn);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
	    JButton nickChange = new JButton("별명변경");
		    HideBtnDesign.apply(nickChange);
		    nickChange.addActionListener(e -> {
		        // 버튼별 기능 분기
		    });
		    detailPanel.add(nickChange);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
	    JButton delFf = new JButton("즐찾제거");
		    HideBtnDesign.apply(delFf);
		    delFf.addActionListener(e -> {
		        // 버튼별 기능 분기
		    });
		    detailPanel.add(delFf);;
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
