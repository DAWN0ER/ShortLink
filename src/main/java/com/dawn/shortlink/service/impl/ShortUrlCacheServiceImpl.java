package com.dawn.shortlink.service.impl;

import com.dawn.shortlink.dao.CacheUrlMapperDecorator;
import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.*;
import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.dawn.shortlink.service.ShortUrlService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("ShortUrlCacheServiceImpl")
public class ShortUrlCacheServiceImpl implements ShortUrlService {

    @Autowired
    private CacheUrlMapperDecorator urlMapper; // 这个是 mapper 的装饰器, 增加了缓存命中的功能
    @Autowired
    private RedissonClient redisson;

    private final String nameOfShortUrlFilter = "shortUrlBloomFilter";
    private final String nameOfLongUrlFilter = "longUrlBloomFilter";


    @Override
    public UrlResponse getOriginUrl(String shortUrl) {
        if(!redisson.getBloomFilter(nameOfShortUrlFilter).contains(shortUrl))
            return new UrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null);
        String res = urlMapper.selectOriginUrlByShortUrl(shortUrl);
        if (res==null) return new UrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null);
        else return new UrlResponse(ShortUrlResponseStateEnum.GOAL,res);
    }

    @Override
    public UrlResponse generateUrl(ShortUrlInfoVO vo) {
        String originUrl = vo.originUrl;
        String description = vo.description;
        long timeout = vo.timeout;
        String shortUrl = Base62CodeUtil.hashAndEncode(originUrl);

        if(redisson.getBloomFilter(nameOfLongUrlFilter).contains(originUrl) // 布隆过滤器判断长连接存在
            && urlMapper.selectOriginUrlByShortUrl(shortUrl).equals(originUrl) // 带命中缓存的查询数据库里的长连接是否匹配
        ){
            return new UrlResponse(ShortUrlResponseStateEnum.ALREADY_SAVED,shortUrl); // 长连接已经被保存
        } try {
            shortUrl = retryGenerateShortUrl(shortUrl,originUrl); // 尝试重新生成短链接(不超过三次)
            urlMapper.insertUrl(new ShortUrlInfoDTO(shortUrl,originUrl,description,timeout)); // 尝试入库
            // 如果都成功没有抛出异常, 就更新 redis
            redisson.getBloomFilter(nameOfShortUrlFilter).add(shortUrl);
            redisson.getBloomFilter(nameOfLongUrlFilter).add(originUrl);
        }catch (RetryGenerateShortUrlFailedException e) {
            return new UrlResponse(ShortUrlResponseStateEnum.FAILURE, null);
        }catch (DuplicateKeyException e){
            redisson.getBloomFilter(nameOfShortUrlFilter).add(shortUrl); // 短链接冲突, 加入redis
            return new UrlResponse(ShortUrlResponseStateEnum.COLLISIONS, null);
        }
        return new UrlResponse(ShortUrlResponseStateEnum.SUCCESS,shortUrl);
    }


    private String retryGenerateShortUrl(String shortUrl , String originUrl) throws RetryGenerateShortUrlFailedException {
        RBloomFilter<Object> shortFilter = redisson.getBloomFilter(nameOfShortUrlFilter);
        int count=3;
        while(count>0 && shortFilter.contains(shortUrl)){
            shortUrl = Base62CodeUtil.hashAndEncode(originUrl+new Date());
            count--;
        }
        if(count<=0 && shortFilter.contains(shortUrl)) throw new RetryGenerateShortUrlFailedException();
        return shortUrl;
    }

}
