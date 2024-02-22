package com.dawn.shortlink.service.domain.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class BloomFilterSet {

    private final HashMap<String,BloomFilter> bfMap;

    public BloomFilterSet(@Autowired @NotNull BloomFilterConfiguration configuration, @Autowired RedisTemplate redisTemplate) {
        this.bfMap=new HashMap<>();
        List<BloomFilterConfParam> paramList =configuration.getBloomFilters();
        for(BloomFilterConfParam param:paramList){
            this.creatBloomFilter(
                    param.getName(),
                    param.getExpectedInsertions(),
                    param.getFpp(),
                    redisTemplate

            );
        }
    }

    private void creatBloomFilter(String name, int expectedInsertions, double fpp, RedisTemplate redis){
        this.bfMap.put(name,new BloomFilter(expectedInsertions,fpp,redis));
    }

    public BloomFilter getBloomFilter(String name){
        return bfMap.get(name);
    }
}
