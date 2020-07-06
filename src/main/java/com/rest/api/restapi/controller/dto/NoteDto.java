package com.rest.api.restapi.controller.dto;

import lombok.Data;

@Data
public class NoteDto {
    private String title;
    private String description;

    public NoteDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public NoteDto() {
    }
}
