package com.dawn.shortlink.service.impl;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.Base62CodeUtil;
import com.dawn.shortlink.domain.ShortUrl;
import com.dawn.shortlink.domain.ShortUrlResponse;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service("shortUrlServiceImpl")
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private UrlMapper mapper;

    @Override
    public ShortUrlResponse getShortUrl(String originUrl) {
        if(originUrl==null) return null;
        ShortUrl res = mapper.selectByShortUrl(Base62CodeUtil.encode(originUrl));
        if(res == null) return new ShortUrlResponse(0,null);
        else return new ShortUrlResponse(1,res);
    }

    @Override
    public ShortUrlResponse saveUrl(String originUrl, String description,long timeout) {
        if(originUrl==null) return null;
        ShortUrl shortUrl;
        try {
            if (timeout<=0) {
                shortUrl = new ShortUrl(Base62CodeUtil.encode(originUrl), originUrl, description);
            }else{
                shortUrl = new ShortUrl(Base62CodeUtil.encode(originUrl), originUrl, description,timeout);
            }
            mapper.insertUrl(shortUrl);
            return new ShortUrlResponse(2,shortUrl);
        }catch (DuplicateKeyException e){ // 短链接重复
            return getShortUrl(originUrl);
        }
    }


}
