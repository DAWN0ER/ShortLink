package com.dawn.shortlink.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

public class ShortUrlRequestBody {

    @JsonProperty(value = "origin_url")
    public String originUrl;
    @JsonProperty(value = "description")
    public String description;
    @JsonProperty(value = "timeout")
    public long timeout;


    public ShortUrlRequestBody(String originUrl, String description, long timeout) {
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
