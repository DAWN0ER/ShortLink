package com.dawn.shortlink.dao;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.dao.mappers.domain.UrlDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShortUrlDao {

    @Autowired
    private UrlMapper urlMapper;

    public void saveUrlDo(UrlDO urlDO) throws Exception {
        if(urlMapper.selectByShortUrl(urlDO.getShort_url())==null){
            urlMapper.insertUrlDO(urlDO);
        }else throw new Exception("Repeated url");
    }

    public UrlDO selectByShortUrl(String url){
        return urlMapper.selectByShortUrl(url);
    }


}
