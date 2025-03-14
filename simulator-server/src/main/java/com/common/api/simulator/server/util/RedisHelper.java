package com.common.api.simulator.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisHelper {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setWithExpire(String key, String value, long hours) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, hours, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("Redis set error: key={}", key, e);
        }
    }

    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis get error: key={}", key, e);
            return null;
        }
    }

    public boolean delete(String key) {
        try {
            return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
        } catch (Exception e) {
            log.error("Redis delete error: key={}", key, e);
            return false;
        }
    }

    public void udpate(String key, String value) {
        try {
            Long ttl = stringRedisTemplate.getExpire(key);
            if (ttl != null && ttl > 0) {
                stringRedisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
            } else {
                stringRedisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception e) {
            log.error("Redis save error: key={}", key, e);
        }
    }
}