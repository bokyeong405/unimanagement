package unimanagement;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Logged_Info {

    public void showStudentInfo(String userId) {
        JFrame infoFrame = new JFrame("학생 정보");
        infoFrame.setSize(400, 400);
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));
        infoFrame.add(panel);

        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    panel.add(new JLabel("이름:"));
                    panel.add(new JLabel(data[1]));

                    panel.add(new JLabel("성별:"));
                    panel.add(new JLabel(data[3]));

                    panel.add(new JLabel("전화번호:"));
                    panel.add(new JLabel(data[4]));

                    panel.add(new JLabel("이메일:"));
                    panel.add(new JLabel(data[5]));

                    panel.add(new JLabel("소속학과:"));
                    panel.add(new JLabel(data[6]));

                    panel.add(new JLabel("이수 학기:"));
                    panel.add(new JLabel(data[7]));

                    panel.add(new JLabel("학번:"));
                    panel.add(new JLabel(data[8]));

                    panel.add(new JLabel("입학년도:"));
                    panel.add(new JLabel(data[9]));

                    panel.add(new JLabel("휴학 학기:"));
                    panel.add(new JLabel(data[10]));

                    panel.add(new JLabel("지난 성적:"));
                    panel.add(new JLabel(data[11]));

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        infoFrame.setVisible(true);
    }

    public void showProfessorInfo(String userId) {
        JFrame infoFrame = new JFrame("교수 정보");
        infoFrame.setSize(400, 400);
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));
        infoFrame.add(panel);

        try (BufferedReader br = new BufferedReader(new FileReader("professors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    panel.add(new JLabel("이름:"));
                    panel.add(new JLabel(data[1]));

                    panel.add(new JLabel("성별:"));
                    panel.add(new JLabel(data[3]));

                    panel.add(new JLabel("전화번호:"));
                    panel.add(new JLabel(data[4]));

                    panel.add(new JLabel("이메일:"));
                    panel.add(new JLabel(data[5]));

                    panel.add(new JLabel("소속학과:"));
                    panel.add(new JLabel(data[6]));

                    panel.add(new JLabel("사번:"));
                    panel.add(new JLabel(data[7]));

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        infoFrame.setVisible(true);
    }

    public void showEmployeeInfo(String userId) {
        JFrame infoFrame = new JFrame("직원 정보");
        infoFrame.setSize(400, 400);
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));
        infoFrame.add(panel);

        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(userId)) {
                    panel.add(new JLabel("이름:"));
                    panel.add(new JLabel(data[1]));

                    panel.add(new JLabel("성별:"));
                    panel.add(new JLabel(data[3]));

                    panel.add(new JLabel("전화번호:"));
                    panel.add(new JLabel(data[4]));

                    panel.add(new JLabel("이메일:"));
                    panel.add(new JLabel(data[5]));

                    panel.add(new JLabel("소속대학:"));
                    panel.add(new JLabel(data[6]));

                    panel.add(new JLabel("사번:"));
                    panel.add(new JLabel(data[7]));

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        infoFrame.setVisible(true);
    }
}
