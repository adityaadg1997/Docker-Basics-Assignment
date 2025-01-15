package com.nagarro.Docker.Assignment.Basic.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.Docker.Assignment.Basic.beans.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper; // For JSON serialization and deserialization

    public void setCache(String key, Customer customer) {
        try {
            String json = objectMapper.writeValueAsString(customer); // Convert Customer to JSON string
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer getCache(String key) {
        try {
            String json = redisTemplate.opsForValue().get(key); // Get JSON string from Redis
            if (json != null) {
                return objectMapper.readValue(json, Customer.class); // Convert JSON string back to Customer
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearCache(String key) {
        redisTemplate.delete(key);
    }
}
