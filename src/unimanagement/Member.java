package unimanagement;

public abstract class Member {
    private String id;
    private String name;
    private String password;
    private String gender;
    private String phoneNumber;
    private String email;
    private String department;

    public Member(String id, String name, String password, String gender, String phoneNumber, String email, String department) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toCSV() {
        return String.join(",", id, name, password, gender, phoneNumber, email, department);
    }
}
