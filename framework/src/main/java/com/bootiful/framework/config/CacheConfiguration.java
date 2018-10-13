package com.bootiful.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component
//@ConfigurationProperties("cache")
public class CacheConfiguration {

    Map<String, Long> expirations = new HashMap<String, Long>();

    public Map<String, Long> getExpirations() {
        return expirations;
    }

    public void setExpirations(Map<String, Long> expirations) {
        this.expirations = expirations;
    }
}
