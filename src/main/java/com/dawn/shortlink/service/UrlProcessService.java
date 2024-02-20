package com.dawn.shortlink.service;

import com.dawn.shortlink.dao.ShortUrlDao;
import com.dawn.shortlink.domain.Base62Code;
import com.dawn.shortlink.domain.UrlDO;
import com.dawn.shortlink.domain.UrlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UrlProcessService {

    @Autowired
    Base62Code coder;
    @Autowired
    ShortUrlDao urlDao;

    public String getShortUrl(String originUrl){
        return coder.encode(originUrl);
    }

    public UrlVO getShortUrlAndSave(String originUrl){
        return getShortUrlAndSave(originUrl,null);
    }

    public UrlVO getShortUrlAndSave(String originUrl, String description){
        Calendar calendar = Calendar.getInstance();
        Date createdTime = new Date();
        calendar.setTime(createdTime);
        calendar.add(Calendar.MONTH,1);
        Date deadTime = calendar.getTime();
        UrlVO urlVO = new UrlVO(getShortUrl(originUrl),originUrl,createdTime,deadTime,description);
        try {
            urlDao.saveUrlDo(new UrlDO(urlVO));
        }catch (Exception e){
            e.printStackTrace();
            return new UrlVO(urlVO.getShort_url(),urlVO.getOrigin_url(),
                    null,null,"重复URL");
        }
        return urlVO;
    }



}
