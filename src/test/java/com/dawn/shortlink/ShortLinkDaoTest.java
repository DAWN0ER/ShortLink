package com.dawn.shortlink;

import com.dawn.shortlink.dao.ShortUrlDao;
import com.dawn.shortlink.domain.UrlDO;
import com.dawn.shortlink.service.UrlProcessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortLinkDaoTest extends ShortLinkApplicationTests{

    @Autowired
    private ShortUrlDao dao;
    @Autowired
    private UrlProcessService service;

    @Test
    public void daoTest(){
        System.out.println("测试开始");
        UrlDO urlDO = new UrlDO(service.getShortUrlAndSave(UUID.randomUUID().toString().replace('-','.')+".com"));
        System.out.println(urlDO.toString());
        UrlDO res = dao.selectByShortUrl(urlDO.getShort_url());
        System.out.println(res.toString());
        System.out.println("测试结束");
    }



}
