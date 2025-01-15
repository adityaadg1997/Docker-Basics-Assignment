package com.nagarro.Docker.Assignment.Basic.controller;


import com.nagarro.Docker.Assignment.Basic.beans.Customer;
import com.nagarro.Docker.Assignment.Basic.services.CacheService;
import com.nagarro.Docker.Assignment.Basic.services.CustomerManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/customerManagement")
public class CustomerManagementApi {

    @Autowired
    private CustomerManagementService customerService;

    @Autowired
    private CacheService cacheService;

    @PostMapping("/customers")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/customers/first")
    public ResponseEntity<Customer> getFirstCustomer() {
        String cacheKey = "firstCustomerData";
        //check if first customer is already present in cache data
        Customer cachedCustomer = cacheService.getCache(cacheKey);
        if(Objects.nonNull(cachedCustomer)){
            log.info("cachedCustomer data :::: {}", cachedCustomer);
            return new ResponseEntity<>(cachedCustomer, HttpStatus.OK);
        }

        //else get from DB and set the cache and return customer data.
        Customer firstCustomer = customerService.getFirstCustomer();
        cacheService.setCache(cacheKey, firstCustomer);
        return new ResponseEntity<>(firstCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/clear/{key}")
    public ResponseEntity<String> clearCache(@PathVariable String key) {
        cacheService.clearCache(key);
        return ResponseEntity.ok("Cache for key " + key + " deleted");
    }

    @GetMapping("/test-redis")
    public String testRedis() {

        Customer c = new Customer();
        c.setCustomerId("12345");
        c.setCustomerName("cached Customer data ::::");
        cacheService.setCache("testKey", c);
        return cacheService.getCache("testKey").getCustomerName();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/customer")
    public ResponseEntity<String> deleteCustomerAndAccByCustomerId(@RequestParam String customerId) {
        customerService.deleteCustomerAndAccount(customerId);
        return ResponseEntity.ok("Customer with customerId " + customerId + "deleted successfully");
    }


}
