package KokoaTalk.Chatting;

import javax.swing.*;
import java.awt.*;

import KokoaTalk.ScrollbarUI;

public class ChattingMainPanel extends JFrame {

	private String name;
	
    public ChattingMainPanel(String name) {
        setTitle(name);
        setSize(400, 800); //frame 사이즈
        setBackground(new Color(220,214,247)); //배경색 지정
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //끄면 종료.
        setLocationRelativeTo(null); 
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        mainPanel.add(new ChattingHeader(name), BorderLayout.NORTH);
        mainPanel.add(new ChattingFooter(), BorderLayout.SOUTH);
        
        ChattingCommunityBox1 communityBox = new ChattingCommunityBox1();
        
        //스크롤바
        JScrollPane scrollPane = new JScrollPane(communityBox);
        scrollPane.setBorder(null);
        
        //스크롤바 디자인 변경
        scrollPane.getVerticalScrollBar().setUI(new ScrollbarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollbarUI());
        
        //스크롤바 삭제 코드 아래 2줄
        //scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); //
        //scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0)); //
        
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel); //
        setVisible(true);
        
        //스크롤바 가장 아래로 이동
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
        
    }

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new ChattingMainPanel("UserName"));
    }
}
