package DatabaseConnection;
import java.sql.*;
public class CourseController {
    // CREATE
    public static void addCourse(String courseName) {
        String sql = "INSERT INTO Courses (course_name) VALUES (?)";
        try (Connection conn = DatabaseConnect.DbConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseName);
            pstmt.executeUpdate();
            System.out.println("Course added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public static void getCourses() {
        String sql = "SELECT * FROM Courses";
        try (Connection conn = DatabaseConnect.DbConnect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("course_id") + " - " + rs.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteCourse(int courseId) {
        String sql = "DELETE FROM Courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnect.DbConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();
            System.out.println("Course deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
