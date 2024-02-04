package com.springboot.springbootsslbundles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecureServiceRestApi {

    @Autowired
    private RestTemplate restTemplate;

    public String fetchData(String dataId) {
        log.warn("CRJ::SecureServiceRestApi: using restTemplate: " + restTemplate);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://localhost:8443/greeting?name=test123", // Must match certificate CN or SAN
                HttpMethod.GET,
                null,
                String.class,
                dataId
        );
        log.warn("CRJ::SecureServiceRestApi: fetched data with id {}", dataId);
        return response.getBody();
    }
}
