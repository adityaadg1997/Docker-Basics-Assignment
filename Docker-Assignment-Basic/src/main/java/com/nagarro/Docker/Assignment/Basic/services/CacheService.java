package com.nagarro.Docker.Assignment.Basic.services;

import com.nagarro.Docker.Assignment.Basic.beans.Customer;

public interface CacheService {

    Customer getCache(String key);

    void setCache(String key, Customer customer);

    void clearCache(String key);
}
