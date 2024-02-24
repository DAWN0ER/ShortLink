package com.dawn.shortlink.service;

import com.dawn.shortlink.domain.pojo.ShortUrlInfoVO;
import com.dawn.shortlink.domain.pojo.UrlResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ShortUrlService {
    UrlResponse getOriginUrl(String shortUrl);
    UrlResponse generateUrl(ShortUrlInfoVO urlInfoVO);
    UrlResponse redirectToOrigin(String shortUrl, HttpServletResponse httpServletResponse) throws IOException;
}
