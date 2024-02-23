package com.dawn.shortlink.dao;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/*
 *  装饰 mapper 添加了内存命中功能
 */
@Repository
public class CacheUrlMapperDecorator{

    @Autowired
    RedisTemplate<String,String> redis;

    @Autowired
    private UrlMapper urlMapper;

    public void insertUrl(ShortUrlInfoDTO shortUrlInfoDTO) {
        urlMapper.insertUrl(shortUrlInfoDTO);
        if(Boolean.TRUE.equals(redis.hasKey(shortUrlInfoDTO.getShortUrl()))){
            redis.delete(shortUrlInfoDTO.getShortUrl());
        }
    }

    public String selectOriginUrlByShortUrl(String shortUrl) {
        if(Boolean.TRUE.equals(redis.hasKey(shortUrl))){
            return redis.opsForValue().get(shortUrl);
        }
        String res = urlMapper.selectOriginUrlByShortUrl(shortUrl);
        redis.opsForValue().set(shortUrl,res);
        return res;
    }


}
