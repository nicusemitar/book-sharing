package com.endava.booksharing.api.restcontroller;


import com.endava.booksharing.service.UserDetailsServiceImpl;
import com.endava.booksharing.service.UserService;
import com.endava.booksharing.utils.exceptions.UserCredentialsExceptions;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.endava.booksharing.utils.UserTestUtils.USER_REQUEST_DTO_PASS;
import static com.endava.booksharing.utils.UserTestUtils.USER_REQUEST_DTO_USERNAME_FAIL;
import static com.endava.booksharing.utils.UserTestUtils.USER_RESPONSE_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserService userService;

    private Gson gson = new Gson();

    @Test
    void shouldSaveUser() throws Exception {
        when(userService.saveUser(USER_REQUEST_DTO_PASS)).thenReturn(USER_RESPONSE_DTO);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldThrowUserCredentialsException() throws Exception {
        when(userService.saveUser(USER_REQUEST_DTO_PASS)).thenThrow(new UserCredentialsExceptions("Unable to save user to database due to internal issues"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowMethodNotAllowed() throws Exception {
        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturnBadRequestOnNullPointerException() throws Exception {
        when(userService.saveUser(USER_REQUEST_DTO_PASS)).thenThrow(NullPointerException.class);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnValidationUsername() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(USER_REQUEST_DTO_USERNAME_FAIL)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").value("Field: username;  Message: Username should contain 3-18 characters!"));
    }
}