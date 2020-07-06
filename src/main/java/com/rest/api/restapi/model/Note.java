package com.rest.api.restapi.model;

import lombok.Data;

@Data
public class Note {
    private long id;
    private String title;
    private String description;

    public Note(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
