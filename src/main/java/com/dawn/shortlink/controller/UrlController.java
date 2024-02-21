package com.dawn.shortlink.controller;

import com.dawn.shortlink.domain.ShortUrlInfoDTO;
import com.dawn.shortlink.domain.ShortUrlRequestBody;
import com.dawn.shortlink.domain.ShortUrlResponse;
import com.dawn.shortlink.domain.ShortUrlResponseStateEnum;
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
    @Qualifier("shortUrlServiceImpl")
    private ShortUrlService shortUrlService;

    @RequestMapping(path = "/generate_short_url",method = RequestMethod.POST) // 生成短链接 generate_short_url
    @ResponseBody
    public ShortUrlResponse saveURL(@RequestBody ShortUrlRequestBody requestBody){
//        return shortUrlService.saveUrl(requestBody.getUrl(),requestBody.getDescription(),requestBody.getTimeout());
        return new ShortUrlResponse(ShortUrlResponseStateEnum.SUCCESS,
                new ShortUrlInfoDTO(
                        requestBody.getUrl(),
                        requestBody.getUrl(),
                        requestBody.getDescription())); // 测试用的代码
    }

    /*
    TODO 需求有误
     */
    @RequestMapping(path = "/get_short_url",method = RequestMethod.GET )
    @ResponseBody
    public ShortUrlResponse getShortUrl(@RequestParam("origin_url") String url){
        return shortUrlService.getShortUrl(url);
    }


}
