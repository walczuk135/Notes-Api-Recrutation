package com.rest.api.restapi.repository;

import com.rest.api.restapi.model.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@Repository
public class NoteRepositoryImpl implements NoteRepository{

    private List<Note> notes;

    public NoteRepositoryImpl() {
        this.notes = new ArrayList<>();
    }

    public Optional<Note> findById(Long id) {
        return null;
    }
}
