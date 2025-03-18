package DatabaseConnection;
import Classes.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseController {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Create a new course
    public void createCourse(Course course) {
        String sql = "INSERT INTO Courses (id, name, semester) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getId());
            stmt.setString(2, course.getName());
            stmt.setInt(3, course.getSemester());
            stmt.executeUpdate();
            System.out.println("Course added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a course by ID
    public Course getCourse(String id) {
        String sql = "SELECT * FROM Courses WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getString("id"));
                course.setName(rs.getString("name"));
                course.setSemester(rs.getInt("semester"));
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getString("id"));
                course.setName(rs.getString("name"));
                course.setSemester(rs.getInt("semester"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Update a course
    public void updateCourse(Course course) {
        String sql = "UPDATE Courses SET name = ?, semester = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getName());
            stmt.setInt(2, course.getSemester());
            stmt.setString(3, course.getId());
            stmt.executeUpdate();
            System.out.println("Course updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a course by ID
    public void deleteCourse(String id) {
        String sql = "DELETE FROM Courses WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
            System.out.println("Course deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
