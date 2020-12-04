package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.service.AssignmentsService;
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
import java.util.Arrays;
import java.util.Collections;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.utils.AssignmentsTestUtils.ASSIGNMENTS_RESPONSE_DTO;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private UserDetailsServiceImpl userDetailsService;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGetAssignmentsByUserId() throws Exception {
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(assignmentsService.getAssignmentsByUserId(ID_ONE)).
                thenReturn(Arrays.asList(ASSIGNMENTS_RESPONSE_DTO));

        mockMvc.perform(get("/assignments/current-user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Collections.singletonList(ASSIGNMENTS_RESPONSE_DTO))));
    }
}