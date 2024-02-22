package com.dawn.shortlink.service.domain.pojo;

public class ShortUrlResponse {

    private final ShortUrlResponseStateEnum state;
    private final String shortUrl;

    public ShortUrlResponse(ShortUrlResponseStateEnum state, String shortUrl) {
        this.state = state;
        this.shortUrl = shortUrl;
    }

    public ShortUrlResponseStateEnum getState() {
        return state;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
