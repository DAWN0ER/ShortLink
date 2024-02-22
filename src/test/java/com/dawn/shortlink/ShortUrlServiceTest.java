package com.dawn.shortlink;

import com.dawn.shortlink.service.domain.utils.BloomFilterConfiguration;
import com.dawn.shortlink.service.domain.utils.BloomFilterSet;
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
    public void getFunctionTest(){

    }

    @Test
    public void getBFTest(){

    }

}
