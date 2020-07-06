package com.rest.api.restapi.controller;

import com.rest.api.restapi.service.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class NotesController {
    private final NotesService service;

    private NotesController(NotesService notesService) {
        this.service = notesService;
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

}
