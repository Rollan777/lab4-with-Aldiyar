
package com.autoservice.model;

import java.math.BigDecimal;

public class ServiceItem {

    private final int id;
    private final String name;
    private final BigDecimal price;

    public ServiceItem(int id, String name, BigDecimal price) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Service ID must be positive"
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Service name cannot be empty"
            );
        }

        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Service price must be positive"
            );
        }

        this.id = id;
        this.name = name.trim();
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Service ID: " + id
                + ", Name: " + name
                + ", Price: " + price + " KZT";
    }
}
