package com.rest.api.restapi.service;

import com.rest.api.restapi.controller.dto.MapperDto;
import com.rest.api.restapi.controller.dto.NoteDto;
import com.rest.api.restapi.model.Note;
import com.rest.api.restapi.repository.NoteRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    private final NoteRepositoryImpl repository;


    public NotesService(NoteRepositoryImpl repository) {
        this.repository = repository;
    }

    public Optional<Note> findById(long id) {
        return repository.findById(id);
    }

    public Note save(NoteDto noteDto) {
        Note note = MapperDto.mapDtoToNote(noteDto);
        return note;
    }

    public List<Note> findAllNote() {
        return null;
    }
}
