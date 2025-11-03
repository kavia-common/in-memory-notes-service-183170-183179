package com.example.notes.controller;

import com.example.notes.dto.NoteRequest;
import com.example.notes.dto.NoteResponse;
import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * REST controller exposing Notes API under /api/notes.
 */
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    /**
     * Create a new note.
     * POST /api/notes
     */
    @PostMapping
    public ResponseEntity<NoteResponse> create(@Valid @RequestBody NoteRequest request) {
        Note note = service.create(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(note.getId())
                .toUri();

        NoteResponse resp = NoteResponse.of(note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt(), note.getUpdatedAt());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(resp, headers, HttpStatus.CREATED);
    }

    /**
     * Get a single note by id.
     * GET /api/notes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> get(@PathVariable Long id) {
        Note note = service.get(id);
        NoteResponse resp = NoteResponse.of(note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt(), note.getUpdatedAt());
        return ResponseEntity.ok(resp);
    }

    /**
     * Update an existing note by id.
     * PUT /api/notes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> update(@PathVariable Long id, @Valid @RequestBody NoteRequest request) {
        Note note = service.update(id, request);
        NoteResponse resp = NoteResponse.of(note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt(), note.getUpdatedAt());
        return ResponseEntity.ok(resp);
    }

    /**
     * Delete a note by id.
     * DELETE /api/notes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * List notes with optional filtering and pagination.
     * GET /api/notes?search=&sort=&order=&page=&size=
     */
    @GetMapping
    public ResponseEntity<List<NoteResponse>> list(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size
    ) {
        List<NoteResponse> list = service.list(search, sort, order, page, size)
                .stream()
                .map(n -> NoteResponse.of(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt()))
                .toList();
        return ResponseEntity.ok(list);
    }
}
