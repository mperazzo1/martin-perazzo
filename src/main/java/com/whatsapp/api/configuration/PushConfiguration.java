package com.whatsapp.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "custom.push")
@Validated
@Data
public class PushConfiguration {
    private String server;

    private String path;
}
