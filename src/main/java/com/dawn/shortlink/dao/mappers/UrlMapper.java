package com.dawn.shortlink.dao.mappers;

import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface UrlMapper {
    void insertUrl(ShortUrlInfoDTO shortUrlInfoDTO);
    ShortUrlInfoDTO selectOneByShortUrl(String shortUrl);
    void deleteExpired(Date now);
}
