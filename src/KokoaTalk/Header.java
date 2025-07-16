
// 카드마다 헤더 안의 버튼 기능들이 달라야 하는데....//검색기능은 같고, +버튼을 친구목록에서 누르면 친구추가기능, 채팅목록에서 누르면 채팅추가기능으로 만들어야 된다.
// 흠... 친구목록을 없애고 그냥 서버 접속인원으로 표시할까?
// 모든 채팅을 텍스트 파일로 서버컴에 저장해놓고, 사용자가 접속할때 보내면 됨. 사용자는 받은 톡은 다 저장하게 하고. (파일처리) //이렇게 하면 껐다켜도 대화내용은 전부 남아있음.
// 내 프로필을 누르면 이름변경, 상태메시지 변경, 프로필 사진 변경.
// 친구 프로필을 누르면 나오는 레이아웃에는 친구삭제버튼, 즐겨찾기버튼, 별명 변경, 일대일 채팅.
// 서버에 내 이름 파일을 저장. 상대방은 그걸 불러옴. //상태메시지도 같은 위치.
// 추가한 친구목록, 채팅, 등등을 다 서버에 저장하고, 클라이언트가 서버에 접속할 떄마다 친구목록을 보내주는 방식.
// 한 마디로, 클라이언트는 서버에 요청만 함. 서버는 그 요청을 받고 보내는 식. 서버에 메서드를 미리 만들어야 하나?
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
        searchBtn.setBackground(Colors.BGROUND);
        searchBtn.setBorderPainted(false);      // 버튼 테두리 제거
        searchBtn.setFocusPainted(false);       // 포커스 표시 제거
        panel.add(searchBtn);

        // 플러스 버튼
        ImageIcon imgPlusOrigin = new ImageIcon("src/KokoaTalkImg/add.png");
        Image scaledImagePlus = imgPlusOrigin.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon imgPlus = new ImageIcon(scaledImagePlus);

        JButton plusBtn = new JButton(imgPlus);
        plusBtn.setPreferredSize(new Dimension(30, 30));
        plusBtn.setBackground(Colors.BGROUND);
        plusBtn.setBorderPainted(false);      // 버튼 테두리 제거
        plusBtn.setFocusPainted(false);       // 포커스 표시 제거
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
