package com.rest.api.restapi.controller.dto;

import com.rest.api.restapi.model.Note;

public class MapperDto {
    private MapperDto() {
    }

    public static Note mapDtoToNote(NoteDto noteDto) {
        return new Note(noteDto.getTitle(), noteDto.getDescription());
    }
}
