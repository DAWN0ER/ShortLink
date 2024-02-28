package com.dawn.shortlink;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.UUID;


public class DaoTest extends ShortLinkApplicationTests{

    @Autowired
    UrlMapper mapper;
//    @Autowired
//    CacheUrlMapperDecorator cacheMapper;


    @Test
    public void urlMapperSaveTest(){
        System.out.println("-----------Start---------");
        String[] str = UUID.randomUUID().toString().split("-");
        mapper.insertUrl(new ShortUrlInfoDTO(str[0].substring(0,3),str[1],str[2]));
    }

    @Test
    public void urlMapperNullTest(){
        ShortUrlInfoDTO shortUrlInfoDTO = mapper.selectOneByShortUrl("123");
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

//    @Test
//    public void cacheUrlMapperTest(){
//        String str = cacheMapper.selectOriginUrlByShortUrl("k0irG3");
//        System.out.println(
//                "result: \""
//                + str
//                +"\""
//        );
//    }

    @Test
    public void timeoutTest(){

    }


}
