package DatabaseConnection;

import Classes.AttendanceStudent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceStudentController {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Create: Add a new attendance record for a student
    public static void addAttendance(AttendanceStudent attendanceStudent) {
        String sql = "INSERT INTO AttendanceStudent (attendanceId, studentId, present) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendanceStudent.getAttendanceId());
            stmt.setString(2, attendanceStudent.getStudentId());
            stmt.setBoolean(3, attendanceStudent.isPresent());
            stmt.executeUpdate();
            System.out.println("Attendance record added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a student's attendance record
    public AttendanceStudent getAttendance(String attendanceId, String studentId) {
        String sql = "SELECT * FROM AttendanceStudent WHERE attendanceId = ? AND studentId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendanceId);
            stmt.setString(2, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AttendanceStudent(
                        rs.getString("attendanceId"),
                        rs.getString("studentId"),
                        rs.getBoolean("present")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all attendance records
    public static List<AttendanceStudent> getAllAttendanceRecords() {
        List<AttendanceStudent> attendanceRecords = new ArrayList<>();
        String sql = "SELECT * FROM AttendanceStudent";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                attendanceRecords.add(new AttendanceStudent(
                        rs.getString("attendanceId"),
                        rs.getString("studentId"),
                        rs.getBoolean("present")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceRecords;
    }

    // Update: Modify attendance status for a student
    public void updateAttendance(String attendanceId, String studentId, boolean present) {
        String sql = "UPDATE AttendanceStudent SET present = ? WHERE attendanceId = ? AND studentId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, present);
            stmt.setString(2, attendanceId);
            stmt.setString(3, studentId);
            stmt.executeUpdate();
            System.out.println("Attendance record updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete: Remove an attendance record
    public void deleteAttendance(String attendanceId, String studentId) {
        String sql = "DELETE FROM AttendanceStudent WHERE attendanceId = ? AND studentId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendanceId);
            stmt.setString(2, studentId);
            stmt.executeUpdate();
            System.out.println("Attendance record deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
