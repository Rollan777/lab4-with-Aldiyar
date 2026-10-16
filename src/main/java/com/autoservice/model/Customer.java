
package com.autoservice.model;

public class Customer {

    private final int id;
    private String fullName;
    private String phoneNumber;

    public Customer(int id, String fullName, String phoneNumber) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Customer ID must be positive"
            );
        }

        updateContactDetails(fullName, phoneNumber);
        this.id = id;
    }

    public void updateContactDetails(
            String fullName,
            String phoneNumber
    ) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer name cannot be empty"
            );
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Phone number cannot be empty"
            );
        }

        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber.trim();
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id
                + ", Name: " + fullName
                + ", Phone: " + phoneNumber;
    }
}
