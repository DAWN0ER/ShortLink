package com.dawn.shortlink.dao.mappers;

import com.dawn.shortlink.domain.pojo.ShortUrlInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UrlMapper {
    void insertUrl(ShortUrlInfoDTO shortUrlInfoDTO);
    ShortUrlInfoDTO selectOneByShortUrl(String shortUrl);
    void deleteExpired();
}
