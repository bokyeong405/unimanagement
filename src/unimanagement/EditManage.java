package unimanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class EditManage extends Management { // 회원가입 및 비밀번호 변경

    public void showRegisterUI() {
        JFrame frame = new JFrame("회원가입");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);
        placeRegisterComponents(panel, frame);

        frame.setVisible(true);
    }

    private void placeRegisterComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("ID");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("PW");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 50, 165, 25);
        panel.add(passwordText);

        JLabel passwordReLabel = new JLabel("PW 재입력");
        passwordReLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordReLabel);

        JPasswordField passwordReText = new JPasswordField(20);
        passwordReText.setBounds(150, 80, 165, 25);
        panel.add(passwordReText);

        JRadioButton studentButton = new JRadioButton("학생");
        studentButton.setBounds(10, 110, 80, 25);
        panel.add(studentButton);

        JRadioButton employeeButton = new JRadioButton("교직원");
        employeeButton.setBounds(150, 110, 80, 25);
        panel.add(employeeButton);

        ButtonGroup group = new ButtonGroup();
        group.add(studentButton);
        group.add(employeeButton);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(10, 140, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 140, 165, 25);
        panel.add(nameText);

        JLabel genderLabel = new JLabel("성별");
        genderLabel.setBounds(10, 170, 80, 25);
        panel.add(genderLabel);

        JTextField genderText = new JTextField(20);
        genderText.setBounds(150, 170, 165, 25);
        panel.add(genderText);

        JLabel phoneLabel = new JLabel("전화번호");
        phoneLabel.setBounds(10, 200, 80, 25);
        panel.add(phoneLabel);

        JTextField phoneText = new JTextField(20);
        phoneText.setBounds(150, 200, 165, 25);
        panel.add(phoneText);

        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setBounds(10, 230, 80, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(150, 230, 165, 25);
        panel.add(emailText);

        JLabel departmentLabel = new JLabel("소속학과");
        departmentLabel.setBounds(10, 260, 80, 25);
        panel.add(departmentLabel);

        JTextField departmentText = new JTextField(20);
        departmentText.setBounds(150, 260, 165, 25);
        panel.add(departmentText);

        JLabel studentIdLabel = new JLabel("학번");
        studentIdLabel.setBounds(10, 290, 80, 25);
        panel.add(studentIdLabel);

        JTextField studentIdText = new JTextField(20);
        studentIdText.setBounds(150, 290, 165, 25);
        panel.add(studentIdText);

        JLabel employeeIdLabel = new JLabel("사번");
        employeeIdLabel.setBounds(10, 290, 80, 25);
        panel.add(employeeIdLabel);

        JTextField employeeIdText = new JTextField(20);
        employeeIdText.setBounds(150, 290, 165, 25);
        panel.add(employeeIdText);

        studentIdLabel.setVisible(false);
        studentIdText.setVisible(false);
        employeeIdLabel.setVisible(false);
        employeeIdText.setVisible(false);

        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentIdLabel.setVisible(true);
                studentIdText.setVisible(true);
                employeeIdLabel.setVisible(false);
                employeeIdText.setVisible(false);
            }
        });

        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentIdLabel.setVisible(false);
                studentIdText.setVisible(false);
                employeeIdLabel.setVisible(true);
                employeeIdText.setVisible(true);
            }
        });

        JButton registerButton = new JButton("회원가입");
        registerButton.setBounds(10, 320, 150, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = userText.getText();
                String password = new String(passwordText.getPassword());
                String passwordRe = new String(passwordReText.getPassword());

                if (!password.equals(passwordRe)) {
                    JOptionPane.showMessageDialog(panel, "비밀번호가 일치하지 않습니다.");
                    return;
                }

                String name = nameText.getText();
                String gender = genderText.getText();
                String phoneNumber = phoneText.getText();
                String email = emailText.getText();
                String department = departmentText.getText();

                if (studentButton.isSelected()) {
                    String studentIdStr = studentIdText.getText();
                    int studentId = Integer.parseInt(studentIdStr);
                    int enrollmentYear = Integer.parseInt(studentIdStr.substring(0, 4)); // 학번의 앞 4자리를 enrollmentYear에 저장
                    int completedSemesters = (2024 - enrollmentYear) * 2; // 이수학기 계산

                    // leaveOfAbsenceSemesters, previousGrade 초기화
                    int leaveOfAbsenceSemesters = 0;
                    double previousGrade = 0.0;

                    Student student = new Student(id, name, password, gender, phoneNumber, email, department,
                            completedSemesters, studentId, enrollmentYear,
                            leaveOfAbsenceSemesters, previousGrade);
                    students.add(student);
                    saveStudentToFile(student); // 단일 학생 추가
                } else if (employeeButton.isSelected()) {
                    int employeeId = Integer.parseInt(employeeIdText.getText());
                    Employee employee = new Employee(id, name, password, gender, phoneNumber, email, department, employeeId);
                    employees.add(employee);
                    saveEmployeeToFile(employee); // 단일 직원 추가
                }
                JOptionPane.showMessageDialog(panel, "회원가입이 완료되었습니다.");
                frame.dispose();
            }
        });
    }

    private void saveStudentToFile(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt", true))) { // append 모드 활성화
            bw.write(student.toCSV());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProfessorToFile(Professor professor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("professors.txt", true))) { // append 모드 활성화
            bw.write(professor.toCSV());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEmployeeToFile(Employee employee) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt", true))) { // append 모드 활성화
            bw.write(employee.toCSV());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showChangePasswordUI() {
        JFrame frame = new JFrame("비밀번호 변경");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);
        placeChangePasswordComponents(panel, frame);

        frame.setVisible(true);
    }

    private void placeChangePasswordComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("ID");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 20, 165, 25);
        panel.add(userText);

        JLabel currentPasswordLabel = new JLabel("현재 PW");
        currentPasswordLabel.setBounds(10, 50, 80, 25);
        panel.add(currentPasswordLabel);

        JPasswordField currentPasswordText = new JPasswordField(20);
        currentPasswordText.setBounds(150, 50, 165, 25);
        panel.add(currentPasswordText);

        JLabel newPasswordLabel = new JLabel("새 PW");
        newPasswordLabel.setBounds(10, 80, 80, 25);
        panel.add(newPasswordLabel);

        JPasswordField newPasswordText = new JPasswordField(20);
        newPasswordText.setBounds(150, 80, 165, 25);
        panel.add(newPasswordText);

        JLabel newPasswordReLabel = new JLabel("새 PW 재입력");
        newPasswordReLabel.setBounds(10, 110, 80, 25);
        panel.add(newPasswordReLabel);

        JPasswordField newPasswordReText = new JPasswordField(20);
        newPasswordReText.setBounds(150, 110, 165, 25);
        panel.add(newPasswordReText);

        JRadioButton studentButton = new JRadioButton("학생");
        studentButton.setBounds(10, 140, 80, 25);
        panel.add(studentButton);

        JRadioButton employeeButton = new JRadioButton("교직원");
        employeeButton.setBounds(150, 140, 80, 25);
        panel.add(employeeButton);

        ButtonGroup group = new ButtonGroup();
        group.add(studentButton);
        group.add(employeeButton);

        JButton changePasswordButton = new JButton("비밀번호 변경");
        changePasswordButton.setBounds(10, 170, 150, 25);
        panel.add(changePasswordButton);

        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = userText.getText();
                String currentPassword = new String(currentPasswordText.getPassword());
                String newPassword = new String(newPasswordText.getPassword());
                String newPasswordRe = new String(newPasswordReText.getPassword());

                if (!newPassword.equals(newPasswordRe)) {
                    JOptionPane.showMessageDialog(panel, "새 비밀번호가 일치하지 않습니다.");
                    return;
                }

                boolean isUpdated = false;

                if (studentButton.isSelected()) {
                    for (Student student : students) {
                        if (student.getId().equals(id) && student.getPassword().equals(currentPassword)) {
                            student.setPassword(newPassword);
                            isUpdated = true;
                            break;
                        }
                    }
                    if (isUpdated) {
                        saveStudentsToFile(); // 전체 학생 리스트를 파일에 저장
                    }
                } else if (employeeButton.isSelected()) {
                    for (Employee employee : employees) {
                        if (employee.getId().equals(id) && employee.getPassword().equals(currentPassword)) {
                            employee.setPassword(newPassword);
                            isUpdated = true;
                            break;
                        }
                    }
                    if (isUpdated) {
                        saveEmployeesToFile(); // 전체 직원 리스트를 파일에 저장
                    }
                }

                if (isUpdated) {
                    JOptionPane.showMessageDialog(panel, "비밀번호가 변경되었습니다.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(panel, "ID 또는 비밀번호가 일치하지 않습니다.");
                }
            }
        });
    }

    private void saveStudentsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt"))) { // overwrite 모드
            for (Student student : students) {
                bw.write(student.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEmployeesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt"))) { // overwrite 모드
            for (Employee employee : employees) {
                bw.write(employee.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
