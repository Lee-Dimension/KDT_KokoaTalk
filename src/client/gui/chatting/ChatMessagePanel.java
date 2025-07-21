package client.gui.chatting;
// 채팅창 내부 패널
import javax.swing.*;
import java.awt.*;
import client.gui.style.Colors;

public class ChatMessagePanel extends JPanel {

    private Component glue = Box.createVerticalGlue();
    private String myUserId;  // 내가 보낸 메시지인지 판단하기 위해 필요함

    public ChatMessagePanel(String myUserId) {
        this.myUserId = myUserId;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BGROUND);
        add(glue); // 최초에 glue 추가
    }

    private JPanel wrapLeft(Component comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }
    private JPanel wrapRight(Component comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }

    @Override
    public Component add(Component comp) {
        if (glue.getParent() == this) {
            remove(glue);  // 이전 glue 제거
        }

        Component added;
        if (comp instanceof TextByOthers) {
            added = super.add(wrapLeft(comp));
        } else {
            added = super.add(wrapRight(comp));
        }

        super.add(glue);  // glue를 항상 맨 아래로 유지
        revalidate();
        repaint();
        return added;
    }

    // 메시지를 추가하는 메서드
    public void addMessage(String fromUserId, String displayName, String msg) {
        if (fromUserId.equals(myUserId)) {
            add(new TextByMe(msg));              // 내 메시지 → 오른쪽
        } else {
            add(new TextByOthers(displayName, msg));  // 상대 메시지 → 왼쪽
        }
    }
}
