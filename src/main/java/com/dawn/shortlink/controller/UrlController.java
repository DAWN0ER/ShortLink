package com.dawn.shortlink.controller;

import com.dawn.shortlink.domain.ShortUrlResponse;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    @Qualifier("shortUrlServiceImpl")
    private ShortUrlService service;

    @RequestMapping(path = "/save_url",method = RequestMethod.POST)
    @ResponseBody
    public ShortUrlResponse saveURL(@RequestParam("URL") String URL, @RequestParam("description") String description,@RequestParam("timeout") long timeout){
        return service.saveUrl(URL,description,timeout);
    }

    @RequestMapping(path = "/get_short_url",method = RequestMethod.POST )
    public ShortUrlResponse getShortUrl(@RequestParam("URL") String URL){
        return service.getShortUrl(URL);
    }


}
