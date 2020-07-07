package com.rest.api.restapi.controller;

import com.rest.api.restapi.controller.dto.NoteDto;
import com.rest.api.restapi.model.Note;
import com.rest.api.restapi.service.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
                    } catch(URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<>(service.findAllNote(), HttpStatus.OK);
    }

    @PostMapping("/notes")
    public ResponseEntity<Note> save(@RequestBody NoteDto noteDto) {
        Note saveNote = service.save(noteDto);

        try {
            return ResponseEntity
                    .created(new URI("/notes/" + saveNote.getId()))
                    .body(saveNote);
        } catch(URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<Void> update(@RequestBody NoteDto noteDto, @PathVariable Long id) {
        ///  Optional<Note> note = service.findById(id);
            if(service.findById(id).isPresent()) {
                service.update(noteDto, id);
                return ResponseEntity.noContent().build();
            }
            Note save = service.save(noteDto);
            return ResponseEntity.created(URI.create(String.format("/notes/%d", save.getId()))).build();
    }

//    @PutMapping("/notes/{id}")
//    public ResponseEntity<Void> update(@RequestBody NoteDto noteDto, @PathVariable Long id) {
//        ///  Optional<Note> note = service.findById(id);
//        Optional<Note> byId = service.findById(id);
//        if(byId.get() == null){
//            service.save(noteDto);
//            return ResponseEntity.created(URI.create(String.format("/todos/%d", id))).build();
//        }
//        service.update(noteDto,id);
//        return ResponseEntity.noContent().build();
//    }
}
