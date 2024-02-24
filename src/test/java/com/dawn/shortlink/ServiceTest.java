package com.dawn.shortlink;

import com.dawn.shortlink.domain.pojo.ShortUrlInfoVO;
import com.dawn.shortlink.domain.pojo.UrlResponse;
import com.dawn.shortlink.service.ShortUrlService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class ServiceTest extends ShortLinkApplicationTests {

    @Autowired
    @Qualifier("ShortUrlCacheServiceImpl")
    ShortUrlService service;


    @Test
    public void saveFunctionTest(){
        UrlResponse url = service.generateUrl(new ShortUrlInfoVO("www.repeat.com.2", "test", 100L));
        System.out.println(
                url.getState().getCode() + "; "
                + url.getState().getMassage() +";"
                + url.getData() + ";"
        );
    }

    @Test
    public void getFunctionTest(){
        UrlResponse url = service.getOriginUrl("wu80C1");
        System.out.println(
                url.getState().getCode() + "; "
                        + url.getState().getMassage() +";"
                        + url.getData() + ";"
        );
    }

}
