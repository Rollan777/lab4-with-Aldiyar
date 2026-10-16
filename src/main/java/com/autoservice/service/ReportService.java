
package com.autoservice.service;

import com.autoservice.model.Appointment;
import com.autoservice.model.AppointmentStatus;

import java.math.BigDecimal;
import java.util.List;

public class ReportService {

    private final BookingService bookingService;

    public ReportService(BookingService bookingService) {
        if (bookingService == null) {
            throw new IllegalArgumentException(
                    "Booking service is required"
            );
        }

        this.bookingService = bookingService;
    }

    public BigDecimal calculateTotalRevenue() {
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Appointment appointment :
                bookingService.getAllAppointments()) {

            if (appointment.isCompleted()) {
                totalRevenue = totalRevenue.add(
                        appointment.calculateTotalPrice()
                );
            }
        }

        return totalRevenue;
    }

    public long countByStatus(AppointmentStatus status) {
        long count = 0;

        for (Appointment appointment :
                bookingService.getAllAppointments()) {

            if (appointment.getStatus() == status) {
                count++;
            }
        }

        return count;
    }

    public void printReport() {
        List<Appointment> appointments =
                bookingService.getAllAppointments();

        System.out.println("\n========== SERVICE REPORT ==========");

        System.out.println("Total appointments: "
                + appointments.size());

        System.out.println("Scheduled: "
                + countByStatus(AppointmentStatus.SCHEDULED));

        System.out.println("In progress: "
                + countByStatus(AppointmentStatus.IN_PROGRESS));

        System.out.println("Completed: "
                + countByStatus(AppointmentStatus.COMPLETED));

        System.out.println("Cancelled: "
                + countByStatus(AppointmentStatus.CANCELLED));

        System.out.println("Total revenue: "
                + calculateTotalRevenue() + " KZT");

        System.out.println("====================================");
    }
}
