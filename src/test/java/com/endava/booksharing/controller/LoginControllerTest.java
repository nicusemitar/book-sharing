package com.endava.booksharing.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate template;


    @Test
    public void shouldBeUnauthenticated() throws Exception {
        mvc.perform(get("/homepage"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void givenAuthRequestOnPrivateServiceShouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth(USER_ONE.getUsername(), USER_ONE.getPassword())
                .getForEntity("/homepage", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
