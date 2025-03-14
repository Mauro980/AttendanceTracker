package DatabaseConnection;

import Classes.User;

import java.sql.*;

public class UserController {
    public void createUser(User user) {
        String sql = "INSERT INTO Users (userID, name, email, role, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.DbConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getPassword());
            stmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a user by ID
    public User getUser(int userID) {
        String sql = "SELECT * FROM Users WHERE userID = ?";
        try (Connection conn = DatabaseConnect.DbConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("userID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update user details
    public void updateUser(User user) {
        String sql = "UPDATE Users SET name = ?, email = ?, role = ?, password = ? WHERE userID = ?";
        try (Connection conn = DatabaseConnect.DbConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getUserID());
            stmt.executeUpdate();
            System.out.println("User updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a user by ID
    public void deleteUser(int userID) {
        String sql = "DELETE FROM Users WHERE userID = ?";
        try (Connection conn = DatabaseConnect.DbConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.executeUpdate();
            System.out.println("User deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
