package unimanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Logged_Employee extends Logged {

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
            Logged_Employee loggedEmployee = new Logged_Employee();
            loggedEmployee.showEmployeeInfo(userId);
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

    public void showEmployeeInfo(String userId) {
        JFrame frame = new JFrame("직원 정보");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        frame.add(panel, BorderLayout.CENTER);

        for (Employee employee : employees) {
            if (employee.getId().equals(userId)) {
                panel.add(new JLabel("ID: " + employee.getId()));
                panel.add(new JLabel("이름: " + employee.getName()));
                panel.add(new JLabel("비밀번호: " + employee.getPassword()));
                panel.add(new JLabel("성별: " + employee.getGender()));
                panel.add(new JLabel("전화번호: " + employee.getPhoneNumber()));
                panel.add(new JLabel("이메일: " + employee.getEmail()));
                panel.add(new JLabel("부서: " + employee.getDepartment()));
                panel.add(new JLabel("근무 연수: " + employee.getYearsOfService()));
                break;
            }
        }

        JButton saveButton = new JButton("파일로 저장하기");
        saveButton.addActionListener(e -> saveEmployeeInfoToFile(userId));
        frame.add(saveButton, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private void saveEmployeeInfoToFile(String userId) {
        for (Employee employee : employees) {
            if (employee.getId().equals(userId)) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("employee_info.txt"))) {
                    bw.write("ID: " + employee.getId());
                    bw.newLine();
                    bw.write("이름: " + employee.getName());
                    bw.newLine();
                    bw.write("비밀번호: " + employee.getPassword());
                    bw.newLine();
                    bw.write("성별: " + employee.getGender());
                    bw.newLine();
                    bw.write("전화번호: " + employee.getPhoneNumber());
                    bw.newLine();
                    bw.write("이메일: " + employee.getEmail());
                    bw.newLine();
                    bw.write("부서: " + employee.getDepartment());
                    bw.newLine();
                    bw.write("근무 연수: " + employee.getYearsOfService());
                    bw.newLine();
                    JOptionPane.showMessageDialog(null, "파일로 저장되었습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void showStudentManagement() {
        JFrame frame = new JFrame("학생 정보 조회");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("이름");
        model.addColumn("비밀번호");
        model.addColumn("성별");
        model.addColumn("전화번호");
        model.addColumn("이메일");
        model.addColumn("학과");
        model.addColumn("완료 학기 수");
        model.addColumn("학생 번호");
        model.addColumn("입학 연도");
        model.addColumn("휴학 학기 수");
        model.addColumn("이전 성적");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("파일로 저장하기");
        saveButton.addActionListener
        (e -> saveTableDataToFile(model, "students_data.txt"));
        panel.add(saveButton, BorderLayout.NORTH);

        loadStudentData(model);

        frame.setVisible(true);
    }

    private void loadStudentData(DefaultTableModel model) {
        model.setRowCount(0); // Clear the table
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProfessorManagement() {
        JFrame frame = new JFrame("교수 정보 조회");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("이름");
        model.addColumn("비밀번호");
        model.addColumn("성별");
        model.addColumn("전화번호");
        model.addColumn("이메일");
        model.addColumn("학과");
        model.addColumn("경력");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("파일로 저장하기");
        saveButton.addActionListener(e -> saveTableDataToFile(model, "professors_data.txt"));
        panel.add(saveButton, BorderLayout.NORTH);

        loadProfessorData(model);

        frame.setVisible(true);
    }

    private void loadProfessorData(DefaultTableModel model) {
        model.setRowCount(0); // Clear the table
        try (BufferedReader br = new BufferedReader(new FileReader("professors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEnrollmentView() {
        JFrame frame = new JFrame("수강신청 조회");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new FlowLayout());

        JComboBox<String> courseTypeComboBox = new JComboBox<>(new String[]{"전체", "전공", "교양"});
        comboPanel.add(courseTypeComboBox);

        JComboBox<String> majorComboBox = new JComboBox<>(new String[]{"선택", "전자공학과", "소프트웨어학과", "기계공학과", "화학공학과", "건축학과", "생명과학과", "물리학과", "수학과", "경영학과", "경제학과"});
        comboPanel.add(majorComboBox);
        majorComboBox.setVisible(false);

        JComboBox<String> courseComboBox = new JComboBox<>();
        comboPanel.add(courseComboBox);

        panel.add(comboPanel, BorderLayout.NORTH);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("이름");
        model.addColumn("학번");
        model.addColumn("소속학과");
        model.addColumn("이메일");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        courseTypeComboBox.addActionListener(e -> {
            String selectedType = (String) courseTypeComboBox.getSelectedItem();
            if ("전공".equals(selectedType)) {
                majorComboBox.setVisible(true);
                courseComboBox.removeAllItems();
            } else {
                majorComboBox.setVisible(false);
                loadCourses(selectedType, null, courseComboBox);
            }
        });

        majorComboBox.addActionListener(e -> {
            String selectedType = (String) courseTypeComboBox.getSelectedItem();
            String selectedMajor = (String) majorComboBox.getSelectedItem();
            if (selectedMajor != null && !selectedMajor.equals("선택")) {
                loadCourses(selectedType, selectedMajor, courseComboBox);
            }
        });

        courseComboBox.addActionListener(e -> {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            if (selectedCourse != null) {
                loadEnrollmentData(selectedCourse, model);
            }
        });

        frame.setVisible(true);
    }

    private void loadCourses(String type, String major, JComboBox<String> courseComboBox) {
        courseComboBox.removeAllItems();
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    if ("전공".equals(type) && major != null && data[1].startsWith(getMajorCode(major))) {
                        courseComboBox.addItem(data[0] + " (" + data[1] + ")");
                    } else if ("교양".equals(type) && data[1].startsWith("00")) {
                        courseComboBox.addItem(data[0] + " (" + data[1] + ")");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEnrollmentData(String selectedCourse, DefaultTableModel model) {
        model.setRowCount(0); // Clear the table
        try (BufferedReader br = new BufferedReader(new FileReader("enrollment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4 && selectedCourse.equals(data[1] + " (" + data[2] + ")")) {
                    String studentId = data[0];
                    String[] studentData = getStudentData(studentId);
                    if (studentData != null) {
                        model.addRow(new Object[]{studentData[1], studentData[8], studentData[6], studentData[5]});
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getStudentData(String studentId) {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(studentId)) {
                    return data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMajorCode(String major) {
        switch (major) {
            case "전자공학과":
                return "01";
            case "소프트웨어학과":
                return "02";
            case "기계공학과":
                return "03";
            case "화학공학과":
                return "04";
            case "건축학과":
                return "05";
            case "생명과학과":
                return "06";
            case "물리학과":
                return "07";
            case "수학과":
                return "08";
            case "경영학과":
                return "09";
            case "경제학과":
                return "10";
            default:
                return "";
        }
    }

    private void saveTableDataToFile(DefaultTableModel model, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < model.getColumnCount(); i++) {
                bw.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) {
                    bw.write(",");
                }
            }
            bw.newLine();

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    bw.write(model.getValueAt(i, j).toString());
                    if (j < model.getColumnCount() - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
            JOptionPane.showMessageDialog(null, "파일로 저장되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
