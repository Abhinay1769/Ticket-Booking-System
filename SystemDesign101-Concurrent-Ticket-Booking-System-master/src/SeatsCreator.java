import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeatsCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/TicketBookingSystem"; // Database URL
    private static final String USER = "root"; // MySQL username
    private static final String PASSWORD = "YourPASS"; // MySQL password

    public static void main(String[] args) {
        // Step 1: Connect to the MySQL database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO seats (seat_name, trip_id) VALUES (?, ?)")) {
             
            System.out.println("Connected to the database!");

            // Step 2: Insert 100 seats for trip_id = 1
            for (int row = 1; row <= 10; row++) { // 10 rows
                for (char col = 'A'; col <= 'J'; col++) { // 10 seats per row (A-J)
                    String seatName = row + String.valueOf(col); // Seat name like "1A", "1B", ..., "10J"
                    preparedStatement.setString(1, seatName); // Set the seat name
                    preparedStatement.setInt(2, 1); // Set trip_id = 1 (assuming one flight)
                    preparedStatement.executeUpdate(); // Execute the insert statement
                    System.out.println("Inserted seat: " + seatName);
                }
            }

            System.out.println("100 seats inserted successfully!");

        } catch (SQLException e) {
            // Handle SQL exceptions (like database connection failure)
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            e.printStackTrace();
        }
    }
}
