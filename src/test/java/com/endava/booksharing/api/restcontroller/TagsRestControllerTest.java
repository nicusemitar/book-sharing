package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.service.TagsService;
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

import static com.endava.booksharing.TestConstants.TAG_ONE;
import static com.endava.booksharing.TestConstants.TAG_TYPE_NAME_ONE;
import static com.endava.booksharing.utils.TagsTestUtils.TAGS_RESPONSE_DTO;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TagsRestController.class)
public class TagsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private TagsService tagsService;

    private Gson gson = new Gson();

    @Test
    @WithMockUser
    public void shouldReturnTagsResponseDtoByTagType() throws Exception {
        when(tagsService.getTagsByType(TAG_TYPE_NAME_ONE)).thenReturn((Collections.singletonList(TAGS_RESPONSE_DTO)));

        mockMvc.perform(get("/tags/type/{type}", TAG_TYPE_NAME_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Collections.singletonList(TAGS_RESPONSE_DTO))));
        verify(tagsService).getTagsByType(TAG_TYPE_NAME_ONE);
    }


    @Test
    @WithMockUser
    public void shouldReturnTagsResponseDtoByTagName() throws Exception {
        when(tagsService.getTagsByName(TAG_ONE)).thenReturn((Collections.singletonList(TAGS_RESPONSE_DTO)));

        mockMvc.perform(get("/tags/name/{name}", TAG_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Collections.singletonList(TAGS_RESPONSE_DTO))));
        verify(tagsService).getTagsByName(TAG_ONE);
    }
}
