Ticket Booking System

Project Description

This project is a multi-threaded ticket booking system designed to simulate real-time seat bookings for flights while ensuring data integrity in a concurrent environment. Using Java and MySQL, the system handles seat reservations where users can concurrently attempt to book seats without conflicts.

The core functionality relies on database locking techniques such as UPDATE locks and SKIP LOCKS to manage access to seats, ensuring that once a seat is being booked by one user, it cannot be accessed by others. The project also explores pessimistic locking methods to avoid race conditions and maintain consistency, even under high concurrency.

By simulating a scenario with 100 concurrent users, the system demonstrates the importance of proper locking mechanisms in preventing inconsistencies and ensuring a seamless user experience.


Files and Order of Execution

1.	FlightCreator

o	Responsible for creating the trips table and inserting flight information.

2.	SeatsCreator

o	Used for creating the seats table and initializing the seat information.

3.	UserInserter

o	Adds user information to the database for booking.

4.	NoLocking

o	Contains a simple implementation of booking seats without any locking mechanism.

5.	WorstImplementation

o	Simulates a bad implementation of seat booking without proper concurrency handling.

6.	TicketBookingSystem

o	Main entry point for the ticket booking system. Handles seat booking with concurrency management.

Execution Order
1.	First, run FlightCreator to set up flight data.

2.	Then, run SeatsCreator to set up seat data.

3.	Next, run UserInserter to insert user data into the database.

4.	After that, run WorstImplementation to simulate the booking without locks.

5.	Finally, run TicketBookingSystem for the main functionality with proper concurrency handling.


Ensure that the database schema and tables are created as needed before running these implementations.
Prerequisites

Before running the project, ensure that you have the following installed:

•	Java 8+
•	MySQL Server (Make sure MySQL is running locally or on a remote server)
•	JDBC for MySQL (MySQL Connector)



TicketBookingSystem (Main Application)

The TicketBookingSystem class is responsible for booking seats and printing the final seat assignment.
•	The system uses multi-threading to allow 100 users to concurrently attempt booking seats.
•	It tracks which seats are available or booked using the FOR UPDATE SKIP LOCKED SQL feature to prevent race conditions.
•	After booking, it prints the seat grid and resets all seats for the next round of bookings.

Sample Output

Includes console logs showing:
•	Threads trying to book the same seat
•	Success/failure rates under different locking mechanisms
•	Impact of unsafe booking (NoLocking) vs safe booking

Seats Booked by Users:
User 1 (1A)
User 2 (1B)
User 3 (2A)
...
Total Booked Seats: 100
Execution Time: 1500 milliseconds


Future Improvements

•	GUI Interface (Swing/JavaFX)
•	Admin panel for reports
•	OTP/email confirmation for bookings
•	Integration with payment gateway (mocked)

