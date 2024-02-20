package com.dawn.shortlink.service;
import com.dawn.shortlink.domain.ShortUrlResponse;

public interface ShortUrlService {
    ShortUrlResponse getShortUrl(String originUrl);
    ShortUrlResponse saveUrl(String originUrl,String description,long timeout);
}
