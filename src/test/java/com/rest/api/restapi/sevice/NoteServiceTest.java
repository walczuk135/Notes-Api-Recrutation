package com.rest.api.restapi.sevice;

import com.rest.api.restapi.controller.dto.NoteDto;
import com.rest.api.restapi.model.Note;
import com.rest.api.restapi.repository.NoteRepositoryImpl;
import com.rest.api.restapi.service.NotesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoteServiceTest {
    @Autowired
    private NotesService service;

    @MockBean
    private NoteRepositoryImpl repository;

    @Test
    void testFindByIdSuccess() {
        //given
        Note mockNote = new Note( "Note number one", "Note one example lorrem ipsum");
        given(repository.findById(anyLong())).willReturn(Optional.of(mockNote));

        //when
        Optional<Note> returnedNote = service.findById(1L);

        //then
        Assertions.assertTrue(returnedNote.isPresent());
        Assertions.assertSame(returnedNote.get(), mockNote);
        Assertions.assertSame(returnedNote.get().getId(), mockNote.getId());
        Assertions.assertSame(returnedNote.get().getTitle(), mockNote.getTitle());
        Assertions.assertSame(returnedNote.get().getDescription(), mockNote.getDescription());
    }

    @Test
    void testFindByIdNotFound() {
        //given
        given(repository.findById(1)).willReturn(Optional.empty());

        //when
        Optional<Note> returnedNote = service.findById(1);

        //then
        Assertions.assertFalse(returnedNote.isPresent());
    }

    @Test
    void testSaveNote() {
        //given
        NoteDto noteDto=new NoteDto("Note number one", "Note one example lorrem ipsum");
        Note note=new Note("Note number one", "Note one example lorrem ipsum");
        given(repository.save(note)).willReturn(note);

        //when
        Note returnedNote = service.save(noteDto);

        //then
        Assertions.assertNotNull(returnedNote);
        Assertions.assertEquals(returnedNote.getId(),note.getId());
    }

    @Test
    void testFindAllNote() {
        //given
        Note mockNote1 = new Note( "Note number one", "Note one example lorrem ipsum");
        mockNote1.setId(1L);
        Note mockNote2 = new Note( "Note number one", "Note one example lorrem ipsum");
        mockNote1.setId(2L);
        Note mockNote3 = new Note( "Note number one", "Note one example lorrem ipsum");
        mockNote3.setId(3L);
        List<Note> noteList= Arrays.asList(mockNote1,mockNote2,mockNote3);
        given(repository.findByAll()).willReturn(noteList);

        //when
        List<Note> returnedNotes = service.findAllNote();

        //then
        assertThat(noteList).hasSize(3);
        assertThat(noteList).contains(mockNote1,mockNote2,mockNote3);
        assertThat(noteList).element(1).isEqualTo(mockNote1);
    }

}
