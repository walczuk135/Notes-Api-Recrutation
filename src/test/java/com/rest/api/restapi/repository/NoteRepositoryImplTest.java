package com.rest.api.restapi.repository;

import com.rest.api.restapi.model.Note;
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
public class NoteRepositoryImplTest {

    @Autowired
    @MockBean
    private NoteRepositoryImpl repository;

    @Test
    void testFindByIdSuccess() {
        // Setup our mock
        Note mockNote = new Note(1, "Note number one", "Note one example lorrem ipsum");
        doReturn(Optional.of(mockNote)).when(repository).findById(1L);

        // Execute the service call
        Optional<Note> returnedNote = repository.findById(1L);

        // Assert the response
        Assertions.assertTrue(returnedNote.isPresent(), "Note was not found");
        Assertions.assertSame(returnedNote.get(), mockNote, "Note should be the same");
    }
}
