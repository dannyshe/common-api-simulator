package com.payment.util;

import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.when;

public class CommonBeanUtil {

    public static RedisTemplate getBasicRedisTemplate() {
        RedisTemplate redisTemplate = Mockito.mock(RedisTemplate.class);
        ValueOperations valueOperations = Mockito.mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        return redisTemplate;
    }

}
