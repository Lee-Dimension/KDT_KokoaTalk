package KokoaTalk.Profile;

import KokoaTalk.Colors;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SignUpGUI extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JButton signUpBtn;

    public SignUpGUI() {
        
        setTitle("코코아톡 회원가입");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // 창만 닫힘
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 로고
        JLabel logoLabel = new JLabel("회원가입");
        logoLabel.setBackground(Colors.BGROUND);
        logoLabel.setOpaque(true);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        logoLabel.setForeground(Colors.TEXT);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(logoLabel, BorderLayout.NORTH);

        // 중앙 입력 패널
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(Colors.BGROUND);

        JPanel idPanel = new JPanel(new FlowLayout());
        idPanel.setBackground(Colors.BGROUND);
        idPanel.add(new JLabel("아이디 : "));
        idField = new JTextField(); //id입력받는 패널
        idField.setPreferredSize(new Dimension(150, 30));
        idPanel.add(idField);

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setBackground(Colors.BGROUND);
        namePanel.add(new JLabel("    이름 : "));
        nameField = new JTextField(); //이름 입력받는 패널
        nameField.setPreferredSize(new Dimension(150, 30));
        namePanel.add(nameField);

        
        centerPanel.add(idPanel);
        centerPanel.add(namePanel);

        add(centerPanel, BorderLayout.CENTER);

        // 하단 회원가입 버튼
        JPanel signUpPanel = new JPanel();
        signUpPanel.setBackground(Colors.BGROUND);
        signUpBtn = new JButton("확인");
        HideBtnDesign.apply(signUpBtn);
        signUpPanel.add(signUpBtn);
        add(signUpPanel, BorderLayout.SOUTH);
        setVisible(true);
        
        // 회원가입 버튼 클릭 이벤트
        signUpBtn.addActionListener( e -> {
        	String id = idField.getText(); 
        	String name = nameField.getText();
        	
            // id 중복 확인
            File userFile = new File("src/UserList/"+id + ".ser");
            if (userFile.exists()) {
                JOptionPane.showMessageDialog(this, "이미 있는 id입니다.");
                return;
            }
        	// 유저 파일 만들기
        	 UserClass user = new UserClass(id, name);
        	 UserFileManager.saveUser(user, "src/UserList/" + id+".ser");
        	 
    	    // ✅ 친구리스트 파일만 생성
    	    File friendListFile = new File("src/FriendList/" + id + ".txt");
    	    try {
    	        friendListFile.createNewFile(); // 이미 있으면 아무 일도 안 함
    	    } catch (IOException ex) {
    	    	System.out.printf("경로 못찼겠엉");
    	        ex.printStackTrace();
    	    }
        	 
    	    // 완료 메시지
    	    JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!");

    	    // 창 닫기
    	    this.dispose();
        });
    }
}