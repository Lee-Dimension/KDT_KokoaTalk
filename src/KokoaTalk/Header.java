
package KokoaTalk;

import java.awt.*;
import javax.swing.*;

public class Header {
    private JPanel panel;
    private JLabel label;
    private JButton plusBtn;
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
        ImageIcon imgSearchOrigin = new ImageIcon("src/KokoaTalkImg/search.png");
        Image scaledImageSearch = imgSearchOrigin.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon imgSearch = new ImageIcon(scaledImageSearch);

        JButton searchBtn = new JButton(imgSearch);
        searchBtn.setPreferredSize(new Dimension(30, 30));
        searchBtn.setBackground(Colors.BGROUND);
        searchBtn.setBorderPainted(false);      // 버튼 테두리 제거
        searchBtn.setFocusPainted(false);       // 포커스 표시 제거
        panel.add(searchBtn);

        // 플러스 버튼
        ImageIcon imgPlusOrigin = new ImageIcon("src/KokoaTalkImg/add.png");
        Image scaledImagePlus = imgPlusOrigin.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon imgPlus = new ImageIcon(scaledImagePlus);

        plusBtn = new JButton(imgPlus);
        plusBtn.setPreferredSize(new Dimension(30, 30));
        plusBtn.setBackground(Colors.BGROUND);
        plusBtn.setBorderPainted(false);      // 버튼 테두리 제거
        plusBtn.setFocusPainted(false);       // 포커스 표시 제거
        panel.add(plusBtn);

        JLabel spaceLabel = new JLabel("    ");
        spaceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(spaceLabel);
    }
    
    
    public JButton getPlusButton() {
        return plusBtn;
    }

    public JPanel getPanel() {
        return panel;
    }
    
    public void setHeaderText(String text) {
        label.setText("  " + text);
    }
}
