package KokoaTalk.Profile;

import KokoaTalk.FrameMain;
import KokoaTalk.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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
        logoLabel.setFont(new Font("SanSefit", Font.BOLD, 24));
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
        
        //로그인 버튼 클릭 시
        loginBtn.addActionListener(new LoginAction());
        
        // 회원가입 버튼 클릭 시
        signUpBtn.addActionListener( e -> {new SignUpGUI();});
        
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

            boolean loginSuccess = false;
            String userName = "";

            try (BufferedReader br = new BufferedReader(new FileReader("src/KokoaTalk/UserList/userList.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens.length >= 2) {
                        String id = tokens[0].trim();
                        String name = tokens[1].trim();
                        if (enteredId.equals(id)) {
                            loginSuccess = true;
                            userName = name;
                            break;
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "파일을 읽는 도중 오류가 발생했습니다.");
                return;
            }

            if (loginSuccess) {
                JOptionPane.showMessageDialog(null, "로그인 성공! " + userName + "님 환영합니다!");
                new FrameMain(enteredId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "해당 아이디를 찾을 수 없습니다.");
            }
        }
    }


    public static void main(String[] args) {
        new UserLoginGUI();
    }
}
