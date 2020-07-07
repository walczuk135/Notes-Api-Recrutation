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
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    void testGetNoteByIdFound() throws Exception {
        //given
        Note mockNote = new Note("Note number one", "Note one example lorrem ipsum");
        mockNote.setId(1L);
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
        mockNote.setId(1L);
        mockNote.setId(1L);
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
        mockNote1.setId(1L);
        Note mockNote2 = new Note("Note number two", "Note one example lorrem ipsum");
        mockNote2.setId(2L);
        Note mockNote3 = new Note("Note number three", "Note one example lorrem ipsum");
        mockNote3.setId(3L);
        List<Note> listNotes=Arrays.asList(mockNote1,mockNote2,mockNote3);
        given(service.findAllNote()).willReturn(listNotes);

        //then
        mockMvc.perform(get("/notes/"))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].title", is("Note number one")))
                .andExpect(jsonPath("$.[0].description", is("Note one example lorrem ipsum")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].title", is("Note number two")))
                .andExpect(jsonPath("$.[1].description", is("Note one example lorrem ipsum")))
                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[2].title", is("Note number three")))
                .andExpect(jsonPath("$.[2].description", is("Note one example lorrem ipsum")));
    }

    @Test
    public void shouldUpdateNote() throws Exception {
        // Given
        Note mockNote = new Note("Note number one", "Note one example lorrem ipsum");
        mockNote.setId(1L);
        given(service.findById(1L)).willReturn(Optional.of(mockNote));
        // When
        ResultActions result = mockMvc.perform(put("/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockNote)));
        // Then
        result.andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenTryPartialUpdateNoteWhichNotExists() throws Exception {
        //given
        given(service.findById(1)).willReturn(null);
        Map<String, Object> updates = new HashMap<>();
        updates.put("description", "new description");
        //when
        ResultActions result = this.mockMvc.perform(patch("/todos/","10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updates)));
        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void shouldPartialUpdateNote() throws Exception {
        //given
        Note note = new Note("title", "desc");
        note.setId(1L);
        given(service.findById(1L)).willReturn(Optional.of(note));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("title", "new title");
        updates.put("description", "new description");
        //when
        ResultActions result = mockMvc.perform(patch("/note/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updates)));
        //then
        result.andExpect(status().isNoContent())
                .andExpect(status().reason("Note partial updated!"));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
