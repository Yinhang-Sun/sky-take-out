package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configurable
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("Start creating redis template object. . .");
        RedisTemplate redisTemplate = new RedisTemplate();
        //Set the redis connection factory object
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //Set the serializer for redis key
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
