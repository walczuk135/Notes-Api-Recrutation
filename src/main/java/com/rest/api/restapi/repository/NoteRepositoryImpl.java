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

    public Note save(Note note) {
        notes.add(note);
        return note;
    }

    public List<Note> findAllNotes() {
        return notes;
    }

    public void updateNote(Note note) {
        notes.set((int) (note.getId()-1),note);
        System.out.println(notes);
    }
}
