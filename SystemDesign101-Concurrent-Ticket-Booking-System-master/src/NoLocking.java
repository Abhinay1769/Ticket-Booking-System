import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoLocking {
    static final String URL = "jdbc:mysql://localhost:3306/TicketBookingSystem";
    static final String USER = "root";
    static final String PASSWORD = "YourPASS"; // Update with your MySQL password

    // Book a seat for a user
    public static boolean bookSeat(int userId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start a transaction
            connection.setAutoCommit(false);
            String query = "SELECT seat_id, seat_name FROM seats WHERE user_id IS NULL LIMIT 1 FOR UPDATE"; // Lock the row
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    int seatId = resultSet.getInt("seat_id");
                    String seatName = resultSet.getString("seat_name");

                    // Book the seat
                    String updateSQL = "UPDATE seats SET user_id = ? WHERE seat_id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                        updateStmt.setInt(1, userId);
                        updateStmt.setInt(2, seatId);
                        updateStmt.executeUpdate();
                        System.out.println("User  " + userId + " was assigned the seat " + seatName);
                        connection.commit(); // Commit the transaction
                        return true;
                    }
                } else {
                    return false; // No available seats
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback in case of an error
                System.err.println("Error booking seat for user " + userId + ": " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
        return false;
    }

    // Reset user_id to NULL after all seat bookings
    public static void resetUser Ids() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String resetSQL = "UPDATE seats SET user_id = NULL";
            try (PreparedStatement resetStmt = connection.prepareStatement(resetSQL)) {
                resetStmt.executeUpdate();
                System.out.println("All user IDs have been reset to NULL.");
            }
        } catch (SQLException e) {
            System.err.println("Error resetting user IDs: " + e.getMessage());
        }
    }

    // Display the 10x10 seat grid
    public static void displaySeatGrid() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT seat_name, user_id FROM seats";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet resultSet = stmt.executeQuery();
                System.out.println("\nSeat Grid (X = Booked, - = Empty):");

                // Create a 10x10 grid of seats
                int counter = 0;
                while (resultSet.next()) {
                    String seatName = resultSet.getString("seat_name");
                    Integer userId = resultSet.getObject("user_id", Integer.class);
                    String seatStatus = (userId == null) ? "-" : "X"; // Empty seat or booked seat

                    // Print each seat in the grid, formatted with spaces between
                    System.out.print(seatStatus + " ");
                    counter++;

                    // Print a new line after every 10 seats
                    if (counter % 10 == 0) {
                        System.out.println();
                    }
                }
                System.out.println(); // Newline for better readability
            }
        } catch (SQLException e) {
            System.err.println("Error displaying seat grid: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Executor service to simulate concurrent booking
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 1; i <= 100; i++) {
            final int userId = i;
            executorService.submit(() -> bookSeat(userId)); // No concurrency handling
        }
        executorService.shutdown();

        // Wait for all tasks to complete before displaying the grid
        while (!executorService.isTerminated()) {
            // Waiting for all tasks to finish
        }

        // Display seat grid before resetting
        displaySeatGrid();

        // After all threads have completed, reset the user_ids
        resetUser Ids();  // Reset user IDs to NULL after all tasks have completed

        // Display seat grid after resetting user IDs
        displaySeatGrid();
    }
}
