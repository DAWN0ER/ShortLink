package com.dawn.shortlink.service.impl;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.*;
import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.dawn.shortlink.service.ShortUrlService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//@TODO 两个方法全部重写
@Service("ShortUrlCacheServiceImpl")
public class ShortUrlCacheServiceImpl implements ShortUrlService {

    private static final String NULL_PREFIX = "SHORT_LINK_IS_NULL";

    @Autowired
    @Qualifier("BloomFilterRedisson")
    private RedissonClient bloomFilterRedisson;

    @Autowired
    @Qualifier("UrlCacheRedisson")
    RedissonClient cacheRedisson;

    @Autowired
    private UrlMapper urlMapper;

    private final String nameOfShortUrlFilter = "shortUrlBloomFilter";
    private final String nameOfLongUrlFilter = "longUrlBloomFilter";


    @Override
    public UrlResponse<String> getOriginUrl(String shortUrl) {
        String originUrlRes = (String) cacheRedisson.getBucket(shortUrl).get();
        if (originUrlRes!=null) // 缓存中数据存在
            return new UrlResponse(ShortUrlResponseStateEnum.GOAL,originUrlRes); // 命中

        if (!bloomFilterRedisson.getBloomFilter("shortUrlBloomFilter").contains(shortUrl) // 布隆过滤器判断不存在
                || cacheRedisson.getBucket(NULL_PREFIX+shortUrl).get()!=null // 布隆过滤器误判存在, 但空值表中查到了
        )
            return new UrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null); // 未找到

        RLock lock = cacheRedisson.getLock("urlCacheLock"); // 获得 redis 实现的分布锁
        try{
            lock.lock();// 上锁, 避免缓存击穿伤害 SQL 服务器
            originUrlRes = (String) cacheRedisson.getBucket(shortUrl).get();
            if (originUrlRes!=null) // double check 提高效率
                return new UrlResponse(ShortUrlResponseStateEnum.GOAL,originUrlRes); // 命中

            ShortUrlInfoDTO infoDTO = urlMapper.selectOneByShortUrl(shortUrl); // 查询数据库里的信息

            if(infoDTO!=null && infoDTO.getExpiredTime().after(new Date())){ // 数据存在且未过期
                long timeout = (long) ((infoDTO.getExpiredTime().getTime()-new Date().getTime())*(new Random().nextDouble()*(0.3)+0.2)); // 过期时间 = 剩余时间的 0.2~0.5
                cacheRedisson.getBucket(shortUrl).set(infoDTO.getOriginUrl(),timeout,TimeUnit.MILLISECONDS); // 添加缓存
                return new UrlResponse(ShortUrlResponseStateEnum.GOAL,infoDTO.getOriginUrl()); // 查询命中
            }

            cacheRedisson.getBucket(NULL_PREFIX+shortUrl).set(NULL_PREFIX,7, TimeUnit.DAYS); // 添加空值, 临时决定7天后过期
            return new UrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public UrlResponse<String> generateUrl(ShortUrlInfoVO vo) {
        String originUrl = vo.originUrl;
        String description = vo.description;
        long timeout = vo.timeout;
        String shortUrl = Base62CodeUtil.hashAndEncode(originUrl);
        String bucketValue = null;

        if(bloomFilterRedisson.getBloomFilter(nameOfLongUrlFilter).contains(originUrl) // 布隆过滤器中可能有记录
                && (bucketValue = (String) cacheRedisson.getBucket(shortUrl).get()) != null // 缓存中存放有短链接的 key
                && bucketValue.equals(originUrl)  // 短链接的 key 刚好指向长连接
                && urlMapper.selectOneByShortUrl(shortUrl).getOriginUrl().equals(originUrl) //最后查询数据库里的长连接是否匹配
        ) return new UrlResponse<String>(ShortUrlResponseStateEnum.ALREADY_SAVED,shortUrl); // 这个长连接已经被保存

        try {
            shortUrl = retryGenerateShortUrl(shortUrl, originUrl); // 尝试重新生成短链接(不超过三次)
            urlMapper.insertUrl(new ShortUrlInfoDTO(shortUrl,originUrl,description,timeout)); // 尝试入库
            cacheRedisson.getBucket(shortUrl).delete(); // 旁路策略

            // 如果都成功没有抛出异常, 就更新 bloom filter
            bloomFilterRedisson.getBloomFilter(nameOfShortUrlFilter).add(shortUrl);
            bloomFilterRedisson.getBloomFilter(nameOfLongUrlFilter).add(originUrl);

        }catch (RetryGenerateShortUrlFailedException e) {
            return new UrlResponse<String>(ShortUrlResponseStateEnum.FAILURE, null);
        }catch (DuplicateKeyException e){
            bloomFilterRedisson.getBloomFilter(nameOfShortUrlFilter).add(shortUrl); // 短链接冲突, 加入布隆过滤器
            return new UrlResponse<String>(ShortUrlResponseStateEnum.COLLISIONS, null);
        }
        return new UrlResponse<String>(ShortUrlResponseStateEnum.SUCCESS,shortUrl);
    }

    @Override
    public UrlResponse redirectToOrigin(String shortUrl, HttpServletResponse httpServletResponse) throws IOException {
        UrlResponse<String> urlResponse = this.getOriginUrl(shortUrl);
            if(urlResponse.getData()!=null) {
                httpServletResponse.sendRedirect(urlResponse.getData());
            }
            return urlResponse;
    }

    private String retryGenerateShortUrl(String shortUrl , String originUrl) throws RetryGenerateShortUrlFailedException {
        RBloomFilter<Object> shortFilter = bloomFilterRedisson.getBloomFilter(nameOfShortUrlFilter);
        int count=3;
        while(count>0 && shortFilter.contains(shortUrl)){
            shortUrl = Base62CodeUtil.hashAndEncode(originUrl+new Date());
            count--;
        }
        if(count<=0 && shortFilter.contains(shortUrl)) throw new RetryGenerateShortUrlFailedException();
        return shortUrl;
    }

}
