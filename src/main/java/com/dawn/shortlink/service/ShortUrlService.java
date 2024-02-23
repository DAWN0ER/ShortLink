package com.dawn.shortlink.service;

import com.dawn.shortlink.domain.pojo.ShortUrlInfoVO;
import com.dawn.shortlink.domain.pojo.UrlResponse;

public interface ShortUrlService {
    UrlResponse getOriginUrl(String shortUrl);
    UrlResponse generateUrl(ShortUrlInfoVO urlInfoVO);
}
