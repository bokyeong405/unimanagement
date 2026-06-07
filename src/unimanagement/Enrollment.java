package unimanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Enrollment extends Logged {

    private JFrame frame;
    private DefaultListModel<String> selectedCoursesModel;
    private DefaultListModel<String> availableCoursesModel;
    private JComboBox<String> departmentComboBox;
    private JButton filterButton;
    private String studentMajor;
    private final Object lock = new Object(); // 동기화 객체

    public void showCourseRegistration(String userId) {
        studentMajor = getStudentMajor(userId);

        frame = new JFrame("수강신청 시스템");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // 상단 패널 (전공/교양 선택)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel typeLabel = new JLabel("유형 선택:");
        topPanel.add(typeLabel);

        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"전공", "교양"});
        topPanel.add(typeComboBox);

        JLabel departmentLabel = new JLabel("학과 선택:");
        departmentComboBox = new JComboBox<>(new String[]{"전자공학과", "소프트웨어학과", "기계공학과", "화학공학과", "건축학과", "생명과학과", "물리학과", "수학과", "경영학과", "경제학과"});
        topPanel.add(departmentComboBox);

        typeComboBox.addActionListener(e -> {
            if (typeComboBox.getSelectedItem().equals("전공")) {
                departmentComboBox.setVisible(true);
            } else {
                departmentComboBox.setVisible(false);
                availableCoursesModel.clear();
                loadAvailableCourses(typeComboBox.getSelectedItem().toString(), null);
            }
        });

        filterButton = new JButton("과목 조회");
        topPanel.add(filterButton);

        frame.add(topPanel, BorderLayout.NORTH);

        // 중앙 패널 (선택된 과목 목록 및 신청 가능한 과목 목록)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));

        // 신청 가능한 과목 목록
        availableCoursesModel = new DefaultListModel<>();
        JList<String> availableCoursesList = new JList<>(availableCoursesModel);
        JScrollPane availableCoursesScrollPane = new JScrollPane(availableCoursesList);
        centerPanel.add(new JLabel("신청 가능한 과목들:"));
        centerPanel.add(availableCoursesScrollPane);

        // 신청된 과목 목록
        selectedCoursesModel = new DefaultListModel<>();
        JList<String> selectedCoursesList = new JList<>(selectedCoursesModel);
        JScrollPane selectedCoursesScrollPane = new JScrollPane(selectedCoursesList);
        centerPanel.add(new JLabel("현재 신청된 과목들:"));
        centerPanel.add(selectedCoursesScrollPane);

        frame.add(centerPanel, BorderLayout.CENTER);

        // 하단 패널 (추가 및 삭제 버튼)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("추가");
        bottomPanel.add(addButton);

        JButton removeButton = new JButton("삭제");
        bottomPanel.add(removeButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        filterButton.addActionListener(new FilterButtonListener(typeComboBox, departmentComboBox, availableCoursesModel));
        addButton.addActionListener(new AddButtonListener(userId, selectedCoursesModel, availableCoursesList, availableCoursesModel, studentMajor, typeComboBox));
        removeButton.addActionListener(new RemoveButtonListener(userId, selectedCoursesList, selectedCoursesModel));

        loadCurrentEnrollment(userId, selectedCoursesModel);
        frame.setVisible(true);
    }

    private String getStudentMajor(String userId) {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    return data[6]; // 전공 정보를 반환
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadCurrentEnrollment(String userId, DefaultListModel<String> model) {
        model.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("enrollment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 2 && data[0].equals(userId)) {
                    model.addElement(data[1] + " (" + data[2] + ")");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculateCurrentCredits(String userId) {
        int totalCredits = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("enrollment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 2 && data[0].equals(userId)) {
                    totalCredits += getCredits(data[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalCredits;
    }

    private int getCredits(String courseCode) {
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(courseCode)) {
                    return Integer.parseInt(data[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void loadAvailableCourses(String type, String department) {
        availableCoursesModel.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    if (type.equals("전공") && !data[1].startsWith("00")) {
                        if (department == null || department.isEmpty() || data[1].startsWith(getMajorCode(department))) {
                            availableCoursesModel.addElement(data[0] + " (" + data[1] + ")");
                        }
                    } else if (type.equals("교양") && data[1].startsWith("00")) {
                        availableCoursesModel.addElement(data[0] + " (" + data[1] + ")");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isTimeConflict(String userId, String newCourseTime) {
        try (BufferedReader br = new BufferedReader(new FileReader("enrollment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 3 && data[0].equals(userId)) {
                    String existingCourseTime = data[3];
                    if (isTimeOverlap(existingCourseTime, newCourseTime)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isTimeOverlap(String time1, String time2) {
        String[] time1Parts = {time1.substring(0, 2), time1.substring(2)};
        String[] time2Parts = {time2.substring(0, 2), time2.substring(2)};
        for (String part1 : time1Parts) {
            for (String part2 : time2Parts) {
                if (part1.equals(part2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasCourseHistory(String userId, String courseCode) {
        try (BufferedReader br = new BufferedReader(new FileReader("courseHistory.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId) && data[1].equals(courseCode)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAlreadyEnrolled(String userId, String courseCode) {
        try (BufferedReader br = new BufferedReader(new FileReader("enrollment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId) && data[2].equals(courseCode)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getCurrentEnrollmentCount(String courseCode) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("enrollment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[2].equals(courseCode)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int getMaxEnrollment(String courseCode) {
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(courseCode)) {
                    return Integer.parseInt(data[3]); // Assuming the max enrollment is the fourth column
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
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

    private class FilterButtonListener implements ActionListener {
        private JComboBox<String> typeComboBox;
        private JComboBox<String> departmentComboBox;
        private DefaultListModel<String> model;

        public FilterButtonListener(JComboBox<String> typeComboBox, JComboBox<String> departmentComboBox, DefaultListModel<String> model) {
            this.typeComboBox = typeComboBox;
            this.departmentComboBox = departmentComboBox;
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String type = (String) typeComboBox.getSelectedItem();
            String department = (String) departmentComboBox.getSelectedItem();
            loadAvailableCourses(type, department);
        }
    }

    private class AddButtonListener implements ActionListener {
        private String userId;
        private DefaultListModel<String> selectedCoursesModel;
        private JList<String> availableCoursesList;
        private DefaultListModel<String> availableCoursesModel;
        private String studentMajor;
        private JComboBox<String> typeComboBox;

        public AddButtonListener(String userId, DefaultListModel<String> selectedCoursesModel, JList<String> availableCoursesList, DefaultListModel<String> availableCoursesModel, String studentMajor, JComboBox<String> typeComboBox) {
            this.userId = userId;
            this.selectedCoursesModel = selectedCoursesModel;
            this.availableCoursesList = availableCoursesList;
            this.availableCoursesModel = availableCoursesModel;
            this.studentMajor = studentMajor;
            this.typeComboBox = typeComboBox;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                synchronized (lock) {
                    String selectedCourse = availableCoursesList.getSelectedValue();
                    if (selectedCourse != null) {
                        String courseName = selectedCourse.split(" \\(")[0];
                        String courseCode = selectedCourse.split(" \\(")[1].replace(")", "");
                        String courseTime = getCourseTime(courseCode);
                        int currentCredits = calculateCurrentCredits(userId);
                        int courseCredits = getCredits(courseCode);
                        int currentEnrollmentCount = getCurrentEnrollmentCount(courseCode);
                        int maxEnrollment = getMaxEnrollment(courseCode);

                        if (isAlreadyEnrolled(userId, courseCode)) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "이미 신청된 과목입니다."));
                            return;
                        }

                        if (hasCourseHistory(userId, courseCode)) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "수강한 과목입니다."));
                            return;
                        }

                        if (currentCredits + courseCredits > 19) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "최대 신청 가능 학점을 확인하세요."));
                            return;
                        }

                        if (typeComboBox.getSelectedItem().equals("전공") && !courseCode.startsWith(getMajorCode(studentMajor))) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "전공에 해당하는 강의만 신청할 수 있습니다."));
                            return;
                        }

                        if (isTimeConflict(userId, courseTime)) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "시간이 겹치는 강의가 있습니다."));
                            return;
                        }

                        if (currentEnrollmentCount >= maxEnrollment) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "여석이 없습니다."));
                            return;
                        }

                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("enrollment.txt", true))) {
                            bw.write(userId + "," + courseName + "," + courseCode + "," + courseTime);
                            bw.newLine();
                            SwingUtilities.invokeLater(() -> {
                                selectedCoursesModel.addElement(courseName + " (" + courseCode + ")");
                                JOptionPane.showMessageDialog(frame, "수강신청이 완료되었습니다.");
                            });
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    private class RemoveButtonListener implements ActionListener {
        private String userId;
        private JList<String> selectedCoursesList;
        private DefaultListModel<String> model;

        public RemoveButtonListener(String userId, JList<String> selectedCoursesList, DefaultListModel<String> model) {
            this.userId = userId;
            this.selectedCoursesList = selectedCoursesList;
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                synchronized (lock) {
                    String selectedCourse = selectedCoursesList.getSelectedValue();
                    if (selectedCourse != null) {
                        File inputFile = new File("enrollment.txt");
                        File tempFile = new File("enrollment_temp.txt");

                        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (!(data[0].equals(userId) && data[1].equals(selectedCourse.split(" \\(")[0]) && data[2].equals(selectedCourse.split(" \\(")[1].replace(")", "")))) {
                                    bw.write(line);
                                    bw.newLine();
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        if (inputFile.delete()) {
                            tempFile.renameTo(inputFile);
                        }
                        SwingUtilities.invokeLater(() -> {
                            model.removeElement(selectedCourse);
                            JOptionPane.showMessageDialog(frame, "수강신청이 삭제되었습니다.");
                        });
                    }
                }
            }).start();
        }
    }

    private String getCourseTime(String courseCode) {
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(courseCode)) {
                    return data[5]; // 강의 시간을 반환
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
