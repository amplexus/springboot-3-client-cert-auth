package com.example.springbootsslbundles;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

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

import lombok.extern.slf4j.Slf4j;

// @SpringBootTest
@ContextConfiguration(classes = SecureRestTemplateTestConfig.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(MyController.class)
@Import(MyController.class)
@Slf4j
@WithMockUser
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestTemplateBuilder builder;
    
    @MockBean
	  private SecureServiceRestApi secureService;

    @BeforeEach
    void setMockOutput() {
        when(secureService.fetchData(ArgumentMatchers.anyString()))
        .thenReturn("Hello world");
    }

    @Test 
    void someTest() throws Exception {
        mvc.perform(get("/hello")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content").value("Hello, World!"));
          // .andExpect(content().string("Hello, World!"));
    }

}
