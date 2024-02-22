package com.dawn.shortlink.domain.pojo;

public class UrlResponse {

    private final ShortUrlResponseStateEnum state;
    private final String url;

    public UrlResponse(ShortUrlResponseStateEnum state, String url) {
        this.state = state;
        this.url = url;
    }

    public ShortUrlResponseStateEnum getState() {
        return state;
    }

    public String getUrl() {
        return url;
    }
}
