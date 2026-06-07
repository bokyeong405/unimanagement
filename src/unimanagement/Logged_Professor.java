package unimanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logged_Professor extends Logged {

    public void showCourseManagement(String userId) {
        JFrame frame = new JFrame("강의 관리");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("강의명");
        model.addColumn("강의 코드");
        model.addColumn("학점");
        model.addColumn("수강 인원");
        model.addColumn("강의 시간");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadCourseData(userId, model);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void loadCourseData(String userId, DefaultTableModel model) {
       
        String professorName = getProfessorNameById(userId);
        if (professorName == null) {
            JOptionPane.showMessageDialog(null, "교수 정보를 찾을 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[3].equals(professorName)) {
                    model.addRow(new Object[]{data[0], data[1], data[2], data[4], data[5]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getProfessorNameById(String userId) {
        try (BufferedReader br = new BufferedReader(new FileReader("professors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    return data[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showGradeInput(String userId) {
        JFrame frame = new JFrame("성적 입력");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JComboBox<String> courseTypeComboBox = new JComboBox<>(new String[]{"전공", "교양"});
        JComboBox<String> majorComboBox = new JComboBox<>(new String[]{
                "선택", "전자공학과", "소프트웨어학과", "기계공학과", "화학공학과",
                "건축학과", "생명과학과", "물리학과", "수학과",
                "경영학과", "경제학과"});
        JComboBox<String> courseComboBox = new JComboBox<>();

        majorComboBox.setVisible(false);
        courseComboBox.setVisible(false);

        courseTypeComboBox.addActionListener(e -> {
            String selectedType = (String) courseTypeComboBox.getSelectedItem();
            if ("전공".equals(selectedType)) {
                majorComboBox.setVisible(true);
            } else {
                majorComboBox.setVisible(false);
                updateCourseComboBox(selectedType, "", courseComboBox, userId);
            }
        });

        majorComboBox.addActionListener(e -> {
            if (majorComboBox.getSelectedItem() != null) {
                String selectedType = (String) courseTypeComboBox.getSelectedItem();
                String selectedMajor = (String) majorComboBox.getSelectedItem();
                updateCourseComboBox(selectedType, selectedMajor, courseComboBox, userId);
            }
        });

        panel.add(courseTypeComboBox);
        panel.add(majorComboBox);
        panel.add(courseComboBox);

        frame.add(panel, BorderLayout.NORTH);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("이름");
        model.addColumn("학번");
        model.addColumn("소속학과");
        model.addColumn("이메일");
        model.addColumn("성적");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        courseComboBox.addActionListener(e -> {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            if (selectedCourse != null) {
                loadEnrollmentData(selectedCourse, model);
            }
        });

        JButton inputGradeButton = new JButton("입력");
        inputGradeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String studentId = model.getValueAt(selectedRow, 1).toString();
                String courseCode = getCourseCodeByName(courseComboBox.getSelectedItem().toString());
                showGradeInputDialog(studentId, courseCode, model, selectedRow);
            }
        });

        frame.add(inputGradeButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void updateCourseComboBox(String selectedType, String selectedMajor, JComboBox<String> courseComboBox, String userId) {
        courseComboBox.removeAllItems();
        courseComboBox.setVisible(true);

        String professorName = getProfessorNameById(userId);
        if (professorName == null) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                boolean shouldAdd = "전공".equals(selectedType) && (selectedMajor.equals("선택") || getMajorCode(selectedMajor).equals(data[1].substring(0, 2))) ||
                                    "교양".equals(selectedType) && "00".equals(data[1].substring(0, 2));
                if (shouldAdd && data[3].equals(professorName)) {
                    courseComboBox.addItem(data[0] + " (" + data[1] + ")");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCourseCodeByName(String courseName) {
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (courseName.equals(data[0] + " (" + data[1] + ")")) {
                    return data[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                        model.addRow(new Object[]{studentData[1], studentData[8], studentData[6], studentData[5], data[3]});
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

    private void showGradeInputDialog(String studentId, String courseCode, DefaultTableModel model, int rowIndex) {
        String grade = JOptionPane.showInputDialog("성적을 입력하세요:");
        if (grade != null && !grade.trim().isEmpty()) {
            updateGradeInEnrollment(studentId, courseCode, grade);
            model.setValueAt(grade, rowIndex, 4); // 성적 열 업데이트
        }
    }

    private void updateGradeInEnrollment(String studentId, String courseCode, String grade) {
        File inputFile = new File("enrollment.txt");
        File tempFile = new File("enrollment_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(studentId) && data[2].equals(courseCode)) {
                    data[3] = grade; // 성적 업데이트
                    line = String.join(",", data);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    public void showCounselingCheck(String userId) {
        JFrame frame = new JFrame("상담 확인");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String professorName = getProfessorNameById(userId);
        if (professorName == null) {
            JOptionPane.showMessageDialog(frame, "교수 정보를 찾을 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] columnNames = {"상태", "학생명", "상담 시기", "상담 제목", "상담 내용"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadCounselingData(professorName, model);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void loadCounselingData(String professorName, DefaultTableModel model) {
        try (BufferedReader br = new BufferedReader(new FileReader("counsel.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(professorName)) {
                    String status = (data.length > 5 && !data[5].isEmpty()) ? "완료" : "신청";
                    model.addRow(new Object[]{status, data[0], data[2], data[3], data[4]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMajorCode(String major) {
        switch (major) {
            case "전자공학과": return "01";
            case "소프트웨어학과": return "02";
            case "기계공학과": return "03";
            case "화학공학과": return "04";
            case "건축학과": return "05";
            case "생명과학과": return "06";
            case "물리학과": return "07";
            case "수학과": return "08";
            case "경영학과": return "09";
            case "경제학과": return "10";
            default: return "";
        }
    }
}
