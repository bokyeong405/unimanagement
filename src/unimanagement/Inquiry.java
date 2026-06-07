package unimanagement;

import java.util.ArrayList;

public class Inquiry {
    private ArrayList<Student> students;
    private ArrayList<Professor> professors;
    private ArrayList<Employee> employees;

    public Inquiry(ArrayList<Student> students, ArrayList<Professor> professors, ArrayList<Employee> employees) {
        this.students = students;
        this.professors = professors;
        this.employees = employees;
    }

    public boolean login(String id, String password, String role) {
        if (role.equals("student")) {
            for (Student student : students) {
                if (student.getId().equals(id) && student.getPassword().equals(password)) {
                    return true;
                }
            }
        } else if (role.equals("professor")) {
            for (Professor professor : professors) {
                if (professor.getId().equals(id) && professor.getPassword().equals(password)) {
                    return true;
                }
            }
        } else if (role.equals("employee")) {
            for (Employee employee : employees) {
                if (employee.getId().equals(id) && employee.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean registerStudent(Student student) {
        for (Student s : students) {
            if (s.getId().equals(student.getId())) {
                return false; // Already registered
            }
        }
        students.add(student);
        return true;
    }

    public boolean registerProfessor(Professor professor) {
        for (Professor p : professors) {
            if (p.getId().equals(professor.getId())) {
                return false; // Already registered
            }
        }
        professors.add(professor);
        return true;
    }

    public boolean registerEmployee(Employee employee) {
        for (Employee e : employees) {
            if (e.getId().equals(employee.getId())) {
                return false; // Already registered
            }
        }
        employees.add(employee);
        return true;
    }
}
