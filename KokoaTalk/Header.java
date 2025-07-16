package KokoaTalk;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Header {
    private JPanel panel;
    private JLabel label;

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
        searchBtn.setBorderPainted(false);
        searchBtn.setContentAreaFilled(false);
        panel.add(searchBtn);

        // 플러스 버튼
        ImageIcon imgPlusOrigin = new ImageIcon("src/KokoaTalkImg/add.png");
        Image scaledImagePlus = imgPlusOrigin.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon imgPlus = new ImageIcon(scaledImagePlus);

        JButton plusBtn = new JButton(imgPlus);
        plusBtn.setPreferredSize(new Dimension(30, 30));
        plusBtn.setBorderPainted(false);
        plusBtn.setContentAreaFilled(false);
        panel.add(plusBtn);

        JLabel spaceLabel = new JLabel("    ");
        spaceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(spaceLabel);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setHeaderText(String text) {
        label.setText("  " + text);
    }
}
