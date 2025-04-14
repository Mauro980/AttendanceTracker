package Classes;

public class Teacher extends User {
    private String department;
    private String qualification;

    public Teacher(int userID, String name, String email, String password, String department, String qualification) {
        super(userID, name, email, "Teacher", password); // Role is set as "Teacher"
        this.department = department;
        this.qualification = qualification;
    }

    public Teacher(String name, String email, String role, String password) {
        super(name, email, role, password);
    }

    public Teacher(String fullName, String email, String pass, String department, String s) {
        super();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return name + " (" + department + ")"; // Display name in JComboBox
    }
}

