package com.dawn.shortlink.controller;


import com.dawn.shortlink.domain.pojo.ShortUrlResponseStateEnum;
import com.dawn.shortlink.domain.pojo.UrlResponse;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/to")
public class ShortLinkRedirectController {

    @Autowired
    @Qualifier("ShortUrlCacheServiceImpl")
    ShortUrlService shortUrlService;

    @GetMapping("/{short_url}")
    @ResponseBody
    public UrlResponse redirect(@PathVariable("short_url") String shortUrl, HttpServletResponse response){
        try {
            return shortUrlService.redirectToOrigin(shortUrl,response);
        } catch (IOException e) {
            return new UrlResponse(ShortUrlResponseStateEnum.ERROR,null);
        }
    }

}
