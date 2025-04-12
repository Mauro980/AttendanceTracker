package Classes;

import java.time.LocalDateTime;

public class Attendance {
    private String attendanceId;
    private String courseId;
    private String teacherId;
    private LocalDateTime date;

    // Constructor accepts LocalDateTime for the date
    public Attendance(String attendanceId, String courseId, String teacherId, LocalDateTime date) {
        this.attendanceId = attendanceId;
        this.courseId = courseId;
        this.teacherId = teacherId;
        // Convert LocalDateTime to String
        this.date = date;
    }

    // Getter and setter for attendanceId
    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    // Getter and setter for courseId
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    // Getter and setter for teacherId
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    // Getter and setter for date
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
