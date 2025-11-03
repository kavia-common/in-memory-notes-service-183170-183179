package com.example.notes.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain model representing a Note held in memory.
 */
public class Note {
    private Long id;
    private String title;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;

    public Note() {}

    public Note(Long id, String title, String content, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Note of(Long id, String title, String content, Instant createdAt, Instant updatedAt) {
        return new Note(id, title, content, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public Note setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Note setContent(String content) {
        this.content = content;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Note setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Note setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
