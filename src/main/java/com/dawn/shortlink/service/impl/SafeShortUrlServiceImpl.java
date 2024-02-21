package com.dawn.shortlink.service.impl;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.ShortUrlResponse;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SafeShortUrlServiceImpl")
public class SafeShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private UrlMapper mapper;

    @Override
    public ShortUrlResponse getShortUrl(String originUrl) {
        return null;
    }

    @Override
    public ShortUrlResponse saveUrl(String originUrl, String description, long timeout) {
        return null;
    }


}
