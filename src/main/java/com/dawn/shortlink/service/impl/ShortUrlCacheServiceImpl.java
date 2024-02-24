package com.dawn.shortlink.service.impl;

import com.dawn.shortlink.dao.CacheUrlMapperDecorator;
import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.*;
import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.dawn.shortlink.service.ShortUrlService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service("ShortUrlCacheServiceImpl")
public class ShortUrlCacheServiceImpl implements ShortUrlService {

    @Autowired
    private CacheUrlMapperDecorator urlMapper;
    @Autowired
    @Qualifier("BloomFilterRedisson")
    private RedissonClient bloomFilterRedisson;

    private final String nameOfShortUrlFilter = "shortUrlBloomFilter";
    private final String nameOfLongUrlFilter = "longUrlBloomFilter";


    @Override
    public UrlResponse<String> getOriginUrl(String shortUrl) {
        String res = urlMapper.selectOriginUrlByShortUrl(shortUrl);
        if (res.equals("")) return new UrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null);
        return new UrlResponse(ShortUrlResponseStateEnum.GOAL,res);
    }

    @Override
    public UrlResponse<String> generateUrl(ShortUrlInfoVO vo) {
        String originUrl = vo.originUrl;
        String description = vo.description;
        long timeout = vo.timeout;
        String shortUrl = Base62CodeUtil.hashAndEncode(originUrl);
//        String resCacheUrl = null;

        if(bloomFilterRedisson.getBloomFilter(nameOfLongUrlFilter).contains(originUrl) // 布隆过滤器判断长连接存在
//            && (resCacheUrl = urlMapper.selectOriginUrlByShortUrl(shortUrl)).equals(originUrl) // 带命中缓存的查询数据库里的长连接是否匹配
            &&  urlMapper.selectOriginUrlByShortUrl(shortUrl).equals(originUrl) //带命中缓存的查询数据库里的长连接是否匹配
        ){
            return new UrlResponse<String>(ShortUrlResponseStateEnum.ALREADY_SAVED,shortUrl); // 长连接已经被保存
        } try {
//            @TODO 我没写数据库的删除进程, 我写这个判断干嘛, 传一次冲突一次, 我是傻子
//            if(resCacheUrl==null||!resCacheUrl.equals("")) //url mapper 返回分可能有"", 表示这个还能用, 就不需要再生成了
                shortUrl = retryGenerateShortUrl(shortUrl, originUrl); // 尝试重新生成短链接(不超过三次)
            urlMapper.insertUrl(new ShortUrlInfoDTO(shortUrl,originUrl,description,timeout)); // 尝试入库
            // 如果都成功没有抛出异常, 就更新 bloom filter
            bloomFilterRedisson.getBloomFilter(nameOfShortUrlFilter).add(shortUrl);
            bloomFilterRedisson.getBloomFilter(nameOfLongUrlFilter).add(originUrl);
        }catch (RetryGenerateShortUrlFailedException e) {
            return new UrlResponse<String>(ShortUrlResponseStateEnum.FAILURE, null);
        }catch (DuplicateKeyException e){
            bloomFilterRedisson.getBloomFilter(nameOfShortUrlFilter).add(shortUrl); // 短链接冲突, 加入redis
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
