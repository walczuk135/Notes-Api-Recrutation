package com.rest.api.restapi.model;

import lombok.Data;

@Data
public class Note {
    private static long nextId;
    private long id=nextId+1;
    private String title;
    private String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
