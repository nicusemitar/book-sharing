package com.endava.booksharing.api.restcontrollers;


import com.endava.booksharing.api.restcontroller.UserRestController;
import com.endava.booksharing.service.UserService;
import com.endava.booksharing.utils.exceptions.UserCredentialsExceptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.endava.booksharing.utils.UserTestUtils.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sun.plugin2.util.PojoUtil.toJson;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldSaveUser() throws Exception {
        when(userService.saveUser(USER_REQUEST_DTO_PASS)).thenReturn(USER_RESPONSE_DTO);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldThrowUserCredentialsException() throws Exception {
        when(userService.saveUser(USER_REQUEST_DTO_PASS)).thenThrow(new UserCredentialsExceptions("Unable to save user to database due to internal issues"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowMethodNotAllowed() throws Exception {
        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturnBadRequestOnNullPointerException() throws Exception {
        when(userService.saveUser(USER_REQUEST_DTO_PASS)).thenThrow(NullPointerException.class);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(USER_REQUEST_DTO_PASS)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnValidationUsername() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(USER_REQUEST_DTO_USERNAME_FAIL)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").value("Username should contain 3-18 characters!"));
    }
}
