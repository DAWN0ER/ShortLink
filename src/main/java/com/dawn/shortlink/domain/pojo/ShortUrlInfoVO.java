package com.dawn.shortlink.domain.pojo;

public class ShortUrlInfoVO extends ShortUrlRequestBody{

    public ShortUrlInfoVO(ShortUrlRequestBody requestBody){
        super(requestBody.originUrl, requestBody.description, requestBody.timeout);
    }

    public ShortUrlInfoVO(String originUrl, String description, long timeout) {
        super(originUrl, description, timeout);
    }
}
