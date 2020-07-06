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
        // Setup our mock
        Note mockNote = new Note(1, "Note number one", "Note one example lorrem ipsum");
        doReturn(Optional.of(mockNote)).when(repository).findById(1L);

        // Execute the service call
        Optional<Note> returnedReview = service.findById(1L);

        // Assert the response
        Assertions.assertTrue(returnedReview.isPresent(), "Review was not found");
        Assertions.assertSame(returnedReview.get(), mockNote, "Review should be the same");
    }
}
