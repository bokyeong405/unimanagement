package unimanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogIn extends Management {

    private JFrame frame;

    public void showLoginUI() {
        frame = new JFrame("아주대학교 학사관리 시스템");
        frame.setSize(600, 400);  // 창 크기를 더 크게 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("아주대학교");
        titleLabel.setBounds(250, 20, 100, 25);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("ID");
        userLabel.setBounds(100, 100, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(200, 100, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("PW");
        passwordLabel.setBounds(100, 150, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(200, 150, 165, 25);
        panel.add(passwordText);

        JRadioButton studentButton = new JRadioButton("학생");
        studentButton.setBounds(200, 200, 80, 25);
        panel.add(studentButton);

        JRadioButton employeeButton = new JRadioButton("교직원");
        employeeButton.setBounds(300, 200, 80, 25);
        panel.add(employeeButton);

        ButtonGroup group = new ButtonGroup();
        group.add(studentButton);
        group.add(employeeButton);

        JButton loginButton = new JButton("로그인");
        loginButton.setBounds(400, 125, 100, 40);
        panel.add(loginButton);

        JButton registerButton = new JButton("회원가입");
        registerButton.setBounds(100, 300, 100, 25);
        panel.add(registerButton);

        JButton changePasswordButton = new JButton("비밀번호 변경");
        changePasswordButton.setBounds(300, 300, 150, 25);
        panel.add(changePasswordButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = userText.getText();
                String password = new String(passwordText.getPassword());
                Logged logged = new Logged();

                if (studentButton.isSelected()) {
                    boolean success = checkStudentLogin(userId, password, panel);
                    if (success) {
                        logged.showStudentUI(userId, frame);
                    }
                } else if (employeeButton.isSelected()) {
                    boolean success = checkEmployeeLogin(userId, password, panel);
                    if (success) {
                        if (logged.isProfessor(userId)) {
                            logged.showProfessorUI(userId, frame);
                        } else {
                            logged.showEmployeeUI(userId, frame);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "학생 또는 교직원을 선택해주세요");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditManage editManage = new EditManage();
                editManage.showRegisterUI();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditManage editManage = new EditManage();
                editManage.showChangePasswordUI();
            }
        });
    }

    private boolean checkStudentLogin(String userId, String password, JPanel panel) {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    if (data[2].equals(password)) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(panel, "비밀번호가 틀렸습니다.");
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(panel, "아이디가 존재하지 않습니다.");
        return false;
    }

    private boolean checkEmployeeLogin(String userId, String password, JPanel panel) {
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    if (data[2].equals(password)) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(panel, "비밀번호가 틀렸습니다.");
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("professors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    if (data[2].equals(password)) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(panel, "비밀번호가 틀렸습니다.");
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(panel, "아이디가 존재하지 않습니다.");
        return false;
    }
}
