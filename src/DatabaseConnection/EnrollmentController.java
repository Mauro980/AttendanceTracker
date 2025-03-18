package DatabaseConnection;

import Classes.Enrollment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EnrollmentController {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Create a new enrollment record
    public static void enrollStudent(Enrollment enrollment) {
        String sql = "INSERT INTO Enrollment (enrollmentId, studentId, courseId, enrollmentDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enrollment.getEnrollmentId());
            stmt.setString(2, enrollment.getStudentId());
            stmt.setString(3, enrollment.getCourseId());
            stmt.setString(4, enrollment.getEnrollmentDate());
            stmt.executeUpdate();
            System.out.println("Student enrolled successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all enrollments
    public static List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollment";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                enrollments.add(new Enrollment(
                        rs.getString("enrollmentId"),
                        rs.getString("studentId"),
                        rs.getString("courseId"),
                        rs.getString("enrollmentDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    // Get enrollments for a specific student
    public List<Enrollment> getEnrollmentsByStudent(String studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollment WHERE studentId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                enrollments.add(new Enrollment(
                        rs.getString("enrollmentId"),
                        rs.getString("studentId"),
                        rs.getString("courseId"),
                        rs.getString("enrollmentDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    // Get enrollments for a specific course
    public List<Enrollment> getEnrollmentsByCourse(String courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollment WHERE courseId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                enrollments.add(new Enrollment(
                        rs.getString("enrollmentId"),
                        rs.getString("studentId"),
                        rs.getString("courseId"),
                        rs.getString("enrollmentDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    // Delete an enrollment
    public static void deleteEnrollment(String enrollmentId) {
        String sql = "DELETE FROM Enrollment WHERE enrollmentId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enrollmentId);
            stmt.executeUpdate();
            System.out.println("Enrollment deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
