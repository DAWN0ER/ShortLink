package com.dawn.shortlink;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DaoTest extends ShortLinkApplicationTests{

    @Autowired
    UrlMapper mapper;

    @Test
    public void simpleTest(){
        ShortUrlInfoDTO infoDTO = mapper.selectOneByShortUrl("o1vHa");
        System.out.println(infoDTO.toString());
    }

    @Test
    public void insertTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10); // 测试定时任务
        ShortUrlInfoDTO urlInfoDTO = new ShortUrlInfoDTO("test","www.baidu.com",
                "desc",
                5*1000L);
        mapper.insertUrl(urlInfoDTO);
    }



}
