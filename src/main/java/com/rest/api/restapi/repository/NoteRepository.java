package com.rest.api.restapi.repository;

import com.rest.api.restapi.model.Note;

import java.util.Optional;

public interface NoteRepository {
    Optional<Note> findById(Long id);

}
