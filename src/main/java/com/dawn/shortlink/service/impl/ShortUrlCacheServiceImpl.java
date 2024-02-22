package com.dawn.shortlink.service.impl;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import com.dawn.shortlink.domain.pojo.ShortUrlResponseStateEnum;
import com.dawn.shortlink.domain.pojo.ShortUrlServiceException;
import com.dawn.shortlink.domain.pojo.UrlResponse;
import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.dawn.shortlink.domain.utils.BloomFilter;
import com.dawn.shortlink.domain.utils.BloomFilterSet;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("ShortUrlCacheServiceImpl")
public class ShortUrlCacheServiceImpl implements ShortUrlService {

    @Autowired
    private UrlMapper mapper;
    @Autowired
    private BloomFilterSet bloomFilterSet;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redis;

    @Value("${my.short_url_filter_name}")
    private String nameOfShortUrlFilter;
    @Value("${my.long_url_filter_name}")
    private String nameOfLongUrlFilter;


    @Override
    public UrlResponse getOriginUrl(String shortUrl) {
        if(!bloomFilterSet.getBloomFilter(nameOfShortUrlFilter).mightContains(shortUrl,redis))
            return new UrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null);
        ShortUrlInfoDTO res = mapper.selectByShortUrl(shortUrl);
        if (res==null) return new UrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null);
        else return new UrlResponse(ShortUrlResponseStateEnum.GOAL,res.getOriginUrl());
    }

    @Override
    public UrlResponse saveUrl(String originUrl, String description, long timeout) {
        String shortUrl = Base62CodeUtil.encode(originUrl);

        if(bloomFilterSet.getBloomFilter(nameOfLongUrlFilter).mightContains(originUrl,redis) // 布隆过滤器判断长连接存在
            && mapper.selectByShortUrl(shortUrl).getOriginUrl().equals(originUrl) // 查询数据库里的长连接是否匹配
        ){
            return new UrlResponse(ShortUrlResponseStateEnum.ALREADY_SAVED,shortUrl); // 长连接已经被保存
        }else try {
            shortUrl = tryGenerateShortUrl(shortUrl,originUrl); // 尝试重新生成短链接(不超过三次)
            mapper.insertUrl(new ShortUrlInfoDTO(shortUrl,originUrl,description,timeout)); // 尝试入库
            // 如果都成功没有抛出异常, 就更新 redis
            bloomFilterSet.getBloomFilter(nameOfShortUrlFilter).add(shortUrl,redis);
            bloomFilterSet.getBloomFilter(nameOfLongUrlFilter).add(originUrl,redis);
        }catch (ShortUrlServiceException e) {
            return new UrlResponse(ShortUrlResponseStateEnum.FAILURE, null);
        }catch (DuplicateKeyException e){
            return new UrlResponse(ShortUrlResponseStateEnum.COLLISIONS, null);
        }
        return new UrlResponse(ShortUrlResponseStateEnum.SUCCESS,shortUrl);
    }


    private String tryGenerateShortUrl(String shortUrl , String originUrl) throws ShortUrlServiceException {
        BloomFilter shortFilter = bloomFilterSet.getBloomFilter(nameOfShortUrlFilter);
        int count=3;
        while(count>0 && shortFilter.mightContains(shortUrl,redis)){
            shortUrl = Base62CodeUtil.encode(originUrl+new Date().toString());
            count--;
        }
        if(count<=0 && shortFilter.mightContains(shortUrl,redis)) throw new ShortUrlServiceException();
        return shortUrl;
    }

}
