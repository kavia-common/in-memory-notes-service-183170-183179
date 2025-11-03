package com.example.notes.service;

import com.example.notes.dto.NoteRequest;
import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import com.example.notes.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service layer for notes business logic.
 */
@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    // PUBLIC_INTERFACE
    public Note create(NoteRequest req) {
        /** Create a new note from the request DTO. */
        var note = new Note(null, req.getTitle().trim(), req.getContent(), Instant.now(), Instant.now());
        return repository.save(note);
    }

    // PUBLIC_INTERFACE
    public Note update(Long id, NoteRequest req) {
        /** Update an existing note by id using request DTO. */
        Note existing = repository.findById(id).orElseThrow(() -> new NotFoundException("Note " + id + " not found"));
        existing.setTitle(req.getTitle().trim());
        existing.setContent(req.getContent());
        return repository.save(existing);
    }

    // PUBLIC_INTERFACE
    public Note get(Long id) {
        /** Get a note by id or throw NotFoundException. */
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Note " + id + " not found"));
    }

    // PUBLIC_INTERFACE
    public void delete(Long id) {
        /** Delete note by id; throws NotFoundException if missing. */
        if (!repository.existsById(id)) {
            throw new NotFoundException("Note " + id + " not found");
        }
        repository.deleteById(id);
    }

    // PUBLIC_INTERFACE
    public List<Note> list(String search, String sort, String order, Integer page, Integer size) {
        /** List notes with optional search, sort, and pagination. */
        List<Note> notes = repository.findAll();

        // Filter by search (title/content contains, case-insensitive)
        if (search != null && !search.isBlank()) {
            String q = search.toLowerCase(Locale.ROOT);
            notes = notes.stream()
                    .filter(n ->
                            (n.getTitle() != null && n.getTitle().toLowerCase(Locale.ROOT).contains(q)) ||
                            (n.getContent() != null && n.getContent().toLowerCase(Locale.ROOT).contains(q)))
                    .collect(Collectors.toList());
        }

        // Sorting
        Comparator<Note> comparator;
        Collator collator = Collator.getInstance(Locale.ROOT);
        collator.setStrength(Collator.PRIMARY);
        if ("updatedAt".equalsIgnoreCase(sort)) {
            comparator = Comparator.comparing(Note::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
        } else if ("title".equalsIgnoreCase(sort)) {
            comparator = Comparator.comparing(n -> n.getTitle() == null ? "" : n.getTitle(), collator);
        } else {
            // default sort by createdAt
            comparator = Comparator.comparing(Note::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
        }
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        notes = notes.stream().sorted(comparator).collect(Collectors.toList());

        // Pagination (page >= 0, size > 0)
        int p = page == null || page < 0 ? 0 : page;
        int s = size == null || size <= 0 ? 20 : size;
        int fromIdx = Math.min(p * s, notes.size());
        int toIdx = Math.min(fromIdx + s, notes.size());
        return notes.subList(fromIdx, toIdx);
    }
}
