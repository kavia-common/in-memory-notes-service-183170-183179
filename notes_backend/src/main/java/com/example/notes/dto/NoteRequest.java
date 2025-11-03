package com.example.notes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body for creating/updating a note.
 */
public class NoteRequest {

    @NotBlank(message = "title must not be blank")
    @Size(min = 1, max = 200, message = "title length must be between 1 and 200")
    private String title;

    @Size(max = 5000, message = "content length must be at most 5000 characters")
    private String content;

    public NoteRequest() {}

    public NoteRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public NoteRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteRequest setContent(String content) {
        this.content = content;
        return this;
    }
}
