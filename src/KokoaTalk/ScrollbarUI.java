package KokoaTalk;

import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollbarUI extends BasicScrollBarUI {
    private static final Color THUMB_COLOR = Colors.TEXT;  // 막대 색

    @Override
    protected void configureScrollBarColors() {
        thumbColor = THUMB_COLOR;
        trackColor = Colors.BGROUND; // 트랙 배경색
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    // 위아래 버튼 제거
    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (!scrollbar.isEnabled() || thumbBounds.width > thumbBounds.height) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(THUMB_COLOR);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);

        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(220,214,247)); // 트랙 색
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }
    
    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(10, 20); // 최소 사이즈 설정 (width, height)
    }
    
    @Override
    protected Dimension getMaximumThumbSize() {
        return new Dimension(10, 120); // 최대 사이즈 설정 (width, height)
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(10, super.getPreferredSize(c).height); // ← 폭(두께)2
        } else {
            return new Dimension(super.getPreferredSize(c).width, 8); // ← 수평 스크롤바 높이
        }
    }

    
}