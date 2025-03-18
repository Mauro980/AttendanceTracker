package DatabaseConnection;

import Classes.TeacherCourse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherCourseController {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // ✅ CREATE: Assign a teacher to a course
    public static void assignTeacherToCourse(TeacherCourse teacherCourse) {
        String sql = "INSERT INTO TeacherCourse (teacher_id, course_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherCourse.getTeacherID());
            stmt.setString(2, teacherCourse.getCourseID());
            stmt.executeUpdate();
            System.out.println("Teacher assigned to course successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ READ: Retrieve all courses assigned to a teacher
    public static List<TeacherCourse> getCoursesByTeacher(int teacherID) {
        List<TeacherCourse> teacherCourses = new ArrayList<>();
        String sql = "SELECT * FROM TeacherCourse WHERE teacher_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teacherCourses.add(new TeacherCourse(
                        rs.getString("course_id"),
                        rs.getInt("teacher_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherCourses;
    }

    // ✅ READ: Retrieve all teacher-course assignments
    public static List<TeacherCourse> getAllTeacherCourses() {
        List<TeacherCourse> teacherCourses = new ArrayList<>();
        String sql = "SELECT * FROM TeacherCourse";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                teacherCourses.add(new TeacherCourse(
                        rs.getString("course_id"),
                        rs.getInt("teacher_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherCourses;
    }

    // ✅ UPDATE: Reassign a teacher to a different course
    public void updateTeacherCourse(TeacherCourse teacherCourse, String newCourseID) {
        String sql = "UPDATE TeacherCourse SET course_id = ? WHERE teacher_id = ? AND course_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newCourseID);
            stmt.setInt(2, teacherCourse.getTeacherID());
            stmt.setString(3, teacherCourse.getCourseID());
            stmt.executeUpdate();
            System.out.println("Teacher reassigned to new course successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ DELETE: Remove a teacher-course assignment
    public void removeTeacherFromCourse(int teacherID, String courseID) {
        String sql = "DELETE FROM TeacherCourse WHERE teacher_id = ? AND course_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherID);
            stmt.setString(2, courseID);
            stmt.executeUpdate();
            System.out.println("Teacher removed from course successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
