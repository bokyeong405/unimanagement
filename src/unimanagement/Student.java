package unimanagement;

public class Student extends Member {
    private int completedSemesters;
    private int studentId;
    private int enrollmentYear;
    private int leaveOfAbsenceSemesters;
    private double previousGrade;

    public Student(String id, String name, String password, String gender, String phoneNumber, String email, String department, int completedSemesters, int studentId, int enrollmentYear, int leaveOfAbsenceSemesters, double previousGrade) {
        super(id, name, password, gender, phoneNumber, email, department);
        this.completedSemesters = completedSemesters;
        this.studentId = studentId;
        this.enrollmentYear = enrollmentYear;
        this.leaveOfAbsenceSemesters = leaveOfAbsenceSemesters;
        this.previousGrade = previousGrade;
    }

    public int getCompletedSemesters() {
        return completedSemesters;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public int getLeaveOfAbsenceSemesters() {
        return leaveOfAbsenceSemesters;
    }

    public double getPreviousGrade() {
        return previousGrade;
    }

    public double getGrade() {
        return previousGrade;
    }
    
    public void setGrade(double grade) {
        this.previousGrade = grade;
    }

    @Override
    public String toCSV() {
        return String.join(",", getId(), getName(), getPassword(), getGender(), getPhoneNumber(), getEmail(), getDepartment(), String.valueOf(completedSemesters), String.valueOf(studentId), String.valueOf(enrollmentYear), String.valueOf(leaveOfAbsenceSemesters), String.valueOf(previousGrade));
    }
}
