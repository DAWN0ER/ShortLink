package com.dawn.shortlink;

import com.dawn.shortlink.dao.CacheUrlMapperDecorator;
import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;


public class DaoTest extends ShortLinkApplicationTests{

    @Autowired
    UrlMapper mapper;
    @Autowired
    CacheUrlMapperDecorator cacheMapper;
    @Autowired
    RedisTemplate<String,String> redis;


    @Test
    public void urlMapperSaveTest(){
        System.out.println("-----------Start---------");
        String[] str = UUID.randomUUID().toString().split("-");
        mapper.insertUrl(new ShortUrlInfoDTO(str[0].substring(0,3),str[1],str[2]));
    }

    @Test
    public void urlMapperSelectTest(){
        String test = "wu80C1";
        System.out.println(
                redis.opsForValue().get(test) + "\n"
                + cacheMapper.selectOriginUrlByShortUrl("wu80C1") + "\n"
                + redis.opsForValue().get(test)
        );

    }

    @Test
    public void urlMapperNullTest(){
        ShortUrlInfoDTO shortUrlInfoDTO = mapper.selectByShortUrl("123");
        System.out.println(shortUrlInfoDTO);
    }

    @Test
    public void urlMapperRepeatTest(){
        try{
            mapper.insertUrl(
                    new ShortUrlInfoDTO(
                            Base62CodeUtil.hashAndEncode("www.repeat.com"),
                            "www.repeat.com",
                            ""));

        }catch (DuplicateKeyException exception){
            System.out.println("repeat");
        }

    }

    @Test
    public void simpleSelectTest(){
        System.out.println(
                mapper.selectOriginUrlByShortUrl("wu80C1")
        );
    }


}
