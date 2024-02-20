package com.dawn.shortlink.domain;

import java.util.Date;

public class UrlVO extends ShortUrl{

    public UrlVO(String short_url, String origin_url, Date created_time, Date expired_time, String description) {
        super(short_url, origin_url, created_time, expired_time, description);
    }
}
