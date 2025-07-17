package KokoaTalk.Profile;

import KokoaTalk.Colors;

import javax.swing.*;
import java.awt.*;

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
        JLabel logoLabel = new JLabel("코코아톡");
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
        signUpBtn = new JButton("회원가입");
        HideBtnDesign.apply(signUpBtn);
        signUpPanel.add(signUpBtn);
        add(signUpPanel, BorderLayout.SOUTH);
        setVisible(true);
        // 회원가입 버튼 클릭 이벤트
//        signUpBtn.addActionListener();
    }

}