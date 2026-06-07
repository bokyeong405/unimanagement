package unimanagement;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Management {
    protected ArrayList<Student> students;
    protected ArrayList<Professor> professors;
    protected ArrayList<Employee> employees;
    protected ArrayList<Course> majorCourses;
    protected ArrayList<Course> nonMajorCourses;

    public Management() {
        students = new ArrayList<>();
        professors = new ArrayList<>();
        employees = new ArrayList<>();
        majorCourses = new ArrayList<>();
        nonMajorCourses = new ArrayList<>();
    }

    public void loadData() {
        loadStudents();
        loadProfessors();
        loadEmployees();
        majorCourses.addAll(Course.loadMajorCourses("courses.txt"));
        nonMajorCourses.addAll(Course.loadNonMajorCourses("courses.txt"));
    }

    public void saveData() {
        saveStudents();
        saveProfessors();
        saveEmployees();
        Course.saveCourses("courses.txt", majorCourses);
        Course.saveCourses("courses.txt", nonMajorCourses);
    }

    public void loadStudents() {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                String name = data[1];
                String password = data[2];
                String gender = data[3];
                String phoneNumber = data[4];
                String email = data[5];
                String department = data[6];
                int completedSemesters = Integer.parseInt(data[7]);
                int studentId = Integer.parseInt(data[8]);
                int enrollmentYear = Integer.parseInt(data[9]);
                int leaveOfAbsenceSemesters = Integer.parseInt(data[10]);
                double previousGrade = Double.parseDouble(data[11]);

                Student student = new Student(id, name, password, gender, phoneNumber, email, department, completedSemesters, studentId, enrollmentYear, leaveOfAbsenceSemesters, previousGrade);
                students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveStudents() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt", true))) {
            for (Student student : students) {
                bw.write(student.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadProfessors() {
        try (BufferedReader br = new BufferedReader(new FileReader("professors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Professor professor = new Professor(data[0], data[1], data[2], data[3], data[4], data[5], data[6], Integer.parseInt(data[7]));
                professors.add(professor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveProfessors() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("professors.txt", true))) {
            for (Professor professor : professors) {
                bw.write(professor.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadEmployees() {
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee(data[0], data[1], data[2], data[3], data[4], data[5], data[6], Integer.parseInt(data[7]));
                employees.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveEmployees() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt", true))) {
            for (Employee employee : employees) {
                bw.write(employee.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void screen() {
        LogIn logIn = new LogIn();
        logIn.showLoginUI();
    }

    public static void main(String[] args) {
        Management management = new Management();
        management.loadData();
        management.screen();
    }
}
