package com.dawn.shortlink.dao;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoVO;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 *  装饰 mapper 添加了内存命中功能
 */
@Repository
public class CacheUrlMapperDecorator{

    @Autowired
    @Qualifier("UrlCacheRedisson")
    RedissonClient cacheRedisson;
    @Autowired
    @Qualifier("BloomFilterRedisson")
    private RedissonClient bloomFilterRedisson;


    @Autowired
    private UrlMapper urlMapper;

    public void insertUrl(ShortUrlInfoDTO shortUrlInfoDTO) {
        urlMapper.insertUrl(shortUrlInfoDTO); // 向数据库添加信息
        cacheRedisson.getBucket(shortUrlInfoDTO.getShortUrl()).delete(); // 删除 redis 里的旧信息
    }

    /*
    @TODO 上次的逻辑太简单没加分布锁, 这次改一改
    查不到返回空字符串, 而不是 null 可以方便比较
     */
    public String selectOriginUrlByShortUrl(String shortUrl) {
        String originUrlRes = (String) cacheRedisson.getBucket(shortUrl).get();
        if (originUrlRes!=null && !originUrlRes.equals("")) // 缓存中数据存在
            return originUrlRes;

        if (!bloomFilterRedisson.getBloomFilter("shortUrlBloomFilter").contains(shortUrl)) // bloom 中数据不存在
            return "";

        RLock lock = cacheRedisson.getLock("urlCacheLock"); // 获得 redis 分布锁, 虽然不懂我这个单线程为什么要做分布锁
        try{
            while(!lock.tryLock()); // 可能有问题,但是先就这样
            originUrlRes = (String) cacheRedisson.getBucket(shortUrl).get();
            if (originUrlRes!=null && !originUrlRes.equals("")) // double check 提高效率
                return originUrlRes;

            ShortUrlInfoDTO infoDTO = urlMapper.selectOneByShortUrl(shortUrl); // 查询数据库里的信息

            if(infoDTO!=null && infoDTO.getExpiredTime().after(new Date())){ // 数据存在且未过期
                cacheRedisson.getBucket(shortUrl).set(infoDTO.getOriginUrl()); // 添加缓存
                return infoDTO.getOriginUrl();
            }
            cacheRedisson.getBucket(shortUrl).set(""); // 添加空值
            return "";
        } finally {
            lock.unlock();
        }
    }


}
