package com.dawn.shortlink.domain.pojo;

public class RetryGenerateShortUrlFailedException extends RuntimeException{
    public RetryGenerateShortUrlFailedException(String s) {
        super(s);
    }

    public RetryGenerateShortUrlFailedException() {
        super();
    }
}
