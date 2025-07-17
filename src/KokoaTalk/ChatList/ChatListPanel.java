package KokoaTalk.ChatList;

import KokoaTalk.Colors;
import KokoaTalk.Chatting.ChattingMainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ChatListPanel extends JPanel {
    public ChatListPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 광고 라벨
        JLabel titleLabel = new JLabel("광고 받습니다~");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 16, 25, 16));
        add(titleLabel, BorderLayout.NORTH);

        // 채팅방 목록 패널 생성
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(Colors.BGROUND);

        File folder = new File("chat_rooms");
        File[] chatFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (chatFiles != null) {
            for (File file : chatFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String nameLine = reader.readLine();              // 채팅방 이름: 친구1
                    String participantsLine = reader.readLine();      // 참여자: 사용자A, 사용자B (사용안함)
                    String messageLine = reader.readLine();           // 최근 메시지: 잘 지내? (사용안함)
                    String timeLine = reader.readLine();              // 시간: 오전 9:30
                    String unreadLine = reader.readLine();            // 안 읽은 메시지: 3  (추가 가정)

                    if (nameLine != null && timeLine != null) {
                        String chatName = nameLine.replace("채팅방 이름: ", "").trim();
                        String time = timeLine.replace("시간: ", "").trim();
                        int unreadCount = 0;

                        if (unreadLine != null && unreadLine.startsWith("안 읽은 메시지: ")) {
                            try {
                                unreadCount = Integer.parseInt(unreadLine.replace("안 읽은 메시지: ", "").trim());
                            } catch (NumberFormatException e) {
                                unreadCount = 0; // 파싱 실패 시 0으로 초기화
                            }
                        }

                        // 채팅방 버튼 생성
                        JButton chatItem = new JButton();
                        chatItem.setLayout(new BoxLayout(chatItem, BoxLayout.X_AXIS)); //
                        chatItem.setBackground(Colors.BGROUND);
                        chatItem.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
                        chatItem.setFocusPainted(false);
                        chatItem.setContentAreaFilled(false);
                        chatItem.setOpaque(true);

                        chatItem.addActionListener(e -> new ChattingMainPanel(chatName));

                        //프로필 이미지 및 이름 패널
                        JPanel profilePanel = new JPanel();
                        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
                        profilePanel.setBackground(Colors.BGROUND);
                        
                        // 프로필 이미지 기본 경로 사용
                        String profilePath = "src/KokoaTalkImg/face.jpg";
                        ImageIcon profileIcon = new ImageIcon(profilePath);
                        JLabel profileLabel = new JLabel(new ImageIcon(profileIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
                        //chatItem.add(Box.createRigidArea(new Dimension(10, 0)));
                        profilePanel.add(profileLabel);
                        
                        // 채팅방 이름
                        JLabel nameLabel = new JLabel(chatName);
                        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                        profilePanel.add(nameLabel);
                        
                        //전체 큰 공간에서 가로로 빈공간 추가
                        JLabel emptyPanel = new JLabel();
                        emptyPanel.setPreferredSize(new Dimension(180, 0));
                        emptyPanel.setBackground(Colors.BGROUND);
                        
                        
                        // 정보 패널 (마지막 시간, 안 읽은 메시지 갯수)
                        JPanel infoPanel = new JPanel();
                        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                        infoPanel.setBackground(Colors.BGROUND);
                        
                        JLabel timeLabel = new JLabel("마지막 대화: " + time);
                        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                        timeLabel.setForeground(Color.GRAY);
                        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                        infoPanel.add(timeLabel);

                        // 읽지 않은 메시지 갯수 출력
                        if (unreadCount > 0) {
                            JLabel unreadLabel = new JLabel(""+unreadCount);
                            unreadLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                            unreadLabel.setForeground(Color.RED);
                            unreadLabel.setAlignmentX(RIGHT_ALIGNMENT);
                            unreadLabel.setPreferredSize(new Dimension(10, 5));
                            infoPanel.add(unreadLabel);
                        }
                        
                        
                   

                        chatItem.add(profilePanel);
                        chatItem.add(emptyPanel);
                        chatItem.add(infoPanel);
                       
                        chatItem.add(Box.createHorizontalGlue());

                        chatListPanel.add(chatItem);
                        //chatListPanel.add(new JSeparator(JSeparator.HORIZONTAL));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        JScrollPane scrollPanel = new JScrollPane(chatListPanel);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPanel, BorderLayout.CENTER);
    }
}
