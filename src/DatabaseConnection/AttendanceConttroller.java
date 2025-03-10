package DatabaseConnection;
import java.sql.*;
public class AttendanceConttroller {
    // CREATE
    public static void markAttendance(int studentId, int courseId, String status) {
        String sql = "INSERT INTO Attendance (student_id, course_id, date, status) VALUES (?, ?, CURDATE(), ?)";
        try (Connection conn = DatabaseConnect.DbConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setString(3, status);
            pstmt.executeUpdate();
            System.out.println("Attendance marked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public static void getAttendance() {
        String sql = "SELECT * FROM Attendance";
        try (Connection conn = DatabaseConnect.DbConnect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("attendance_id") + " - Student: " + rs.getInt("student_id") + " - Course: " + rs.getInt("course_id") + " - Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
