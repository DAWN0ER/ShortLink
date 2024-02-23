package com.dawn.shortlink.domain.pojo;

public class UrlResponse {

    private final ShortUrlResponseStateEnum state;
    private final String data;

    public UrlResponse(ShortUrlResponseStateEnum state, String url) {
        this.state = state;
        this.data = url;
    }

    public ShortUrlResponseStateEnum getState() {
        return state;
    }

    public String getData() {
        return data;
    }
}
