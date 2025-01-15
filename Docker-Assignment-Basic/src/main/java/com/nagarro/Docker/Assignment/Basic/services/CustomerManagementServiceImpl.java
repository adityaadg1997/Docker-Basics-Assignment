package com.nagarro.Docker.Assignment.Basic.services;


import com.nagarro.Docker.Assignment.Basic.beans.Customer;
import com.nagarro.Docker.Assignment.Basic.exceptions.CustomerBaseException;
import com.nagarro.Docker.Assignment.Basic.exceptions.ResourceNotFoundException;
import com.nagarro.Docker.Assignment.Basic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

    @Autowired
    private CustomerRepository repository;


    @Override
    public Customer addCustomer(Customer customer) {
        String id = UUID.randomUUID().toString();
        customer.setCustomerId(id);

        return repository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @Override
    public Customer getFirstCustomer() {
        Customer customerData = repository.findTopByOrderByCustomerId();
        if(Objects.nonNull(customerData)){
            return customerData;
        }

        throw new CustomerBaseException("No Customer Data Found in DB!");
    }

    @Override
    public Customer getCustomerById(String customerId) {
        return repository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
    }

    @Override
    public Customer updateCustomer(String customerId, Customer updatedCustomer) {
        Customer existingCustomer = getCustomerById(customerId);
        existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
        existingCustomer.setEmailAddress(updatedCustomer.getEmailAddress());
        existingCustomer.setContactNumber(updatedCustomer.getContactNumber());
        existingCustomer.setPassword(updatedCustomer.getPassword());

        return repository.save(existingCustomer);
    }

    @Override
    public void deleteCustomerAndAccount(String customerId) {
        Customer customer = repository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
        repository.delete(customer);
    }
}
