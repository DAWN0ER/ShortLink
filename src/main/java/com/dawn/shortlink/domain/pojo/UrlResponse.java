package com.dawn.shortlink.domain.pojo;

public class UrlResponse<T> {

    private final ShortUrlResponseStateEnum state;
    private final T data;

    public UrlResponse(ShortUrlResponseStateEnum state, T data) {
        this.state = state;
        this.data = data;
    }

    public ShortUrlResponseStateEnum getState() {
        return state;
    }

    public T getData() {
        return data;
    }
}
