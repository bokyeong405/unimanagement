package unimanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String courseCode;
    private int credits;
    private String professorName;
    private int maxEnrollment;
    private String time;

    public Course(String courseName, String courseCode, int credits, String professorName, int maxEnrollment, String time) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.professorName = professorName;
        this.maxEnrollment = maxEnrollment;
        this.time = time;
    }

    public static List<Course> loadCourses(String fileName) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    Course course = new Course(data[0], data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), data[5]);
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static List<Course> loadMajorCourses(String fileName) {
        List<Course> courses = loadCourses(fileName);
        List<Course> majorCourses = new ArrayList<>();
        for (Course course : courses) {
            if (!course.getCourseCode().startsWith("00")) {
                majorCourses.add(course);
            }
        }
        return majorCourses;
    }

    public static List<Course> loadNonMajorCourses(String fileName) {
        List<Course> courses = loadCourses(fileName);
        List<Course> nonMajorCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCourseCode().startsWith("00")) {
                nonMajorCourses.add(course);
            }
        }
        return nonMajorCourses;
    }
    
    public void assignGrade(Student student, double grade) {
        student.setGrade(grade);
    }

    public static void saveCourses(String fileName, List<Course> courses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Course course : courses) {
                bw.write(course.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toCSV() {
        return String.join(",", courseName, courseCode, String.valueOf(credits), professorName, String.valueOf(maxEnrollment), time);
    }

    public String getCourseCode() {
        return courseCode;
    }
}
