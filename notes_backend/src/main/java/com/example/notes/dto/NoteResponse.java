package com.example.notes.dto;

import java.time.Instant;

/**
 * Response body for returning a note to the client.
 */
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;

    public NoteResponse() {}

    public NoteResponse(Long id, String title, String content, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // PUBLIC_INTERFACE
    public static NoteResponse of(Long id, String title, String content, Instant createdAt, Instant updatedAt) {
        /** Build a NoteResponse instance. */
        return new NoteResponse(id, title, content, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public NoteResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoteResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public NoteResponse setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public NoteResponse setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
