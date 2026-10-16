
package com.autoservice.service;

import com.autoservice.model.ServiceItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServiceCatalog {

    private final List<ServiceItem> services;
    private int nextServiceId;

    public ServiceCatalog() {
        this.services = new ArrayList<>();
        this.nextServiceId = 1;

        addService("Oil Change", new BigDecimal("15000"));
        addService("Car Wash", new BigDecimal("5000"));
        addService("Brake Inspection", new BigDecimal("12000"));
        addService("Engine Diagnostics", new BigDecimal("20000"));
        addService("Tire Replacement", new BigDecimal("18000"));
    }

    public ServiceItem addService(
            String name,
            BigDecimal price
    ) {
        ServiceItem serviceItem = new ServiceItem(
                nextServiceId,
                name,
                price
        );

        services.add(serviceItem);
        nextServiceId++;

        return serviceItem;
    }

    public ServiceItem findServiceById(int serviceId) {
        for (ServiceItem serviceItem : services) {
            if (serviceItem.getId() == serviceId) {
                return serviceItem;
            }
        }

        throw new IllegalArgumentException(
                "Service not found: " + serviceId
        );
    }

    public List<ServiceItem> getAllServices() {
        return List.copyOf(services);
    }

    public int countServices() {
        return services.size();
    }
}
