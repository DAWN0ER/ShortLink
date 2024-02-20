package com.dawn.shortlink.domain;

public class ShortUrlResponse {

    private final int state;
    private final ShortUrl shortUrl;
    private String description;

    public ShortUrlResponse(int state, ShortUrl shortUrl) {
        this.state = state;
        this.shortUrl = shortUrl;
        switch (state){
            case 1: this.description="已经存在的url"; break;
            case 2: this.description="新url已保存"; break;
            case 0: this.description="没有找到url信息";break;
        }
    }

    public int getState() {
        return state;
    }

    public ShortUrl getShortUrl() {
        return shortUrl;
    }

    public String getDescription() {
        return description;
    }
}
