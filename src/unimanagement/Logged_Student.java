package unimanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logged_Student extends Logged {

    public void showCourseRegistration(String userId) {
        Enrollment enrollment = new Enrollment();
        enrollment.showCourseRegistration(userId);
    }

    public void showGradeInquiry(String studentId) {
        JFrame frame = new JFrame("성적 조회");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("학생 학번");
        model.addColumn("강의 코드");
        model.addColumn("강의 이름");
        model.addColumn("학점");
        model.addColumn("성적");
        model.addColumn("수강학기");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadGradeData(studentId, model);

        frame.setVisible(true);
    }

    private void loadGradeData(String studentId, DefaultTableModel model) {
        model.setRowCount(0); // Clear the table
        Map<String, String> courseNameMap = loadCourseNames();
        List<String[]> grades = new ArrayList<>();
        double totalCredits = 0;
        double totalGradePoints = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("courseHistory.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // 디버깅을 위해 각 줄 출력
                System.out.println("Read line: " + line);
                if (data[0].equals(studentId)) {
                    grades.add(data);
                    totalCredits += Integer.parseInt(data[3]);
                    totalGradePoints += Integer.parseInt(data[3]) * Double.parseDouble(data[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (grades.isEmpty()) {
            model.addRow(new Object[]{"수강 내역이 없습니다.", "", "", "", "", ""});
        } else {
            String currentSemester = "";
            for (String[] grade : grades) {
                if (!grade[5].equals(currentSemester)) {
                    currentSemester = grade[5];
                    model.addRow(new Object[]{"", "", "", "", "", ""}); // 빈 줄 추가
                }
                String courseName = courseNameMap.getOrDefault(grade[1], "Unknown Course");
                model.addRow(new Object[]{grade[0], grade[1], courseName, grade[3], grade[4], grade[5]});
            }

            double gpa = totalGradePoints / totalCredits;
            updateStudentGrade(studentId, gpa);
        }
    }

    private Map<String, String> loadCourseNames() {
        Map<String, String> courseNameMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    courseNameMap.put(data[1], data[0]); // 강의 코드 -> 강의 이름 매핑
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseNameMap;
    }

    private void updateStudentGrade(String studentId, double gpa) {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("students_temp.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(studentId)) {
                    data[data.length - 1] = String.valueOf(gpa);
                    line = String.join(",", data);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File originalFile = new File("students.txt");
        File tempFile = new File("students_temp.txt");
        if (originalFile.delete()) {
            tempFile.renameTo(originalFile);
        }
    }

    public void showCounselingRegistration(String userId) {
        JFrame frame = new JFrame("상담 신청");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JLabel counselingLabel = new JLabel("상담신청 내역:");
        counselingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(counselingLabel, BorderLayout.NORTH);

        JTable counselingTable = new JTable();
        DefaultTableModel counselingModel = new DefaultTableModel();
        counselingModel.addColumn("상태");
        counselingModel.addColumn("교수명");
        counselingModel.addColumn("상담 학기");
        counselingModel.addColumn("상담 제목");
        counselingModel.addColumn("상담 내용");
        counselingTable.setModel(counselingModel);
        JScrollPane counselingScrollPane = new JScrollPane(counselingTable);
        panel.add(counselingScrollPane, BorderLayout.CENTER);

        JLabel noCounselingLabel = new JLabel("상담신청 내역이 없습니다.", SwingConstants.CENTER);
        noCounselingLabel.setVisible(false);
        panel.add(noCounselingLabel, BorderLayout.SOUTH);

        loadCounselingData(userId, counselingModel, noCounselingLabel);

        JButton registerButton = new JButton("상담 신청");
        panel.add(registerButton, BorderLayout.SOUTH);
        registerButton.addActionListener(e -> showCounselingForm(userId, counselingModel, noCounselingLabel));

        frame.setVisible(true);
    }

    private void loadCounselingData(String userId, DefaultTableModel model, JLabel noCounselingLabel) {
        model.setRowCount(0); // Clear the table
        boolean hasCounseling = false;
        try (BufferedReader br = new BufferedReader(new FileReader("counsel.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[0].equals(userId)) {
                    model.addRow(new Object[]{data[1], data[2], data[3], data[4], data[5]});
                    hasCounseling = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        noCounselingLabel.setVisible(!hasCounseling);
    }

    private void showCounselingForm(String userId, DefaultTableModel model, JLabel noCounselingLabel) {
        JFrame formFrame = new JFrame("상담 신청");
        formFrame.setSize(400, 300);
        formFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10)); // 행 간격 조정
        formFrame.add(panel, BorderLayout.CENTER);

        panel.add(new JLabel("교수명:"));
        JComboBox<String> professorComboBox = new JComboBox<>();
        try (BufferedReader br = new BufferedReader(new FileReader("professors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                professorComboBox.addItem(data[1]); // 교수 이름을 콤보박스에 추가
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel.add(professorComboBox);

        panel.add(new JLabel("상담 학기:"));
        JLabel semesterLabel = new JLabel("2024-1");
        panel.add(semesterLabel);

        panel.add(new JLabel("상담 제목:"));
        JTextField titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("상담 내용:"));
        JTextArea contentArea = new JTextArea(5, 30); // 상담 내용 입력 칸을 크게 조정
        panel.add(new JScrollPane(contentArea));

        JButton submitButton = new JButton("신청하기");
        panel.add(submitButton);
        submitButton.addActionListener(e -> {
            String professorName = (String) professorComboBox.getSelectedItem();
            String title = titleField.getText();
            String content = contentArea.getText();
            if (professorName == null || title.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(formFrame, "내용이 입력되지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("counsel.txt", true))) {
                bw.write(userId + "," + professorName + ",2024-1," + title + "," + content);
                bw.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            formFrame.dispose();
            loadCounselingData(userId, model, noCounselingLabel); // 새로고침
        });

        formFrame.setVisible(true);
    }
}
