package KokoaTalk.Profile;

import KokoaTalk.Colors;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

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
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(150, 30));
        idPanel.add(idField);

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setBackground(Colors.BGROUND);
        namePanel.add(new JLabel("    이름 : "));
        nameField = new JTextField();
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
        signUpBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();

            if(id.isEmpty() || name.isEmpty()){
                JOptionPane.showMessageDialog(this, "아이디와 이름을 모두 입력하세요!");
                return;
            }

            UserClass newUser = new UserClass(id, name);

            boolean result = signUpToServer(newUser);

            if(result){
                JOptionPane.showMessageDialog(this, "회원가입 완료!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "이미 존재하는 아이디거나 오류가 발생했습니다.");
            }
        });
    }

    // 실제 서버로 회원가입 요청 메서드 (간단한 예시)
    private boolean signUpToServer(UserClass user) {
        try (
            Socket socket = new Socket("localhost", 7777); // 서버 주소/포트 맞춰서!
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("SIGNUP"); // 명령어
            out.writeObject(user);     // 유저 객체 전송

            String response = (String) in.readObject(); // "OK" or "FAIL"
            return "OK".equals(response);
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
