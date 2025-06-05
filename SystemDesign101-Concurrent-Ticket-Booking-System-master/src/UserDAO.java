import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/TicketBookingSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "YourPASS"; // Replace with your MySQL password

    // Create: Add a new user
    public boolean addUser(String userName) {
        String sql = "INSERT INTO users (user_name) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    // Read: Get user by ID
    public String getUserById(int userId) {
        String sql = "SELECT user_name FROM users WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_name");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    // Read: Get all users
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT user_id, user_name FROM users ORDER BY user_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                users.add("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
        return users;
    }

    // Update: Update a user's name by ID
    public boolean updateUserName(int userId, String newUserName) {
        String sql = "UPDATE users SET user_name = ? WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUserName);
            pstmt.setInt(2, userId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // Delete: Delete a user by ID
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // Main method for quick testing
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        System.out.println("Adding user 'Test User': " + userDAO.addUser("Test User"));

        System.out.println("All Users:");
        for (String user : userDAO.getAllUsers()) {
            System.out.println(user);
        }

        int testUserId = 1;
        System.out.println("User with ID " + testUserId + ": " + userDAO.getUserById(testUserId));

        System.out.println("Updating user ID " + testUserId + " to 'Updated User': " + userDAO.updateUserName(testUserId, "Updated User"));

        System.out.println("Deleting user ID " + testUserId + ": " + userDAO.deleteUser(testUserId));
    }
}

