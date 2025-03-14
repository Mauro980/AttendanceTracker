package Classes;

public class Teacher extends User{
    private int teacherID;

    public Teacher(int userID, String name, String password, int teacherID) {
        super(userID, name, "","Teacher", password);
        this.teacherID = teacherID;
    }


    public void login() {
        System.out.println("Teacher " + name + " has logged in.");
    }

    public void takeAttendance() {
        System.out.println(name + " is taking attendance.");
    }

    public void viewReports() {
        System.out.println(name + " is viewing reports.");
    }
}
