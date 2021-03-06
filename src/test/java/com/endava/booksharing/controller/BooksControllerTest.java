package com.endava.booksharing.controller;

import com.endava.booksharing.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BooksController.class)
public class BooksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser(authorities = "USER")
    void shouldReturnAllBooksPage() throws Exception {
        mockMvc.perform(get("/all-books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("allBooksPage"));
    }
}
