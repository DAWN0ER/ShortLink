package com.dawn.shortlink.domain.pojo;

public class ShortUrlServiceException extends RuntimeException{
    public ShortUrlServiceException(String s) {
        super(s);
    }

    public ShortUrlServiceException() {
        super();
    }
}
