package com.dawn.shortlink.service.domain.pojo;

public class LongUrlResponse extends ShortUrlResponse{

    private final String longUrl;

    public LongUrlResponse(ShortUrlResponseStateEnum state, String shortUrl, String longUrl) {
        super(state, shortUrl);
        this.longUrl = longUrl;
    }
}
