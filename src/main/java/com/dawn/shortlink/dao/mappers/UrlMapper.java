package com.dawn.shortlink.dao.mappers;

import com.dawn.shortlink.domain.UrlDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface UrlMapper {
    void insertUrlDO(UrlDO urlDO);
    UrlDO selectByShortUrl(String short_url);
    void deleteTimeoutUrl(Date date);
}
