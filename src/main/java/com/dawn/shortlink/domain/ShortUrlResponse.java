package com.dawn.shortlink.domain;

public class ShortUrlResponse {

    private final ShortUrlResponseStateEnum state;
    private final String shortUrl;

    public ShortUrlResponse(ShortUrlResponseStateEnum state, ShortUrlInfoDTO shortUrlInfoDTO) {
        this.state = state;
        this.shortUrl = shortUrlInfoDTO==null?null:shortUrlInfoDTO.getShortUrl();
    }

    public ShortUrlResponseStateEnum getState() {
        return state;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
