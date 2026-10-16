
package com.autoservice.service;

import com.autoservice.model.Appointment;
import com.autoservice.model.AppointmentStatus;
import com.autoservice.model.Customer;
import com.autoservice.model.ServiceItem;
import com.autoservice.model.Vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private BookingService bookingService;
    private Vehicle vehicle;
    private ServiceItem serviceItem;
    private LocalDateTime bookingDate;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService();

        Customer customer = new Customer(
                1,
                "Alex Johnson",
                "+77011234567"
        );

        vehicle = new Vehicle(
                1,
                customer,
                "Toyota",
                "Camry",
                2022,
                "777ABC02"
        );

        serviceItem = new ServiceItem(
                1,
                "Oil Change",
                new BigDecimal("15000")
        );

        bookingDate = LocalDateTime.now().plusDays(1);
    }

    @Test
    void shouldBookAppointment() {
        Appointment appointment = bookingService.bookAppointment(
                vehicle,
                serviceItem,
                bookingDate
        );

        assertEquals(1, appointment.getId());
        assertEquals(
                AppointmentStatus.SCHEDULED,
                appointment.getStatus()
        );
        assertEquals(1, bookingService.countAppointments());
    }

    @Test
    void shouldPreventDoubleBooking() {
        bookingService.bookAppointment(
                vehicle,
                serviceItem,
                bookingDate
        );

        assertThrows(
                IllegalStateException.class,
                () -> bookingService.bookAppointment(
                        vehicle,
                        serviceItem,
                        bookingDate
                )
        );
    }

    @Test
    void shouldCancelAppointment() {
        Appointment appointment = bookingService.bookAppointment(
                vehicle,
                serviceItem,
                bookingDate
        );

        bookingService.cancelAppointment(appointment.getId());

        assertEquals(
                AppointmentStatus.CANCELLED,
                appointment.getStatus()
        );
    }

    @Test
    void shouldAllowBookingAfterCancellation() {
        Appointment firstAppointment =
                bookingService.bookAppointment(
                        vehicle,
                        serviceItem,
                        bookingDate
                );

        bookingService.cancelAppointment(
                firstAppointment.getId()
        );

        Appointment secondAppointment =
                bookingService.bookAppointment(
                        vehicle,
                        serviceItem,
                        bookingDate
                );

        assertEquals(2, secondAppointment.getId());
    }

    @Test
    void shouldCompleteAppointment() {
        Appointment appointment = bookingService.bookAppointment(
                vehicle,
                serviceItem,
                bookingDate
        );

        bookingService.startAppointment(appointment.getId());
        bookingService.completeAppointment(appointment.getId());

        assertEquals(
                AppointmentStatus.COMPLETED,
                appointment.getStatus()
        );

        assertEquals(
                1,
                bookingService.countCompletedAppointments()
        );
    }

    @Test
    void shouldRejectCompletingScheduledAppointment() {
        Appointment appointment = bookingService.bookAppointment(
                vehicle,
                serviceItem,
                bookingDate
        );

        assertThrows(
                IllegalStateException.class,
                () -> bookingService.completeAppointment(
                        appointment.getId()
                )
        );
    }
}
