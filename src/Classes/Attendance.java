package Classes;

public class Attendance {
    private String attendanceId;
    private String courseId;
    private String teacherId;
    private String date;

    public Attendance(String attendanceId, String courseId, String teacherId, String date) {
        this.attendanceId = attendanceId;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.date = date;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
