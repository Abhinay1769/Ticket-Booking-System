#Ticket Booking System

#Overview

The Ticket Booking System is a multi-threaded Java application designed to simulate real-time flight seat bookings while demonstrating the challenges and solutions of managing concurrency in a database environment. Using MySQL as the backend, this system highlights how multiple users can attempt to book seats simultaneously and how proper locking mechanisms can prevent conflicts and ensure data consistency.

#Key Concepts Demonstrated

Concurrency & Multi-threading: Utilizes Java's ExecutorService to simulate 100 concurrent users attempting to book seats.
Database Locking Mechanisms: Shows different approaches — from no locking (worst implementation) to pessimistic locking (using FOR UPDATE) to handle concurrent updates.

#Transaction Management: Demonstrates using SQL transactions (commit & rollback) to maintain data integrity in concurrent operations.

Data Consistency: Highlights how proper locking prevents race conditions, ensuring each seat is booked exactly once.
Project Structure
Class Name

#Description

FlightCreator

Creates the trips table and inserts initial flight data.

SeatsCreator

Creates the seats table and inserts 100 seats for the flight.

UserInserter

Creates the users table and inserts 100 random users.

WorstImplementation

Demonstrates seat booking without any concurrency control, leading to potential conflicts.

NoLocking

Attempts seat booking without locking, showing partial concurrency handling.

TicketBookingSystem

Main application implementing proper concurrency control with pessimistic locking.

Setup Instructions
Prerequisites
Java 8 or higher
MySQL Server installed and running
MySQL JDBC connector added to the classpath
Database Setup
Create the database:

sql
Run
Copy code
CREATE DATABASE TicketBookingSystem;
The application will create necessary tables (trips, seats, and users) automatically upon running their respective classes if they do not already exist.

Running the Project
Follow these steps in the given order:

Create flights

bash
Run
Copy code
javac FlightCreator.java
java FlightCreator
Create and insert seats

bash
Run
Copy code
javac SeatsCreator.java
java SeatsCreator
Create and insert users

bash
Run
Copy code
javac UserInserter.java
java UserInserter
Run worst-case booking implementation (no locking)

bash
Run
Copy code
javac WorstImplementation.java
java WorstImplementation
Run main ticket booking system (with concurrency control)

bash
Run
Copy code
javac TicketBookingSystem.java
java TicketBookingSystem

#What to Expect

The WorstImplementation class will likely demonstrate race conditions, leading to multiple users potentially booking the same seat.
The TicketBookingSystem class will use pessimistic locking to prevent conflicts, ensuring that each seat is booked by only one user.
Both correct and incorrect seat assignments are visualized via printed seat grids (X for booked, - for available).
Execution time for the booking process is displayed for performance insights.

#Troubleshooting

Verify your MySQL server is running and accessible with the credentials provided in the code (URL, USER, PASSWORD).
Ensure all tables are created correctly by running the creation scripts individually, if necessary.
If seat bookings do not behave as expected, confirm that transactions and locking queries are supported by your MySQL version and configuration.
Adjust thread pool size or system resources if performance degrades with 100 concurrent threads.

#Additional Notes

This project focuses solely on the concurrent booking process using Java and MySQL. It does not include a user interface.
Passwords and database connection details are hardcoded for demonstration purposes; consider using configuration files or environment variables for production.
This demo uses basic locking semantics. Real-world systems may require additional optimizations such as optimistic locking, retries, or queuing mechanisms.


#Conclusion

This project elegantly illustrates the importance of handling concurrency and managing database transactions in multi-user environments. By experimenting with variations in locking mechanisms, you gain valuable insights into preventing race conditions and maintaining transactional integrity — key concepts underpinning robust, scalable software systems.
