package com.dawn.shortlink.dao.mappers.domain;

import java.util.Date;

/*
URL 的实体类
 */
public class ShortUrl {
    String short_url;
    String origin_url;
    Date created_time;
    Date expired_time;
    String description;

    public ShortUrl(String short_url, String origin_url, Date created_time, Date expired_time, String description) {
        this.short_url = short_url;
        this.origin_url = origin_url;
        this.created_time = created_time;
        this.expired_time = expired_time;
        this.description = description;
    }

    public String getShort_url() {
        return short_url;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public Date getExpired_time() {
        return expired_time;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "short_url='" + short_url + '\'' +
                ", origin_url='" + origin_url + '\'' +
                ", created_time=" + created_time +
                ", expired_time=" + expired_time +
                ", description='" + description + '\'' +
                '}';
    }
}
