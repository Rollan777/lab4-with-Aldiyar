
package com.autoservice.service;

import com.autoservice.model.Appointment;
import com.autoservice.model.ServiceItem;
import com.autoservice.model.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final List<Appointment> appointments;
    private int nextAppointmentId;

    public BookingService() {
        this.appointments = new ArrayList<>();
        this.nextAppointmentId = 1;
    }

    public Appointment bookAppointment(
            Vehicle vehicle,
            ServiceItem serviceItem,
            LocalDateTime appointmentDate
    ) {
        validateBooking(vehicle, serviceItem, appointmentDate);

        Appointment appointment = new Appointment(
                nextAppointmentId,
                vehicle,
                serviceItem,
                appointmentDate
        );

        appointments.add(appointment);
        nextAppointmentId++;

        return appointment;
    }

    private void validateBooking(
            Vehicle vehicle,
            ServiceItem serviceItem,
            LocalDateTime appointmentDate
    ) {
        if (vehicle == null || serviceItem == null) {
            throw new IllegalArgumentException(
                    "Vehicle and service are required"
            );
        }

        if (appointmentDate == null
                || !appointmentDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Appointment date must be in the future"
            );
        }

        if (!isSlotAvailable(appointmentDate)) {
            throw new IllegalStateException(
                    "This appointment slot is already booked"
            );
        }
    }

    public boolean isSlotAvailable(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException(
                    "Appointment date is required"
            );
        }

        for (Appointment appointment : appointments) {
            if (appointment.occupiesSlot(dateTime)) {
                return false;
            }
        }

        return true;
    }

    public Appointment findAppointmentById(int appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.hasId(appointmentId)) {
                return appointment;
            }
        }

        throw new IllegalArgumentException(
                "Appointment not found: " + appointmentId
        );
    }

    public void startAppointment(int appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.startService();
    }

    public void completeAppointment(int appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.completeService();
    }

    public void cancelAppointment(int appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.cancel();
    }

    public List<Appointment> getAllAppointments() {
        return List.copyOf(appointments);
    }

    public int countAppointments() {
        return appointments.size();
    }

    public int countCompletedAppointments() {
        int completedCount = 0;

        for (Appointment appointment : appointments) {
            if (appointment.isCompleted()) {
                completedCount++;
            }
        }

        return completedCount;
    }
}
