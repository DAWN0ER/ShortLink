package com.dawn.shortlink.controller;

import com.dawn.shortlink.dao.mappers.domain.UrlVO;
import com.dawn.shortlink.service.UrlProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("save")
public class SaveUrlController {

    @Autowired
    UrlProcessService urlProcessService;

    @RequestMapping(path = "/saveURL")
    @ResponseBody
    public UrlVO saveURL(@RequestParam("URL") String URL, @RequestParam("description") String description){
        return urlProcessService.getShortUrlAndSave(URL,description);
    }

    @GetMapping(path = "/hello")
    public void hello(){
        System.out.println("hello world");
    }


}
