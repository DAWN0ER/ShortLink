package com.dawn.shortlink.domain.pojo;

public class ShortUrlRequestBody {

    public String originUrl;
    public String description;
    public long timeout;


    public ShortUrlRequestBody(String originUrl, String description, int timeout) {
        this.originUrl = originUrl;
        this.description = description;
        this.timeout = timeout;
    }

    public ShortUrlRequestBody() {
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getDescription() {
        return description;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
