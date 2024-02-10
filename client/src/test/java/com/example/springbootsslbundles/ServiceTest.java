package com.example.springbootsslbundles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ContextConfiguration(classes = SecureRestTemplateTestConfig.class)
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @MockBean
    private RestTemplateBuilder builder;
    
    @MockBean
    private RestTemplate restTemplate;
    
    private ResponseEntity<String> responseEntity = new ResponseEntity<String>("Hello world", new HttpHeaders(), HttpStatusCode.valueOf(200));

    @InjectMocks
    private SecureServiceRestApi secureService = new SecureServiceRestApi();

    @BeforeEach
    void setMockOutput() {
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any(),
                ArgumentMatchers.anyString())
        ).thenReturn(responseEntity);
    }

    @Test
    void testGet() {
        assertEquals("Hello world", secureService.fetchData("some string"));
    }
}
