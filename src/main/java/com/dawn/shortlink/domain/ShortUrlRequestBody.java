package com.dawn.shortlink.domain;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShortUrlRequestBody {

    public String url;
    public String description;
    public long timeout;


    public ShortUrlRequestBody(String url, String description, int timeout) {
        this.url = url;
        this.description = description;
        this.timeout = timeout;
    }

    public ShortUrlRequestBody() {
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
