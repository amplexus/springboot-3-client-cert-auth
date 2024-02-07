package com.example.springbootsslbundles;

/*
import javax.net.ssl.TrustManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
*/

import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecureRestTemplateConfig {

    // THIS WORKS! But only with p12 certificate, and ONLY if the server cert is trusted by the client
    // NOTE THIS RELIES ON SSL BUNDLES CONFIG IN APP YAML
    @Bean
    public RestTemplate mySecureRestTemplate(RestTemplateBuilder builder, SslBundles sslBundles) {
        log.info("CRJ::SecureRestTemplateConfig: Building RestTemplate");
        SslBundle sslBundle = sslBundles.getBundle("secure-client");

        return builder.setSslBundle(sslBundle).build();
        //return builder.rootUri("https://localhost:8443").setSslBundle(sslBundle).build();
    }

    /*
    // THIS APPROACH ALLOWS THE SERVER CERT TO NOT TRUSTED BY THE CLIENT - USEFUL FOR TESTING LOCALLY
    // NOTE THIS DOESNT USE SSL BUNDLES CONFIG IN APP YAML

    private SSLContext sslContext;
    private String KEYSTORE_PASSWORD = "secret";

    @Bean
    public RestTemplate mySecureRestTemplate() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true; // Don't validate the certificate

        log.warn("CRJ::SecureRestTemplateConfig: Building sslContext");
        this.sslContext = SSLContextBuilder 
            .create() 
            .loadKeyMaterial(ResourceUtils.getFile("classpath:client-cert.p12"), KEYSTORE_PASSWORD.toCharArray(), KEYSTORE_PASSWORD.toCharArray())
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();
        log.warn("CRJ::SecureRestTemplateConfig: created sslContext: " + this.sslContext);
        final SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create().setSslContext(this.sslContext).build();
        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslSocketFactory).build();
        HttpClient httpClient = HttpClients.custom().setConnectionManager(cm).evictExpiredConnections().build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        log.warn("CRJ::SecureRestTemplateConfig: created RestTemplate");
        RestTemplate restTemplate = new RestTemplate(factory);
        log.warn("CRJ::SecureRestTemplateConfig: created restTemplate: " +restTemplate);
        return restTemplate;
    }
    */
}
