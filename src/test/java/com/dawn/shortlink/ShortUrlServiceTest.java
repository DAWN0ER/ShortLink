package com.dawn.shortlink;

import com.dawn.shortlink.domain.ShortUrlResponse;
import com.dawn.shortlink.service.ShortUrlService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class ShortUrlServiceTest extends ShortLinkApplicationTests {

    @Autowired
    @Qualifier("shortUrlServiceImpl")
    ShortUrlService service;

    @Test
    public void getFunctionTest(){
//        System.out.println(service);
        ShortUrlResponse response = service.getShortUrl("www.repeat.com");
        System.out.println(response);
//        System.out.println(response.getDescription());
//        System.out.println(response.getShortUrl().toString());
    }

}
