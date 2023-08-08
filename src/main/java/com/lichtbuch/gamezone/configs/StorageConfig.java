package com.lichtbuch.gamezone.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "storage")
@ConfigurationPropertiesScan
public class StorageConfig {

    private final String location;

    public StorageConfig(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

}
