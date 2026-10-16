
package com.autoservice.model;

public class Vehicle {

    private final int id;
    private final Customer owner;
    private final String brand;
    private final String model;
    private final int manufactureYear;
    private final String licensePlate;

    public Vehicle(
            int id,
            Customer owner,
            String brand,
            String model,
            int manufactureYear,
            String licensePlate
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Vehicle ID must be positive"
            );
        }

        if (owner == null) {
            throw new IllegalArgumentException(
                    "Vehicle must have an owner"
            );
        }

        if (brand == null || brand.isBlank()
                || model == null || model.isBlank()
                || licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException(
                    "Vehicle details cannot be empty"
            );
        }

        if (manufactureYear < 1886
                || manufactureYear > java.time.Year.now().getValue() + 1) {
            throw new IllegalArgumentException(
                    "Invalid manufacture year"
            );
        }

        this.id = id;
        this.owner = owner;
        this.brand = brand.trim();
        this.model = model.trim();
        this.manufactureYear = manufactureYear;
        this.licensePlate = licensePlate.trim();
    }

    public int getId() {
        return id;
    }

    public boolean belongsTo(Customer customer) {
        return customer != null && owner.getId() == customer.getId();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return "Vehicle ID: " + id
                + ", Brand: " + brand
                + ", Model: " + model
                + ", Year: " + manufactureYear
                + ", Plate: " + licensePlate
                + ", Owner: " + owner.getFullName();
    }
}
