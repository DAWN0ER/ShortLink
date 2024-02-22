package com.dawn.shortlink.service;

import com.dawn.shortlink.domain.pojo.UrlResponse;

public interface ShortUrlService {
    UrlResponse getOriginUrl(String shortUrl);
    UrlResponse saveUrl(String originUrl,String description,long timeout);
}
