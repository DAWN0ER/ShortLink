package com.dawn.shortlink.dao.mappers.domain;

import java.util.Date;

public class UrlDO extends ShortUrl {

    public UrlDO(String shortURL, String originURL, Date createdTime, Date expired_time, String description) {
        super(shortURL, originURL, createdTime, expired_time, description);
    }

    public UrlDO(UrlVO vo){
        super(
                vo.getShort_url(),
                vo.getOrigin_url(),
                vo.getCreated_time(),
                vo.getExpired_time(),
                vo.getDescription()
        );
    }


}
