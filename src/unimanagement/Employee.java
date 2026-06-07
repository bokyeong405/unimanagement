package unimanagement;

public class Employee extends Member {
    private int employeeId;

    public Employee(String id, String name, String password, String gender, String phoneNumber, String email, String department, int employeeId) {
        super(id, name, password, gender, phoneNumber, email, department);
        this.employeeId = employeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    
    public int getYearsOfService() {
        // 근속연수 계산 로직을 작성, 예시: 입사 연도를 가지고 현재 연도와 비교하여 근속연수를 계산합니다.
        int hireYear = 2015; // 실제 코드에서는 이 값을 읽어옵니다.
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return currentYear - hireYear;
    }

    public String toCSV() {
        return String.join(",", getId(), getName(), getPassword(), getGender(), getPhoneNumber(), getEmail(), getDepartment(), String.valueOf(employeeId));
    }
    
}
