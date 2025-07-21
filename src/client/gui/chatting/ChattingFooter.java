package client.gui.chatting;

import java.awt.*;
import javax.swing.*;

public class ChattingFooter extends JPanel {
    private JTextField inputText;
    private JButton sendBtn;

    public ChattingFooter() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(400, 50));

        // 텍스트 앞 빈 공간
        JLabel emptySpace = new JLabel();
        emptySpace.setBackground(Color.WHITE);
        emptySpace.setPreferredSize(new Dimension(10, 50));
        emptySpace.setBorder(null);

        // 필드에 직접 할당
        inputText = new JTextField();
        inputText.setBackground(Color.WHITE);
        inputText.setPreferredSize(new Dimension(350, 50));
        inputText.setBorder(null);

//        // 엔터 키 눌렀을 때 이벤트 처리
//        inputText.addActionListener(e -> {
//            String message = inputText.getText().trim();
//            if (!message.isEmpty()) {
//                // TODO: 메시지 보내는 로직 호출
//                System.out.println("Send message: " + message);//테스트용. 나중에 대체하기
//                inputText.setText("");
//            }
//        });

        // 이모티콘 버튼
        JButton emoticon = new JButton();
        ImageIcon emoImage = new ImageIcon("src/data/icon/sticker.png");
        Image emoImageScaled = emoImage.getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH);
        emoticon.setIcon(new ImageIcon(emoImageScaled));
        emoticon.setPreferredSize(new Dimension(40, 50));
        emoticon.setBorderPainted(false);
        emoticon.setContentAreaFilled(false);
        emoticon.setFocusPainted(false);

        add(emptySpace);
        add(inputText);
        add(emoticon);
    }

    public JTextField getInputText() {
        return inputText;
    }
}
