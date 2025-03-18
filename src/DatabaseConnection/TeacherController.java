package DatabaseConnection;

import Classes.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherController {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // ✅ CREATE: Add a new teacher
    public static void addTeacher(Teacher teacher) {
        String sql = "INSERT INTO Teachers (name, email, password, department, qualification) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getEmail());
            stmt.setString(3, teacher.getPassword());
            stmt.setString(4, teacher.getDepartment());
            stmt.setString(5, teacher.getQualification());

            stmt.executeUpdate();
            System.out.println("Teacher added successfully!");

            // Retrieve auto-generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                teacher.setUserID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ READ: Retrieve a teacher by ID
    public Teacher getTeacher(int id) {
        String sql = "SELECT * FROM Teachers WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("department"),
                        rs.getString("qualification")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ READ: Retrieve all teachers
    public static List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM Teachers";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("department"),
                        rs.getString("qualification")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // ✅ UPDATE: Modify teacher details
    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE Teachers SET name = ?, email = ?, password = ?, department = ?, qualification = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getEmail());
            stmt.setString(3, teacher.getPassword());
            stmt.setString(4, teacher.getDepartment());
            stmt.setString(5, teacher.getQualification());
            stmt.setInt(6, teacher.getUserID());

            stmt.executeUpdate();
            System.out.println("Teacher updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ DELETE: Remove a teacher by ID
    public void deleteTeacher(int id) {
        String sql = "DELETE FROM Teachers WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Teacher deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
