package com.rest.api.restapi.repository;

import com.rest.api.restapi.model.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NoteRepositoryImpl implements NoteRepository{

    private final List<Note> notes;

    public NoteRepositoryImpl() {
        this.notes = new ArrayList<>();
    }

    public Optional<Note> findById(long id) {
        return notes.stream()
                .filter(note->note.getId()==id)
                .findAny();
    }

    public List<Note> getNotes() {
        return notes;
    }
}
