package client.gui.login;

import javax.swing.*;

import client.gui.MainFrame;
import client.gui.style.Colors;
import client.gui.style.HideButtonStyle;
import model.User;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

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
        HideButtonStyle.apply(loginBtn);
        centerPanel.add(idField);
        centerPanel.add(loginBtn);
        add(centerPanel, BorderLayout.CENTER);

        // 하단 회원가입 버튼
        JPanel signUpPanel = new JPanel();
        signUpPanel.setBackground(Colors.BGROUND);
        signUpBtn = new JButton("회원가입");
        HideButtonStyle.apply(signUpBtn);
        signUpPanel.add(signUpBtn);

        add(signUpPanel, BorderLayout.SOUTH);
        setVisible(true);

        // 로그인 버튼 클릭 시
        loginBtn.addActionListener(new LoginAction());

        // 회원가입 버튼 클릭 시
        signUpBtn.addActionListener( e -> {new SignUpFrame();});
    }

    // 서버에 로그인 요청하는 메서드
    private User loginToServer(String enteredId) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("LOGIN");
            out.writeObject(enteredId);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof User) {
                return (User) response;
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

            User user = loginToServer(enteredId);
            if (user != null) {
                JOptionPane.showMessageDialog(null, "로그인 성공!\n" + user.getName() + "님 환영합니다!");
                new MainFrame(enteredId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "해당 아이디를 찾을 수 없습니다.");
            }
        }
    }
}
