package com.rest.api.restapi.sevice;

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

import java.util.Optional;

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
        Note mockNote = new Note(1, "Note number one", "Note one example lorrem ipsum");
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
        //Given
        given(repository.findById(1)).willReturn(Optional.empty());

        //when
        Optional<Note> returnedNote = service.findById(1);

        //then
        Assertions.assertFalse(returnedNote.isPresent());
    }

}
