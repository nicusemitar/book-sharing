package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.StorageService;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.utils.FileInfoUtils.EXPECTED_FILE_INFO;
import static com.endava.booksharing.utils.FileInfoUtils.EXPECTED_FILE_INFO_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FileController.class)
public class StorageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private StorageService storageService;

    private Gson gson = new Gson();

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldGetAllImages() throws Exception {
        when(this.storageService.loadAll())
                .thenReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

        this.mvc.perform(get("/book/files"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(EXPECTED_FILE_INFO())));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldUpdateImage() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());
        when(this.storageService.update(any(),any()))
                .thenReturn("Photo updated for book with id: " + ID_ONE);

        mvc.perform(MockMvcRequestBuilders.multipart("/book/{bookID}/update-photo", ID_ONE)
                .file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(Response.build("Photo updated for book with id: " + ID_ONE))));
    }
}
