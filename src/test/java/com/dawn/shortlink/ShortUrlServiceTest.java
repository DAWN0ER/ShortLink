package com.dawn.shortlink;

import com.dawn.shortlink.domain.pojo.UrlResponse;
import com.dawn.shortlink.domain.utils.BloomFilterConfiguration;
import com.dawn.shortlink.domain.utils.BloomFilterSet;
import com.dawn.shortlink.service.ShortUrlService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class ShortUrlServiceTest extends ShortLinkApplicationTests {

    @Autowired
    @Qualifier("ShortUrlCacheServiceImpl")
    ShortUrlService service;
    @Autowired
    BloomFilterConfiguration param;
    @Autowired
    BloomFilterSet set;

    @Test
    public void saveFunctionTest(){
        UrlResponse url = service.saveUrl("www.repeat.com", "test", 100);
        System.out.println(
                url.getState().getCode() + "; "
                + url.getState().getMassage() +";"
                + url.getUrl() + ";"
        );
    }

    @Test
    public void getFunctionTest(){
        UrlResponse url = service.getOriginUrl("wu80C1");
        System.out.println(
                url.getState().getCode() + "; "
                        + url.getState().getMassage() +";"
                        + url.getUrl() + ";"
        );
    }

}
