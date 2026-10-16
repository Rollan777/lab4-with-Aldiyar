
package com.autoservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Appointment {

    private final int id;
    private final Vehicle vehicle;
    private final ServiceItem serviceItem;
    private final LocalDateTime appointmentDate;

    private AppointmentStatus status;

    public Appointment(
            int id,
            Vehicle vehicle,
            ServiceItem serviceItem,
            LocalDateTime appointmentDate
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Appointment ID must be positive"
            );
        }

        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle is required"
            );
        }

        if (serviceItem == null) {
            throw new IllegalArgumentException(
                    "Service is required"
            );
        }

        if (appointmentDate == null
                || !appointmentDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Appointment date must be in the future"
            );
        }

        this.id = id;
        this.vehicle = vehicle;
        this.serviceItem = serviceItem;
        this.appointmentDate = appointmentDate;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public BigDecimal calculateTotalPrice() {
        return serviceItem.getPrice();
    }

    public boolean hasId(int appointmentId) {
        return id == appointmentId;
    }

    public boolean occupiesSlot(LocalDateTime dateTime) {
        return status != AppointmentStatus.CANCELLED
                && appointmentDate.equals(dateTime);
    }

    public boolean isCompleted() {
        return status == AppointmentStatus.COMPLETED;
    }

    public boolean isCancelled() {
        return status == AppointmentStatus.CANCELLED;
    }

    public void startService() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException(
                    "Only scheduled appointments can be started"
            );
        }

        status = AppointmentStatus.IN_PROGRESS;
    }

    public void completeService() {
        if (status != AppointmentStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Service must be in progress"
            );
        }

        status = AppointmentStatus.COMPLETED;
    }

    public void cancel() {
        if (isCompleted() || isCancelled()) {
            throw new IllegalStateException(
                    "This appointment cannot be cancelled"
            );
        }

        status = AppointmentStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + id
                + ", Vehicle: " + vehicle.getLicensePlate()
                + ", Date: " + appointmentDate
                + ", Status: " + status
                + ", Price: " + calculateTotalPrice() + " KZT";
    }
}
