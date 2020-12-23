package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.ReviewService;
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

import java.util.Collections;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.PAGE_TWO;
import static com.endava.booksharing.TestConstants.SIZE_TWO;
import static com.endava.booksharing.utils.ReviewTestUtils.PAGEABLE_REVIEWS_RESPONSE_DTO;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_REQUEST_DTO;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_RESPONSE_DTO_ONE;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewRestController.class)
public class ReviewRestControllerTest {

    private final Gson gson = new Gson();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser
    public void shouldReturnReviewsByBookIdDesc() throws Exception {
        when(reviewService.getAllReviewsByBookIdDesc(ID_ONE)).thenReturn(Collections.singletonList(REVIEW_RESPONSE_DTO_ONE));

        mockMvc.perform(get("/books/{id}/review", ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(Collections.singletonList(REVIEW_RESPONSE_DTO_ONE)))));

        verify(reviewService).getAllReviewsByBookIdDesc(ID_ONE);
    }

    @Test
    @WithMockUser
    public void shouldAddReview() throws Exception {
        when(reviewService.save(REVIEW_REQUEST_DTO, ID_ONE)).thenReturn(REVIEW_RESPONSE_DTO_ONE);

        mockMvc.perform(post("/books/{id}/review", ID_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(REVIEW_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(REVIEW_RESPONSE_DTO_ONE))));

        verify(reviewService).save(REVIEW_REQUEST_DTO, ID_ONE);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldReturnAllReviews() throws Exception {
        when(reviewService.getAllReviews(anyInt(), anyInt())).thenReturn(PAGEABLE_REVIEWS_RESPONSE_DTO);

        mockMvc.perform(get("/reviews"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(PAGEABLE_REVIEWS_RESPONSE_DTO)));

        verify(reviewService).getAllReviews(PAGE_TWO, SIZE_TWO);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldDeleteRequest() throws Exception {

        mockMvc.perform(delete("/reviews/{id}", ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build("Review with id " + ID_ONE + " was deleted successfully!"))));
    }
}