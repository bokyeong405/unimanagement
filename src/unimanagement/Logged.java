package unimanagement;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Logged extends Management {

    public void showStudentUI(String userId, JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("환영합니다, " + getUserName(userId, "students.txt") + "님!");
        welcomeLabel.setBounds(10, 10, 300, 25);
        frame.add(welcomeLabel);

        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setBounds(300, 10, 100, 25);
        frame.add(logoutButton);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            LogIn login = new LogIn();
            login.showLoginUI();
        });

        JLabel menuLabel = new JLabel("학사서비스 메뉴");
        menuLabel.setBounds(150, 50, 200, 25);
        frame.add(menuLabel);

        JButton studentInfoButton = new JButton("학생정보");
        studentInfoButton.setBounds(50, 100, 150, 50);
        frame.add(studentInfoButton);
        studentInfoButton.addActionListener(e -> {
            Logged_Info loggedInfo = new Logged_Info();
            loggedInfo.showStudentInfo(userId);
        });

        JButton courseRegistrationButton = new JButton("수강신청");
        courseRegistrationButton.setBounds(250, 100, 150, 50);
        frame.add(courseRegistrationButton);
        courseRegistrationButton.addActionListener(e -> {
            Logged_Student loggedStudent = new Logged_Student();
            loggedStudent.showCourseRegistration(userId);
        });

        JButton gradeInquiryButton = new JButton("성적 조회");
        gradeInquiryButton.setBounds(50, 200, 150, 50);
        frame.add(gradeInquiryButton);
        gradeInquiryButton.addActionListener(e -> {
            Logged_Student loggedStudent = new Logged_Student();
            loggedStudent.showGradeInquiry(userId);
        });

        JButton counselingButton = new JButton("상담 신청");
        counselingButton.setBounds(250, 200, 150, 50);
        frame.add(counselingButton);
        counselingButton.addActionListener(e -> {
            Logged_Student loggedStudent = new Logged_Student();
            loggedStudent.showCounselingRegistration(userId);
        });

        frame.revalidate();
        frame.repaint();
    }

    public void showProfessorUI(String userId, JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("환영합니다, " + getUserName(userId, "professors.txt") + "님!");
        welcomeLabel.setBounds(10, 10, 300, 25);
        frame.add(welcomeLabel);

        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setBounds(300, 10, 100, 25);
        frame.add(logoutButton);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            LogIn login = new LogIn();
            login.showLoginUI();
        });

        JLabel menuLabel = new JLabel("학사서비스 메뉴");
        menuLabel.setBounds(150, 50, 200, 25);
        frame.add(menuLabel);

        JButton professorInfoButton = new JButton("교수정보");
        professorInfoButton.setBounds(50, 100, 150, 50);
        frame.add(professorInfoButton);
        professorInfoButton.addActionListener(e -> {
            Logged_Info loggedInfo = new Logged_Info();
            loggedInfo.showProfessorInfo(userId);
        });

        JButton courseManagementButton = new JButton("강의 관리");
        courseManagementButton.setBounds(250, 100, 150, 50);
        frame.add(courseManagementButton);
        courseManagementButton.addActionListener(e -> {
            Logged_Professor loggedProfessor = new Logged_Professor();
            loggedProfessor.showCourseManagement(userId);
        });

        JButton gradeInputButton = new JButton("성적 입력");
        gradeInputButton.setBounds(50, 200, 150, 50);
        frame.add(gradeInputButton);
        gradeInputButton.addActionListener(e -> {
            Logged_Professor loggedProfessor = new Logged_Professor();
            loggedProfessor.showGradeInput(userId);
        });

        JButton counselingCheckButton = new JButton("상담 확인");
        counselingCheckButton.setBounds(250, 200, 150, 50);
        frame.add(counselingCheckButton);
        counselingCheckButton.addActionListener(e -> {
            Logged_Professor loggedProfessor = new Logged_Professor();
            loggedProfessor.showCounselingCheck(userId);
        });

        frame.revalidate();
        frame.repaint();
    }

    public void showEmployeeUI(String userId, JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("환영합니다, " + getUserName(userId, "employees.txt") + "님!");
        welcomeLabel.setBounds(10, 10, 300, 25);
        frame.add(welcomeLabel);

        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setBounds(300, 10, 100, 25);
        frame.add(logoutButton);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            LogIn login = new LogIn();
            login.showLoginUI();
        });

        JLabel menuLabel = new JLabel("학사서비스 메뉴");
        menuLabel.setBounds(150, 50, 200, 25);
        frame.add(menuLabel);

        JButton employeeInfoButton = new JButton("직원 정보 조회");
        employeeInfoButton.setBounds(50, 100, 150, 50);
        frame.add(employeeInfoButton);
        employeeInfoButton.addActionListener(e -> {
            Logged_Info loggedInfo = new Logged_Info();
            loggedInfo.showEmployeeInfo(userId);
        });

        JButton enrollmentViewButton = new JButton("수강신청 조회");
        enrollmentViewButton.setBounds(250, 100, 150, 50);
        frame.add(enrollmentViewButton);
        enrollmentViewButton.addActionListener(e -> {
            Logged_Employee loggedEmployee = new Logged_Employee();
            loggedEmployee.showEnrollmentView();
        });

        JButton studentManagementButton = new JButton("학생 정보 조회");
        studentManagementButton.setBounds(50, 200, 150, 50);
        frame.add(studentManagementButton);
        studentManagementButton.addActionListener(e -> {
            Logged_Employee loggedEmployee = new Logged_Employee();
            loggedEmployee.showStudentManagement();
        });

        JButton professorManagementButton = new JButton("교수 정보 조회");
        professorManagementButton.setBounds(250, 200, 150, 50);
        frame.add(professorManagementButton);
        professorManagementButton.addActionListener(e -> {
            Logged_Employee loggedEmployee = new Logged_Employee();
            loggedEmployee.showProfessorManagement();
        });

        frame.revalidate();
        frame.repaint();
    }

    protected String getUserName(String userId, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    return data[1]; // 사용자 이름을 반환
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "사용자";
    }

    public boolean isProfessor(String userId) {
        try (BufferedReader br = new BufferedReader(new FileReader("professors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
