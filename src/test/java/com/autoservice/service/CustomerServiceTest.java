
package com.autoservice.service;

import com.autoservice.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService();
    }

    @Test
    void shouldRegisterCustomer() {
        Customer customer = customerService.registerCustomer(
                "Alex Johnson",
                "+77011234567"
        );

        assertEquals(1, customer.getId());
        assertEquals("Alex Johnson", customer.getFullName());
        assertEquals(1, customerService.countCustomers());
    }

    @Test
    void shouldFindCustomerById() {
        Customer customer = customerService.registerCustomer(
                "John Smith",
                "+77019876543"
        );

        Customer foundCustomer =
                customerService.findCustomerById(customer.getId());

        assertEquals(customer.getId(), foundCustomer.getId());
    }

    @Test
    void shouldRejectEmptyCustomerName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.registerCustomer(
                        "",
                        "+77011234567"
                )
        );
    }

    @Test
    void shouldRejectUnknownCustomerId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.findCustomerById(999)
        );
    }

    @Test
    void shouldUpdateCustomer() {
        Customer customer = customerService.registerCustomer(
                "Alex Johnson",
                "+77011234567"
        );

        customerService.updateCustomer(
                customer.getId(),
                "Alex Smith",
                "+77019876543"
        );

        assertEquals("Alex Smith", customer.getFullName());
        assertEquals("+77019876543", customer.getPhoneNumber());
    }
}
