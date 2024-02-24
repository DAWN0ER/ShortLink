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
    private final int bfDB = 5;
    private final int cacheDB = 2;


    @Bean("BloomFilterRedisson")
    public RedissonClient bloomFilterRedisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port)
                .setDatabase(bfDB);
        RedissonClient client = Redisson.create(config);
        client.getBloomFilter("shortUrlBloomFilter").tryInit(1000000,0.001);
        client.getBloomFilter("longUrlBloomFilter").tryInit(1000000,0.001);
        return client;
    }

    @Bean("UrlCacheRedisson")
    public RedissonClient urlCacheRedisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port)
                .setDatabase(cacheDB);
        RedissonClient client = Redisson.create(config);
        return client;
    }
}
