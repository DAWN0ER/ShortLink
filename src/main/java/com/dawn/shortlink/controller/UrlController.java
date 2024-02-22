package com.dawn.shortlink.controller;

import com.dawn.shortlink.service.domain.pojo.ShortUrlRequestBody;
import com.dawn.shortlink.service.domain.pojo.ShortUrlResponse;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/*
@TODO
 */
@RestController
@RequestMapping("/shortlink")
public class UrlController {

    @Autowired
    @Qualifier("ShortUrlCacheServiceImpl")
    private ShortUrlService shortUrlService;

    @RequestMapping(path = "/generate_short_url",method = RequestMethod.POST) // 生成短链接 generate_short_url
    @ResponseBody
    public ShortUrlResponse saveURL(@RequestBody ShortUrlRequestBody requestBody){
//        return shortUrlService.saveUrl(requestBody.getUrl(),requestBody.getDescription(),requestBody.getTimeout());
        return null;
    }

    /*
    TODO 需求有误
     */
    @RequestMapping(path = "/get_origin_url",method = RequestMethod.GET )
    @ResponseBody
    public ShortUrlResponse getShortUrl(@RequestParam("short_url") String url){
        return shortUrlService.getOriginUrl(url);
    }


}
