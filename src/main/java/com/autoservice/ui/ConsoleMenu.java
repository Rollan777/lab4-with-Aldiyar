
package com.autoservice.ui;

import com.autoservice.model.Appointment;
import com.autoservice.model.Customer;
import com.autoservice.model.ServiceItem;
import com.autoservice.model.Vehicle;
import com.autoservice.service.BookingService;
import com.autoservice.service.CustomerService;
import com.autoservice.service.ReportService;
import com.autoservice.service.ServiceCatalog;
import com.autoservice.service.VehicleService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final ServiceCatalog serviceCatalog;
    private final BookingService bookingService;
    private final ReportService reportService;

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
        customerService = new CustomerService();
        vehicleService = new VehicleService();
        serviceCatalog = new ServiceCatalog();
        bookingService = new BookingService();
        reportService = new ReportService(bookingService);
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> registerCustomer();
                    case 2 -> showCustomers();
                    case 3 -> registerVehicle();
                    case 4 -> showVehicles();
                    case 5 -> showServices();
                    case 6 -> createAppointment();
                    case 7 -> showAppointments();
                    case 8 -> startAppointment();
                    case 9 -> completeAppointment();
                    case 10 -> cancelAppointment();
                    case 11 -> reportService.printReport();
                    case 0 -> {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    default -> System.out.println(
                            "Invalid option. Try again."
                    );
                }
            } catch (IllegalArgumentException
                     | IllegalStateException exception) {
                System.out.println(
                        "Error: " + exception.getMessage()
                );
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("     AUTO SERVICE MANAGEMENT");
        System.out.println("====================================");
        System.out.println("1. Register customer");
        System.out.println("2. Show customers");
        System.out.println("3. Register vehicle");
        System.out.println("4. Show vehicles");
        System.out.println("5. Show service catalog");
        System.out.println("6. Book appointment");
        System.out.println("7. Show appointments");
        System.out.println("8. Start service");
        System.out.println("9. Complete service");
        System.out.println("10. Cancel appointment");
        System.out.println("11. Show report");
        System.out.println("0. Exit");
        System.out.println("====================================");
    }

    private void registerCustomer() {
        System.out.println("\n--- REGISTER CUSTOMER ---");

        String fullName = readText("Full name: ");
        String phoneNumber = readText("Phone number: ");

        Customer customer = customerService.registerCustomer(
                fullName,
                phoneNumber
        );

        System.out.println("Customer registered successfully!");
        System.out.println(customer);
    }

    private void showCustomers() {
        System.out.println("\n--- CUSTOMER LIST ---");

        if (customerService.getAllCustomers().isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer :
                customerService.getAllCustomers()) {
            System.out.println(customer);
        }
    }

    private void registerVehicle() {
        System.out.println("\n--- REGISTER VEHICLE ---");

        showCustomers();

        int customerId = readInt("Customer ID: ");
        Customer owner = customerService.findCustomerById(
                customerId
        );

        String brand = readText("Brand: ");
        String model = readText("Model: ");
        int year = readInt("Manufacture year: ");
        String plate = readText("License plate: ");

        Vehicle vehicle = vehicleService.registerVehicle(
                owner,
                brand,
                model,
                year,
                plate
        );

        System.out.println("Vehicle registered successfully!");
        System.out.println(vehicle);
    }

    private void showVehicles() {
        System.out.println("\n--- VEHICLE LIST ---");

        if (vehicleService.getAllVehicles().isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        for (Vehicle vehicle :
                vehicleService.getAllVehicles()) {
            System.out.println(vehicle);
        }
    }

    private void showServices() {
        System.out.println("\n--- SERVICE CATALOG ---");

        for (ServiceItem service :
                serviceCatalog.getAllServices()) {
            System.out.println(service);
        }
    }

    private void createAppointment() {
        System.out.println("\n--- BOOK APPOINTMENT ---");

        showVehicles();

        int vehicleId = readInt("Vehicle ID: ");
        Vehicle vehicle = vehicleService.findVehicleById(
                vehicleId
        );

        showServices();

        int serviceId = readInt("Service ID: ");
        ServiceItem serviceItem =
                serviceCatalog.findServiceById(serviceId);

        String dateInput = readText(
                "Date and time (yyyy-MM-dd HH:mm): "
        );

        LocalDateTime appointmentDate;

        try {
            appointmentDate = LocalDateTime.parse(
                    dateInput,
                    dateFormatter
            );
        } catch (DateTimeParseException exception) {
            System.out.println(
                    "Invalid date format. Example: 2026-12-20 10:00"
            );
            return;
        }

        Appointment appointment =
                bookingService.bookAppointment(
                        vehicle,
                        serviceItem,
                        appointmentDate
                );

        System.out.println("Appointment created successfully!");
        System.out.println(appointment);
    }

    private void showAppointments() {
        System.out.println("\n--- APPOINTMENT LIST ---");

        if (bookingService.getAllAppointments().isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (Appointment appointment :
                bookingService.getAllAppointments()) {
            System.out.println(appointment);
        }
    }

    private void startAppointment() {
        showAppointments();

        int appointmentId = readInt("Appointment ID to start: ");

        bookingService.startAppointment(appointmentId);

        System.out.println("Service started successfully!");
    }

    private void completeAppointment() {
        showAppointments();

        int appointmentId = readInt(
                "Appointment ID to complete: "
        );

        bookingService.completeAppointment(appointmentId);

        System.out.println("Service completed successfully!");
    }

    private void cancelAppointment() {
        showAppointments();

        int appointmentId = readInt(
                "Appointment ID to cancel: "
        );

        bookingService.cancelAppointment(appointmentId);

        System.out.println("Appointment cancelled successfully!");
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}
