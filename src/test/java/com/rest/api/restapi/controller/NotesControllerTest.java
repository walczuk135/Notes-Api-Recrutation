package com.rest.api.restapi.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotesControllerTest {
    @MockBean
    private NoteService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetReviewByIdFound() throws Exception {
        // Setup our mocked service
        Note mockNote = new Note(1, "Note number one", "Note one example lorrem ipsum");
        doReturn(Optional.of(mockNote)).when(service).findById(1L);

        // Execute the GET request
        mockMvc.perform(get("/note/{id}", 1L))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/notes/"+mockNote.getId()))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1L)))
                .andExpect(jsonPath("$.title", is("Note number one")))
                .andExpect(jsonPath("$.description", is("Note one example lorrem ipsum")));

    }
}
