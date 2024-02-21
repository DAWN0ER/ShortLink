package com.dawn.shortlink.service.impl;

import com.dawn.shortlink.dao.mappers.UrlMapper;
import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.dawn.shortlink.domain.ShortUrlInfoDTO;
import com.dawn.shortlink.domain.ShortUrlResponse;
import com.dawn.shortlink.domain.ShortUrlResponseStateEnum;
import com.dawn.shortlink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service("shortUrlServiceImpl")
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private UrlMapper mapper;

    /*
    @TODO 加入布隆过滤器和redis后业务重写,干脆我换一个实现类算了,好麻烦
     */
    @Override
    public ShortUrlResponse getShortUrl(String originUrl) {
        if(originUrl==null) return null;
        ShortUrlInfoDTO res = mapper.selectByShortUrl(Base62CodeUtil.encode(originUrl));
        if(res == null) return new ShortUrlResponse(ShortUrlResponseStateEnum.NOTFOUND,null);
        else return new ShortUrlResponse(ShortUrlResponseStateEnum.GOAL,res);
    }

    @Override
    public ShortUrlResponse saveUrl(String originUrl, String description,long timeout) {
        if(originUrl==null) return null;
        ShortUrlInfoDTO shortUrlInfoDTO;
        try {
            if (timeout<=0) {
                shortUrlInfoDTO = new ShortUrlInfoDTO(Base62CodeUtil.encode(originUrl), originUrl, description);
            }else{
                shortUrlInfoDTO = new ShortUrlInfoDTO(Base62CodeUtil.encode(originUrl), originUrl, description,timeout);
            }
            mapper.insertUrl(shortUrlInfoDTO);
            return new ShortUrlResponse(ShortUrlResponseStateEnum.SUCCESS, shortUrlInfoDTO);
        }catch (DuplicateKeyException e){ // 短链接重复
            return new ShortUrlResponse(ShortUrlResponseStateEnum.COLLISIONS,null);
        }
    }


}
