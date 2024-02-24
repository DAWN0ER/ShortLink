package com.dawn.shortlink.controller;

import com.dawn.shortlink.domain.pojo.*;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/shortlink")
public class ShortLinkApiController {

    @Autowired
    @Qualifier("ShortUrlCacheServiceImpl")
    private ShortUrlService shortUrlService;

    @RequestMapping(path = "/generate_short_url",method = RequestMethod.POST) // 生成短链接 generate_short_url
    @ResponseBody
    public UrlResponse saveURL(@RequestBody ShortUrlRequestBody requestBody){
        return shortUrlService.generateUrl(new ShortUrlInfoVO(requestBody));
    }

    @RequestMapping(path = "/get_origin_url",method = RequestMethod.GET )
    @ResponseBody
    public UrlResponse getShortUrl(@RequestParam("short_url") String url){
        return shortUrlService.getOriginUrl(url);
    }

    @GetMapping("/test")
    public String netTest(){
        return "Hello world!";
    }

}
