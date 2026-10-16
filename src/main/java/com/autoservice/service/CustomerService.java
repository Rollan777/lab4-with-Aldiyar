
package com.autoservice.service;

import com.autoservice.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private final List<Customer> customers;
    private int nextCustomerId;

    public CustomerService() {
        this.customers = new ArrayList<>();
        this.nextCustomerId = 1;
    }

    public Customer registerCustomer(
            String fullName,
            String phoneNumber
    ) {
        Customer customer = new Customer(
                nextCustomerId,
                fullName,
                phoneNumber
        );

        customers.add(customer);
        nextCustomerId++;

        return customer;
    }

    public Customer findCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }

        throw new IllegalArgumentException(
                "Customer not found: " + customerId
        );
    }

    public void updateCustomer(
            int customerId,
            String fullName,
            String phoneNumber
    ) {
        Customer customer = findCustomerById(customerId);
        customer.updateContactDetails(fullName, phoneNumber);
    }

    public List<Customer> getAllCustomers() {
        return List.copyOf(customers);
    }

    public int countCustomers() {
        return customers.size();
    }
}
