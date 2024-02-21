package com.dawn.shortlink.domain;

import java.util.Date;

/*
URL 信息的实体类
 */

public class ShortUrlInfoDTO {
    protected String shortUrl;
    protected String originUrl;
    protected String description;
    protected Date createdTime;
    protected  Date expiredTime;

    /*
    默认存活时间为30天
     */
    public ShortUrlInfoDTO(String shortUrl, String originUrl, String description) {
        this(shortUrl,originUrl,description,1000*60*60*24*30L); // 默认30天
    }

    /*
    带过期时间的构造函数
     */
    public ShortUrlInfoDTO(String shortUrl, String originUrl, String description, long timeout){
        this.shortUrl = shortUrl;
        this.originUrl = originUrl;
        this.description = description;
        this.createdTime = new Date();
        this.expiredTime = new Date(createdTime.getTime()+timeout);
    }

    /*
    mybatis 数据库返回使用的构造函数
     */
    public ShortUrlInfoDTO(String shortUrl, String originUrl, String description, Date createdTime, Date expiredTime) {
        this.shortUrl = shortUrl;
        this.originUrl = originUrl;
        this.description = description;
        this.createdTime = createdTime;
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "shortUrl='" + shortUrl + '\'' +
                ", originUrl='" + originUrl + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", expiredTime=" + expiredTime +
                '}';
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }
}
