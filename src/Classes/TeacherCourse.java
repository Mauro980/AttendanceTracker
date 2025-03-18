package Classes;

public class TeacherCourse {
    private int teacherID;
    private String courseID;

    public int getTeacherID() {
        return teacherID;
    }

    public TeacherCourse(String courseID, int teacherID) {
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
}
