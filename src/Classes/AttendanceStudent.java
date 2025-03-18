package Classes;

public class AttendanceStudent {
    private String attendanceId;
    private String studentId;
    private boolean present;

    public AttendanceStudent(String attendanceId, String studentId, boolean present) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.present = present;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

}

