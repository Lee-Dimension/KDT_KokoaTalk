package client.gui.chatting;
// 채팅창 헤더
import java.awt.*;
import javax.swing.*;

import client.gui.style.Colors;

public class ChattingHeader extends JPanel {

	/*----------------------필드----------------------*/
    private JLabel titleLabel;
    private JButton chatSettingBtn;

	/*---------------------생성자---------------------*/
    public ChattingHeader(String chattingTitle) {
        setBackground(Colors.BGROUND);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(400, 70));
		//뒤로가기 버튼
		JButton back = new JButton();
		ImageIcon backImage = new ImageIcon("src/data/icon/back.png");
		Image backImageScaled = backImage.getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH);
		back.setIcon(new ImageIcon(backImageScaled));
		back.setPreferredSize(new Dimension(40, 100));
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		
        // 타이틀
        titleLabel = new JLabel(chattingTitle);
        titleLabel.setForeground(Colors.TEXT);
        titleLabel.setPreferredSize(new Dimension(320, 100));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        // 채팅방 설정 버튼
        chatSettingBtn = new JButton();
        ImageIcon chatSettingImage = new ImageIcon("src/data/icon/list.png");
        Image chatSettingImageScaled = chatSettingImage.getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH);
        chatSettingBtn.setIcon(new ImageIcon(chatSettingImageScaled));
        chatSettingBtn.setPreferredSize(new Dimension(40, 100));
        chatSettingBtn.setBorderPainted(false);
        chatSettingBtn.setContentAreaFilled(false);
        chatSettingBtn.setFocusPainted(false);

		add(back);
        add(titleLabel);
        add(chatSettingBtn);
    }
    
	/*---------------------메서드---------------------*/
    public JButton getChatSettingButton() { return chatSettingBtn; }
    public void setTitle(String title) { titleLabel.setText(title); }
}
