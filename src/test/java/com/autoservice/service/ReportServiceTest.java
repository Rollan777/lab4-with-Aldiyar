
package com.autoservice.service;

import com.autoservice.model.Appointment;
import com.autoservice.model.Customer;
import com.autoservice.model.ServiceItem;
import com.autoservice.model.Vehicle;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    @Test
    void shouldCalculateRevenueFromCompletedAppointments() {
        BookingService bookingService = new BookingService();
        ReportService reportService = new ReportService(
                bookingService
        );

        Customer customer = new Customer(
                1,
                "Alex Johnson",
                "+77011234567"
        );

        Vehicle vehicle = new Vehicle(
                1,
                customer,
                "Toyota",
                "Camry",
                2022,
                "777ABC02"
        );

        ServiceItem serviceItem = new ServiceItem(
                1,
                "Oil Change",
                new BigDecimal("15000")
        );

        Appointment appointment = bookingService.bookAppointment(
                vehicle,
                serviceItem,
                LocalDateTime.now().plusDays(1)
        );

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(
                        reportService.calculateTotalRevenue()
                )
        );

        bookingService.startAppointment(appointment.getId());
        bookingService.completeAppointment(appointment.getId());

        assertEquals(
                0,
                new BigDecimal("15000").compareTo(
                        reportService.calculateTotalRevenue()
                )
        );
    }
}
