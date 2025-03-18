package DatabaseConnection;

import Classes.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Create: Add a new attendance record
    public static void addAttendance(Attendance attendance) {
        String sql = "INSERT INTO Attendance (attendanceId, courseId, teacherId, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendance.getAttendanceId());
            stmt.setString(2, attendance.getCourseId());
            stmt.setString(3, attendance.getTeacherId());
            stmt.setString(4, attendance.getDate());
            stmt.executeUpdate();
            System.out.println("Attendance record added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve an attendance record by ID
    public Attendance getAttendance(String attendanceId) {
        String sql = "SELECT * FROM Attendance WHERE attendanceId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendanceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Attendance(
                        rs.getString("attendanceId"),
                        rs.getString("courseId"),
                        rs.getString("teacherId"),
                        rs.getString("date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all attendance records
    public static List<Attendance> getAllAttendanceRecords() {
        List<Attendance> attendanceRecords = new ArrayList<>();
        String sql = "SELECT * FROM Attendance";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                attendanceRecords.add(new Attendance(
                        rs.getString("attendanceId"),
                        rs.getString("courseId"),
                        rs.getString("teacherId"),
                        rs.getString("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceRecords;
    }

    // Update an attendance record
    public void updateAttendance(Attendance attendance) {
        String sql = "UPDATE Attendance SET courseId = ?, teacherId = ?, date = ? WHERE attendanceId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendance.getCourseId());
            stmt.setString(2, attendance.getTeacherId());
            stmt.setString(3, attendance.getDate());
            stmt.setString(4, attendance.getAttendanceId());
            stmt.executeUpdate();
            System.out.println("Attendance record updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an attendance record
    public void deleteAttendance(String attendanceId) {
        String sql = "DELETE FROM Attendance WHERE attendanceId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendanceId);
            stmt.executeUpdate();
            System.out.println("Attendance record deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
