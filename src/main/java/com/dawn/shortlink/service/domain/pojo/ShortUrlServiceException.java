package com.dawn.shortlink.service.domain.pojo;

public class ShortUrlServiceException extends RuntimeException{
    public ShortUrlServiceException(String s) {
        super(s);
    }

    public ShortUrlServiceException() {
        super();
    }
}
