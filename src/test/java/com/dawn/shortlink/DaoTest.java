package com.dawn.shortlink;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import com.google.common.hash.Hashing;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class DaoTest extends ShortLinkApplicationTests{

    @Autowired
    UrlMapper mapper;

    @Test
    public void simpleTest(){
        ShortUrlInfoDTO infoDTO = mapper.selectOneByShortUrl("o1vHa");
        System.out.println(infoDTO.toString());
    }

    private long f(String str){
        return Hashing.murmur3_32_fixed().hashString(str, StandardCharsets.UTF_8).padToLong();
    }

    private void ts(){
        String url = UUID.randomUUID().toString().substring(0,4);
        System.out.println(
                "========== URL = " + url
                        + ", NODE = " + (f(url)%4)/2 + "." +f(url)%2
        );

        ShortUrlInfoDTO urlInfoDTO = new ShortUrlInfoDTO(url,"www.baidu.com",
                "desc",
                500L);
//        System.out.println(urlInfoDTO.toString());
        mapper.insertUrl(urlInfoDTO);
    }

    @Test
    public void insertTest() {
        int k = 10;
//        while (k-->0) ts();
    }



}
