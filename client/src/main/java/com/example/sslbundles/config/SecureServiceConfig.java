package com.example.sslbundles.config;

import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecureServiceConfig {

    // THIS WORKS! But only with p12 certificate, and ONLY if the server cert is trusted by the client
    // NOTE THIS RELIES ON SSL BUNDLES CONFIG IN APP YAML
    @Bean
    public RestTemplate mySecureRestTemplate(RestTemplateBuilder builder, SslBundles sslBundles) {
        log.info("CRJ::SecureRestTemplateConfig: Building RestTemplate");
        SslBundle sslBundle = sslBundles.getBundle("secure-client");

        return builder.setSslBundle(sslBundle).build();
    }

}
