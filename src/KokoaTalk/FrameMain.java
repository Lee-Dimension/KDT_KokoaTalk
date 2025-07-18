package KokoaTalk;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;
import KokoaTalk.ChatList.ChatListPanel;
import KokoaTalk.Profile.FriendPanel;

public class FrameMain extends JFrame {
    private CardLayout cardLayout;   // 필드로 선언
    private JPanel mainPanel;         // 필드로 선언

    public FrameMain(String id) {
        System.out.println("[FrameMain] 생성, id=" + id);

        // 메인 프레임 생성
        JFrame f = new JFrame("코코아톡");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 800);
        f.setMinimumSize(new Dimension(400, 800));
        f.setLocationRelativeTo(null);

        // 그리드백 패널(1단 레이아웃)
        JPanel screenPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1.0;

        // 헤더 패널 생성
        Header header = new Header("친구");
        c.gridy = 0;
        c.weighty = 0.1;
        screenPanel.add(header.getPanel(), c);

        // 메인 화면(카드 레이아웃) 생성 - 필드에 할당!
        mainPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // 각 화면별 패널 추가
        mainPanel.add(new FriendPanel(id, mainPanel, cardLayout), "FRIEND");
        mainPanel.add(new ChatListPanel(), "CHAT");

        c.gridy = 1;
        c.weighty = 9.8;
        screenPanel.add(mainPanel, c);

        // 하단 버튼 패널 생성
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        btnPanel.setBackground(Colors.BGROUNDDEEP);

        // 친구 버튼 이미지 스케일링
        ImageIcon imgFriendOrigin = new ImageIcon("src/KokoaTalkImg/account_circle.png");
        Image originalImage = imgFriendOrigin.getImage();
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon imgFriend = new ImageIcon(scaledImage);

        JButton btnFriend = new JButton(imgFriend);
        btnFriend.setBackground(Colors.BGROUNDDEEP);
        btnFriend.setBorderPainted(false);
        btnFriend.setFocusPainted(false);
        btnPanel.add(btnFriend);

        // 채팅 버튼 이미지 스케일링
        ImageIcon imgChatOrigin = new ImageIcon("src/KokoaTalkImg/chat_bubble.png");
        Image originalImage2 = imgChatOrigin.getImage();
        Image scaledImage2 = originalImage2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon imgChat = new ImageIcon(scaledImage2);

        JButton btnChat = new JButton(imgChat);
        btnChat.setBackground(Colors.BGROUNDDEEP);
        btnChat.setBorderPainted(false);
        btnChat.setFocusPainted(false);
        btnPanel.add(btnChat);

        c.gridy = 2;
        c.weighty = 0.1;
        screenPanel.add(btnPanel, c);

        // 친구 버튼 클릭 시
        btnFriend.addActionListener(e -> {
            cardLayout.show(mainPanel, "FRIEND");
            header.setHeaderText("친구");
            JButton plusBtn = header.getPlusButton();

            // 기존 리스너 제거 (중복 방지)
            for (ActionListener al : plusBtn.getActionListeners()) {
                plusBtn.removeActionListener(al);
            }

            // 친구 추가 버튼 클릭 시
            plusBtn.addActionListener(e2 -> {
                String friendId = JOptionPane.showInputDialog(f, "추가할 친구의 ID를 입력하세요:");
                if (friendId.equals(id)) {
                    JOptionPane.showMessageDialog(f, "본인은 친구로 추가할 수 없습니다.");
                    return;
                }
                if (friendId != null && !friendId.trim().isEmpty()) {
                    friendId = friendId.trim();

                    // 서버에 친구 추가 요청 보내기
                    boolean result = addFriendToServer(id, friendId);
                    if (result) {
                        JOptionPane.showMessageDialog(f, friendId + "님이 친구로 추가되었습니다.");

                        // 친구 목록 패널 새로고침
                        for (Component comp : mainPanel.getComponents()) {
                            if (comp instanceof FriendPanel) {
                                mainPanel.remove(comp);
                                break;
                            }
                        }
                        mainPanel.add(new FriendPanel(id, mainPanel, cardLayout), "FRIEND");
                        mainPanel.revalidate();
                        mainPanel.repaint();

                        // FRIEND 화면으로 전환
                        cardLayout.show(mainPanel, "FRIEND");
                    } else {
                        JOptionPane.showMessageDialog(f, "존재하지 않는 아이디이거나 이미 친구입니다.");
                    }
                }
            });

        });

        // 채팅 버튼 클릭 시
        btnChat.addActionListener(e -> {
            cardLayout.show(mainPanel, "CHAT");
            header.setHeaderText("채팅");
        });

        f.getContentPane().add(screenPanel);
        f.setVisible(true);

        btnFriend.doClick(); // 초기 FRIEND 화면 선택
    }

    // 서버에 친구 추가 요청 보내는 메서드
    private boolean addFriendToServer(String myId, String friendId) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("ADD_FRIEND");
            out.writeObject(myId);
            out.writeObject(friendId);

            String response = (String) in.readObject();
            return "OK".equals(response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
