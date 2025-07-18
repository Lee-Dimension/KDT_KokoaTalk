package KokoaTalk.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import KokoaTalk.Colors;
import KokoaTalk.FrameMain;

public class UserLoginGUI extends JFrame {

    private JTextField idField;
    private JButton loginBtn, signUpBtn;

    public UserLoginGUI() {
        setTitle("코코아톡 로그인");
        setSize(400,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 코코아톡 글자
        JLabel logoLabel = new JLabel("코코아톡");
        logoLabel.setBackground(Colors.BGROUND);
        logoLabel.setOpaque(true);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        logoLabel.setForeground(Colors.TEXT);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(logoLabel, BorderLayout.NORTH);

        // 가운데
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Colors.BGROUND);
        idField = new JTextField(15);
        loginBtn = new JButton("로그인");
        HideBtnDesign.apply(loginBtn);
        centerPanel.add(idField);
        centerPanel.add(loginBtn);
        add(centerPanel, BorderLayout.CENTER);

        // 하단 회원가입 버튼
        JPanel signUpPanel = new JPanel();
        signUpPanel.setBackground(Colors.BGROUND);
        signUpBtn = new JButton("회원가입");
        HideBtnDesign.apply(signUpBtn);
        signUpPanel.add(signUpBtn);

        add(signUpPanel, BorderLayout.SOUTH);
        setVisible(true);

        // 로그인 버튼 클릭 시
        loginBtn.addActionListener(new LoginAction());

        // 회원가입 버튼 클릭 시
        signUpBtn.addActionListener( e -> {new SignUpGUI();});
    }

    // 서버에 로그인 요청하는 메서드
    private UserClass loginToServer(String enteredId) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("LOGIN");
            out.writeObject(enteredId);

            Object response = in.readObject();
            if (response instanceof UserClass) {
                return (UserClass) response;
            } else {
                return null;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 로그인 버튼 이벤트
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String enteredId = idField.getText().trim();
            if (enteredId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
                return;
            }

            UserClass user = loginToServer(enteredId);
            if (user != null) {
                JOptionPane.showMessageDialog(null, "로그인 성공!\n" + user.getName() + "님 환영합니다!");
                new FrameMain(enteredId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "해당 아이디를 찾을 수 없습니다.");
            }
        }
    }
}
