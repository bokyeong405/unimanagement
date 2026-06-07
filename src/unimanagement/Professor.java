package unimanagement;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Member {
    private int employeeId;
    private List<Course> courses;

    public Professor(String id, String name, String password, String gender, String phoneNumber, String email, String department, int employeeId) {
        super(id, name, password, gender, phoneNumber, email, department);
        this.employeeId = employeeId;
        this.courses = new ArrayList<>();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void assignGrade(Student student, Course course, double grade) {
        for (Course c : courses) {
            if (c.equals(course)) {
                c.assignGrade(student, grade);
                break;
            }
        }
    }

    public String toCSV() {
        return String.join(",", getId(), getName(), getPassword(), getGender(), getPhoneNumber(), getEmail(), getDepartment(), String.valueOf(employeeId));
    }
}
