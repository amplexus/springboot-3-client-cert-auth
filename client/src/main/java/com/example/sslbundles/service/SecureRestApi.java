package com.example.sslbundles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecureRestApi {

    @Autowired
    private RestTemplate restTemplate;

    public String fetchData(String dataId) {
        log.info("SecureRestApi: fetching data with id {}", dataId);
        log.info("SecureRestApi: using restTemplate: " + restTemplate);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://localhost:8443/greeting?name=test123", // Hostname must match certificate CN or SAN
                HttpMethod.GET,
                null,
                String.class,
                dataId
        );
        log.info("SecureRestApi: fetched response {}", response);
        return response.getBody();
    }
}
