package com.dawn.shortlink;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.Base62CodeUtil;
import com.dawn.shortlink.domain.ShortUrl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.UUID;


public class ShortLinkDaoTest extends ShortLinkApplicationTests{

    @Autowired
    UrlMapper mapper;


    @Test
    public void urlMapperSaveTest(){
        System.out.println("-----------Start---------");
        String[] str = UUID.randomUUID().toString().split("-");
        mapper.insertUrl(new ShortUrl(str[0].substring(0,3),str[1],str[2]));
    }

    @Test
    public void urlMapperSelectTest(){
        String[] list =
        {"http://xaxvoyqp.coop/odq",
        "http://rdwslthh.na/jefwctofu",
        "www.repeat.com",
        "http://rmtuumkyj.kp/xwqqgwsf",
        "http://sporrdyn.sl/jski",
        "http://hpblvrqqb.pn/jhznp",
        "http://sjgdff.dz/gjrfpk",
        "http://vdcexk.info/fsvqet"};
        for(String e:list){
            ShortUrl shortUrl = mapper.selectByShortUrl(Base62CodeUtil.encode(e));
            System.out.println(shortUrl.toString());
        }

    }

    @Test
    public void urlMapperNullTest(){
        ShortUrl shortUrl = mapper.selectByShortUrl("123");
        System.out.println(shortUrl);
    }

    @Test
    public void urlMapperRepeatTest(){
        try{
            mapper.insertUrl(
                    new ShortUrl(
                            Base62CodeUtil.encode("www.repeat.com"),
                            "www.repeat.com",
                            ""));

        }catch (DuplicateKeyException exception){
            System.out.println("repeat");
        }

    }


}
