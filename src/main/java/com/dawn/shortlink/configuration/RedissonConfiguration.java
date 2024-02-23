package com.dawn.shortlink.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port)
                .setDatabase(5);
        RedissonClient client = Redisson.create(config);
        client.getBloomFilter("shortUrlBloomFilter").tryInit(1000000,0.001);
        client.getBloomFilter("longUrlBloomFilter").tryInit(1000000,0.001);
        return client;
    }
}
