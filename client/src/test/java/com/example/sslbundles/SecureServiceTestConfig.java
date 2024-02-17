package com.example.sslbundles;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecureServiceTestConfig {

    @Bean
    public RestTemplate mySecureRestTemplate(RestTemplateBuilder builder) {
        return builder.build(); // Skip/bypass sslbundles configuration which isn't needed for unit testing
    }
}

