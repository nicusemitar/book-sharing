package com.endava.booksharing.controller;

import com.endava.booksharing.service.BookService;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser
    public void shouldShowBookPage() throws Exception {
        when(bookService.checkIfBookIsNotDeleted(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));

        mockMvc.perform(get("/book/{id}", ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("book"));
    }

    @Test
    @WithMockUser
    public void shouldShowAllBooksPage() throws Exception {
        when(bookService.checkIfBookIsNotDeleted(ID_ONE)).thenReturn(Optional.empty());

        mockMvc.perform(get("/book/{id}", ID_ONE))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/all-books"));
    }
}