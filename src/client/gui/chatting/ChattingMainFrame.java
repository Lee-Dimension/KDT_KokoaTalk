package client.gui.chatting;
// 채팅창 메인 프레임
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.List;   

import client.gui.style.Colors;
import client.gui.style.ScrollbarStyle;
import client.service.chat.ChatClientService;
import client.service.chat.MessageStorageUtil;
import model.Message;


public class ChattingMainFrame extends JFrame {
	private String userId;
	private String chatRoomId;
	private String chatRoomName;
	private ChatClientService chatClientService;
    private ChatMessagePanel chatMessagePanel;
    private ChattingFooter chattingFooter;
    private JScrollPane scrollPane;

	public ChattingMainFrame(String userId,String chatRoomId,String chatRoomName) {
	    this.userId = userId;
	    this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
	    
        setTitle(chatRoomName);
        setSize(400, 800); //frame 사이즈
        setBackground(Colors.BGROUND); //배경색 지정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new ChattingHeader(chatRoomName), BorderLayout.NORTH);
        
        chatMessagePanel = new ChatMessagePanel(userId);
        // 이전 메시지 불러오기
        List<Message> history = MessageStorageUtil.loadMessages(chatRoomId);
        for (Message m : history) {
            chatMessagePanel.addMessage(m.getSenderId(), m.getSenderName(), m.getContent());
        }
        //스크롤바
        scrollPane = new JScrollPane(chatMessagePanel);
        scrollPane.setBorder(null);
        //스크롤바 디자인 변경
        scrollPane.getVerticalScrollBar().setUI(new ScrollbarStyle());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollbarStyle());        
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
     
        // Footer 생성 후 보내기 버튼 이벤트 연결
        chattingFooter = new ChattingFooter();
        mainPanel.add(chattingFooter, BorderLayout.SOUTH);

        
        add(mainPanel); 
        setVisible(true);
        
        //스크롤바 가장 아래로 이동
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
        
        // Footer의 입력 필드에서 엔터 눌렀을 때 메시지 보내기
        chattingFooter.getInputText().addActionListener(e -> {sendAndClear(); });
        
        // 창 닫힐 때 서비스 종료
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                chatClientService.close();
            }
        });
        
        try {
        	 chatClientService = new ChatClientService("localhost", 7777);
        	 // 메시지 수신 시 UI에 전달
        	 chatClientService.setMessageHandler(m -> SwingUtilities.invokeLater(() -> {
    		    // 메시지 저장
    		    MessageStorageUtil.saveMessage(m);

    		    // UI에 표시
    		    chatMessagePanel.addMessage(m.getSenderId(), m.getSenderName(), m.getContent());

    		    // 스크롤바 맨 아래로
    		    JScrollBar bar = scrollPane.getVerticalScrollBar();
    		    bar.setValue(bar.getMaximum());
    		}));
        	 // 방 입장 요청
        	 chatClientService.joinRoom(userId, chatRoomId);
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	

	
    // 메시지 보내고 입력창 초기화
    private void sendAndClear() {
        String message = chattingFooter.getInputText().getText().trim();
        if (!message.isEmpty()) {
            sendMessage(message);
            chattingFooter.getInputText().setText("");
        }
    }
    // 메시지 보냄
    public void sendMessage(String message) {
        try {
        	chatClientService.sendMessage(userId, chatRoomId, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
