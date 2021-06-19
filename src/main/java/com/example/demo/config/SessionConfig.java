package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableRedisHttpSession
public class SessionConfig {
    @Autowired
    RedisProperties redisProperties;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        LettuceConnectionFactory factory=new LettuceConnectionFactory();
        factory.setHostName(redisProperties.getHost());
        factory.setPort(redisProperties.getPort());
        factory.setPassword(redisProperties.getPassword());


        return factory;
    }
}
