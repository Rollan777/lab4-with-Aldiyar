# AutoService Management System

## About the Project

AutoService Management System is a console application that we developed in Java for our Enterprise Development course.

We chose a car service management system because it is a practical example of how different classes and services can work together in one application.

The program helps manage customers, cars, service appointments, and payments for completed services.

Our main goal was to build a working Java application and apply the clean code principles we studied during the course.

## Technologies

We used:

- Java 17
- Maven
- JUnit 5
- IntelliJ IDEA
- Java Collections Framework

## Main Features

Our application has the following features:

1. Register new customers.
2. View the list of customers.
3. Register vehicles and connect them to customers.
4. View registered vehicles.
5. View available car services and their prices.
6. Book an appointment for a selected service.
7. View all appointments.
8. Start a scheduled service.
9. Complete or cancel an appointment.
10. Generate a report with completed services and total revenue.

The program also checks user input and prevents two appointments from being booked for exactly the same time.

## Project Structure

We divided the project into three main packages:

**model** — contains the main objects, such as Customer, Vehicle, ServiceItem, and Appointment.

**service** — contains the main logic of the application, including customer registration, vehicle management, appointment booking, and reports.

**ui** — contains ConsoleMenu, which allows users to interact with the program.

The Main class starts the application.

We used this structure to keep the code organized and make it easier to understand.

## Clean Code Principles

During development, we tried to follow the principles from our lectures.

**Lecture 1 — Clean Code**

We separated the application into different classes instead of writing everything in one file.

**Lecture 2 — Meaningful Names**

We used names such as `registerCustomer()`, `bookAppointment()`, and `calculateTotalRevenue()` so that it is easier to understand what each method does.

**Lecture 3 — Functions**

We tried to keep methods focused on one task. For example, booking and cancelling appointments are handled by separate methods.

**Lecture 4 — Comments**

We avoided unnecessary comments when the code was already understandable. We focused on making the names and logic clear.

**Lecture 5 — Formatting**

We followed consistent indentation, spacing, and Java naming conventions.

**Lecture 6 — Objects and Data Structures**

We used objects to represent customers, vehicles, and appointments. We also used ArrayList to store information and private fields to protect object data.

## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure JDK 17 is selected.
3. Load the Maven dependencies.
4. Open `Main.java`.
5. Run the `main()` method.
6. Select an option from the console menu.

## Testing

We used JUnit 5 to test important parts of the application.

We created three test classes:

- CustomerServiceTest — 5 tests
- BookingServiceTest — 6 tests
- ReportServiceTest — 1 test

**Test results:**

- Total tests: 12
- Passed: 12
- Failed: 0

All 12 tests passed successfully using Maven.

## Limitations

The current version stores data in memory, so information is lost when the program closes.

The application also uses a console interface and does not have a database yet. Appointment conflicts are checked using the exact starting time.

In the future, we could add a database, a web interface, and more advanced appointment scheduling.

## Team Members

This project was developed by two students:

1. Rollan
2. Aldiyar

## Conclusion

While working on this project, we practiced Java, OOP, collections, Maven, and unit testing.

We also learned how to organize code into different classes and apply the clean code principles from our lectures.

The final result is a working console application that can manage the basic operations of a car service center.