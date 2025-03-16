package com.example.cart_service.services.base.impl;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.cart_service.services.base.BaseRedisService;

@Service
public class BaseRedisServiceImpl implements BaseRedisService {
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    public BaseRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        // allows the service to efficiently use Redis hash operations without calling
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Object hashGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public void hashSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public boolean hashExist(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    @Override
    public Map<String, Object> getField(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public void delete(String key, String field) {
        this.hashOperations.delete(key, field);
    }
}
