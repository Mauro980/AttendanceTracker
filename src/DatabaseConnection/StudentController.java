package DatabaseConnection;

import java.sql.*;

import static javax.management.remote.JMXConnectorFactory.connect;

public class StudentController {
    public static void addStudent(String firstName, String lastName, String email, String phone, String enrollmentDate) {
        String sql = "INSERT INTO Students (first_name, last_name, email, phone, enrollment_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.DbConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, enrollmentDate);
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Get all students
    public static void getStudents() {
        String sql = "SELECT * FROM Students";
        try (Connection conn = DatabaseConnect.DbConnect();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + " - " + rs.getString("first_name") + " " + rs.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE: Update a student's email
    public static void updateStudentEmail(int studentId, String newEmail) {
        String sql = "UPDATE Students SET email = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnect.DbConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " student(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE: Delete a student
    public static void deleteStudent(int studentId) {
        String sql = "DELETE FROM Students WHERE student_id = ?";
        try (Connection conn = DatabaseConnect.DbConnect()
             ; PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " student(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
