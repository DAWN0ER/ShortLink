package com.dawn.shortlink.service;
import com.dawn.shortlink.service.domain.pojo.ShortUrlResponse;

public interface ShortUrlService {
    ShortUrlResponse getOriginUrl(String shortUrl);
    ShortUrlResponse saveUrl(String originUrl,String description,long timeout);
}
