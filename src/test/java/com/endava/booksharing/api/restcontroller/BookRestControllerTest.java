package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.BookService;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_DELETED_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_NOT_DELETED_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_REQUEST_DTO;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.DELETE_BOOK_REQUEST_DTO;
import static com.endava.booksharing.utils.BookTestUtils.TO_UPDATE_BOOK_REQUEST_DTO;
import static com.endava.booksharing.utils.BookTestUtils.UPDATED_BOOK_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.FILTER_DTO_ONE;
import static com.endava.booksharing.utils.BooksServiceTestUtils.PAGEABLE_BOOKS_RESPONSE_DTO;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private BookService bookService;

    private Gson gson = new Gson();

    @Test
    @WithMockUser
    public void shouldReturnNotDeletedBookDetails() throws Exception {
        when(bookService.getBook(ID_ONE)).thenReturn(BOOK_NOT_DELETED_RESPONSE_DTO);

        mockMvc.perform(get("/books/{id}", ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(BOOK_NOT_DELETED_RESPONSE_DTO))));
        verify(bookService).getBook(ID_ONE);
    }

    @Test
    @WithMockUser
    public void shouldReturnDeletedBookDetails() throws Exception {
        when(bookService.getBook(ID_ONE)).thenReturn(BOOK_DELETED_RESPONSE_DTO);

        mockMvc.perform(get("/books/{id}", ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(BOOK_DELETED_RESPONSE_DTO))));
        verify(bookService).getBook(ID_ONE);
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldSaveBook() throws Exception {
        when(bookService.saveBook(BOOK_REQUEST_DTO)).thenReturn(BOOK_RESPONSE_DTO);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(BOOK_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(BOOK_RESPONSE_DTO))));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldDeleteBook() throws Exception {
        when(bookService.deleteBook(DELETE_BOOK_REQUEST_DTO)).thenReturn(BOOK_RESPONSE_DTO);

        mockMvc.perform(delete("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(DELETE_BOOK_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(BOOK_RESPONSE_DTO))));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldUpdateBook() throws Exception {
        when(bookService.updateBook(ID_ONE, TO_UPDATE_BOOK_REQUEST_DTO)).thenReturn(UPDATED_BOOK_RESPONSE_DTO);

        mockMvc.perform(post("/books/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(TO_UPDATE_BOOK_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(UPDATED_BOOK_RESPONSE_DTO))));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldReturnJsonWithPageableBooksResponseDto() throws Exception {
        when(bookService.getBooks(anyString(), anyInt(), anyInt(), anyString())).thenReturn(PAGEABLE_BOOKS_RESPONSE_DTO);

        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(PAGEABLE_BOOKS_RESPONSE_DTO)));

        verify(bookService).getBooks("", 0, 15, "title");
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldReturnJsonWithFilteredPageableBooksResponseDto() throws Exception {
        when(bookService.getFilteredBooks(anyString(), anyString(), anySet(),anySet(), anyString(),
                anyString(), anyInt(), anyInt(), anyString())).thenReturn(PAGEABLE_BOOKS_RESPONSE_DTO);

        mockMvc.perform(post("/books/filter")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(FILTER_DTO_ONE)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}