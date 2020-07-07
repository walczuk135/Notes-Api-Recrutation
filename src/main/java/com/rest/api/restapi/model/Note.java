package com.rest.api.restapi.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Note {
    private static long nextId;
    private static final AtomicLong idGenerator = new AtomicLong(1);

    private long id=idGenerator.getAndIncrement();
    private String title;
    private String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
