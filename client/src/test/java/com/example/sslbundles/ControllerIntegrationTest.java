package com.example.sslbundles;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sslbundles.controller.SecureServiceController;
import com.example.sslbundles.service.SecureRestApi;

import lombok.extern.slf4j.Slf4j;

@ContextConfiguration(classes = SecureServiceTestConfig.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(SecureServiceController.class)
@Import(SecureServiceController.class)
@Slf4j
@WithMockUser
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestTemplateBuilder builder;
    
    @MockBean
	private SecureRestApi secureService;

    @BeforeEach
    void setMockOutput() {
        when(secureService.fetchData(ArgumentMatchers.anyString()))
        .thenReturn("Hello world");
    }

    @Test 
    void someTest() throws Exception {
        log.info("executing someTest");
        mvc.perform(get("/hello")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

}
