package com.baeker.study.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisUt {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> long getExpire(T key) {
        return redisTemplate.getExpire(key);
    }

    public <T> String getValue(T key) {
        ValueOperations<T, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
}

