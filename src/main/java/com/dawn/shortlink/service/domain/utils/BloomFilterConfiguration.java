package com.dawn.shortlink.service.domain.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "my")
public class BloomFilterConfiguration {
    private List<BloomFilterConfParam> bloomFilters;

    public List<BloomFilterConfParam> getBloomFilters() {
        return bloomFilters;
    }

    public void setBloomFilters(List<BloomFilterConfParam> bloomFilters) {
        this.bloomFilters = bloomFilters;
    }
}


