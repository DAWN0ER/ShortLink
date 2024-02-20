package com.dawn.shortlink.dao.mappers;

import com.dawn.shortlink.domain.ShortUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface UrlMapper {
    void insertUrl(ShortUrl shortUrl);
    ShortUrl selectByShortUrl(String shortUrl);
    void deleteTimeoutUrl(Date date);
}
