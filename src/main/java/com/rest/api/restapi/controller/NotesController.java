package com.rest.api.restapi.controller;

import com.rest.api.restapi.controller.dto.NoteDto;
import com.rest.api.restapi.model.Note;
import com.rest.api.restapi.service.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class NotesController {
    private final NotesService service;

    private NotesController(NotesService service) {
        this.service = service;
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {
        return service.findById(id)
                .map(note -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI("/notes/" + note.getId()))
                                .body(note);
                    } catch (URISyntaxException e ) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/notes")
    public ResponseEntity<Note> save(@RequestBody NoteDto noteDto) {
        Note saveNote = service.save(noteDto);

        try {
            return ResponseEntity
                    .created(new URI("/notes/" + saveNote.getId()))
                    .body(saveNote);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
