package com.rest.api.restapi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.restapi.controller.dto.NoteDto;
import com.rest.api.restapi.model.Note;
import com.rest.api.restapi.service.NotesService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
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
    private NotesService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetNoteByIdFound() throws Exception {
        //given
        Note mockNote = new Note("Note number one", "Note one example lorrem ipsum");
        given(service.findById(anyLong())).willReturn(Optional.of(mockNote));

        //then
        mockMvc.perform(get("/notes/{id}", 1L))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/notes/" + mockNote.getId()))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Note number one")))
                .andExpect(jsonPath("$.description", is("Note one example lorrem ipsum")));
    }

    @Test
    void testGetNoteByIdNotFound() throws Exception {
        //given
        given(service.findById(anyLong())).willReturn(Optional.empty());

        //then
        mockMvc.perform(get("/notes/{id}", 1L))
                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateNote() throws Exception {
        //given
        NoteDto noteDto = new NoteDto("Note number one", "Note one example lorrem ipsum");
        Note mockNote = new Note("Note number one", "Note one example lorrem ipsum");
        given(service.save(noteDto)).willReturn(mockNote);

        //then
        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(noteDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/notes/" + mockNote.getId()))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Note number one")))
                .andExpect(jsonPath("$.description", is("Note one example lorrem ipsum")));
    }

    @Test
    void testGetAllNote() throws Exception {
        //given
        Note mockNote1 = new Note("Note number one", "Note one example lorrem ipsum");
        Note mockNote2 = new Note("Note number two", "Note one example lorrem ipsum");
        Note mockNote3 = new Note("Note number three", "Note one example lorrem ipsum");
        List<Note> listNotes=Arrays.asList(mockNote1,mockNote2,mockNote3);
        given(service.findAllNote()).willReturn(listNotes);


        //then
        mockMvc.perform(get("/notes/"))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/notes/"))

                // Validate the returned fields
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].title", is("Note number one")))
                .andExpect(jsonPath("$.[0].description", is("Note one example lorrem ipsum")));
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
