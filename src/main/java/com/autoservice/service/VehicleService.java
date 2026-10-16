
package com.autoservice.service;

import com.autoservice.model.Customer;
import com.autoservice.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleService {

    private final List<Vehicle> vehicles;
    private int nextVehicleId;

    public VehicleService() {
        this.vehicles = new ArrayList<>();
        this.nextVehicleId = 1;
    }

    public Vehicle registerVehicle(
            Customer owner,
            String brand,
            String model,
            int manufactureYear,
            String licensePlate
    ) {
        if (findVehicleByPlate(licensePlate) != null) {
            throw new IllegalStateException(
                    "Vehicle with this license plate already exists"
            );
        }

        Vehicle vehicle = new Vehicle(
                nextVehicleId,
                owner,
                brand,
                model,
                manufactureYear,
                licensePlate
        );

        vehicles.add(vehicle);
        nextVehicleId++;

        return vehicle;
    }

    public Vehicle findVehicleById(int vehicleId) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == vehicleId) {
                return vehicle;
            }
        }

        throw new IllegalArgumentException(
                "Vehicle not found: " + vehicleId
        );
    }

    public Vehicle findVehicleByPlate(String licensePlate) {
        if (licensePlate == null || licensePlate.isBlank()) {
            return null;
        }

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate()
                    .equalsIgnoreCase(licensePlate.trim())) {
                return vehicle;
            }
        }

        return null;
    }

    public List<Vehicle> getVehiclesByCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer is required"
            );
        }

        List<Vehicle> customerVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.belongsTo(customer)) {
                customerVehicles.add(vehicle);
            }
        }

        return List.copyOf(customerVehicles);
    }

    public List<Vehicle> getAllVehicles() {
        return List.copyOf(vehicles);
    }

    public int countVehicles() {
        return vehicles.size();
    }
}
