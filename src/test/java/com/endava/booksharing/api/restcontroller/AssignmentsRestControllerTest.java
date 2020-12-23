package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.AssignmentsService;
import com.endava.booksharing.service.TimeExtendService;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import com.endava.booksharing.utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.INVALID_ID;
import static com.endava.booksharing.utils.AssignmentsTestUtils.ASSIGNMENTS_RESPONSE_DTO;
import static com.endava.booksharing.utils.TimeExtendTestUtils.*;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
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
@WebMvcTest(AssignmentsRestController.class)
public class AssignmentsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentsService assignmentsService;

    @MockBean
    private TimeExtendService timeExtendService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGetAssignmentsByUser() throws Exception {
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(assignmentsService.getAssignmentsByUser()).
                thenReturn(Collections.singletonList(ASSIGNMENTS_RESPONSE_DTO));

        mockMvc.perform(get("/assignments/current-user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Collections.singletonList(ASSIGNMENTS_RESPONSE_DTO))));
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldAssignBook() throws Exception {
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(assignmentsService.saveAssigmentForCurrentUserForBookWithID(ID_ONE)).
                thenReturn(ASSIGNMENTS_RESPONSE_DTO);

        mockMvc.perform(post("/assignments/{bookId}/assign",ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(ASSIGNMENTS_RESPONSE_DTO)));
    }

    @Test
    @WithMockUser
    void shouldAddTimeExtend() throws Exception {
        when(timeExtendService.save(TIME_EXTEND_REQUEST_DTO, ID_ONE)).thenReturn(TIME_EXTEND_RESPONSE_DTO);

        mockMvc.perform(post("/assignments/{id}/extends", ID_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(TIME_EXTEND_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(TIME_EXTEND_RESPONSE_DTO))));

        verify(timeExtendService).save(TIME_EXTEND_REQUEST_DTO, ID_ONE);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnJsonWithPageableTimeExtendRequestResponseDto() throws Exception {
        when(timeExtendService.getAllRequests(anyInt(), anyInt())).thenReturn(PAGEABLE_TIME_EXTEND_RESPONSE_DTO);

        mockMvc.perform(get("/assignments/extends"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(PAGEABLE_TIME_EXTEND_RESPONSE_DTO)));

        verify(timeExtendService).getAllRequests(anyInt(), anyInt());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldDeleteRequest() throws Exception {

        mockMvc.perform(delete("/assignments/extends/{id}", ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build("Request with id " + ID_ONE + " was declined"))));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldThrowExceptionOnReturnJsonWithPageableTimeExtendRequestResponseDto() throws Exception {

        mockMvc.perform(get("/assignments/extends").param("page", String.valueOf(INVALID_ID)))
                .andDo(print())
                .andExpect(status().isBadGateway())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldAcceptTimeExtendRequest() throws Exception {
        when(timeExtendService.acceptRequest(ID_ONE, TIME_EXTEND_REQUEST_DTO_TWO)).thenReturn(Collections.singletonList(ASSIGNMENTS_RESPONSE_DTO));

        mockMvc.perform(post("/assignments/extends/{id}", ID_ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(TIME_EXTEND_REQUEST_DTO_TWO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build(Collections.singletonList(ASSIGNMENTS_RESPONSE_DTO)))));

        verify(timeExtendService).acceptRequest(ID_ONE, TIME_EXTEND_REQUEST_DTO_TWO);
    }
}