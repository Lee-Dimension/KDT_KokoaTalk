package client.gui;
// 메인프레임의 헤더 디자인.
import java.awt.*;
import javax.swing.*;

import client.gui.style.Colors;

public class Header {
	/*--------------------헤더----------------------*/
    private static final String IMG_SEARCH = "src/data/icon/search.png";
    private static final String IMG_PLUS = "src/data/icon/add.png";
    private static final int HEADER_BTN_SIZE = 30;

    private JPanel panel;
    private JLabel label;
    private JButton searchBtn, plusBtn;
    
	/*--------------------생성자----------------------*/
    public Header(String text) {
        panel = new JPanel();
        panel.setBackground(Colors.BGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        label = new JLabel("  " + text);
        label.setFont(new Font("SansSerif", Font.BOLD, 30));
        label.setForeground(Colors.TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        panel.add(Box.createHorizontalGlue());

        // 검색 버튼
        searchBtn = createIconButton(IMG_SEARCH, HEADER_BTN_SIZE);
        panel.add(searchBtn);

        // 플러스 버튼
        plusBtn = createIconButton(IMG_PLUS, HEADER_BTN_SIZE);
        panel.add(plusBtn);

        JLabel spaceLabel = new JLabel("    ");
        spaceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(spaceLabel);
    }
    
	/*--------------------메서드----------------------*/
    //이미지 버튼 만드는 메서드
    private JButton createIconButton(String imagePath, int size) {
        ImageIcon imgOrigin = new ImageIcon(imagePath);
        Image scaled = imgOrigin.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(scaled);
        JButton btn = new JButton(img);
        btn.setPreferredSize(new Dimension(size, size));
        btn.setBackground(Colors.BGROUND);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }
    public JButton getPlusButton() {
        return plusBtn;
    }
    public JButton getSearchButton() {
        return searchBtn;
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setHeaderText(String text) {
        label.setText("  " + text);
    }
}
