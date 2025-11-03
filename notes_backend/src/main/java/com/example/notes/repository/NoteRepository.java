package com.example.notes.repository;

import com.example.notes.model.Note;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory repository for Notes.
 */
@Repository
public class NoteRepository {

    private final ConcurrentHashMap<Long, Note> store = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(0);

    public NoteRepository() {
        // Optionally seed with a sample note (commented)
        // save(new Note(null, "Welcome", "Your first note", Instant.now(), Instant.now()));
    }

    public Note save(Note note) {
        if (note.getId() == null) {
            long newId = idSeq.incrementAndGet();
            Instant now = Instant.now();
            note.setId(newId).setCreatedAt(now).setUpdatedAt(now);
        } else {
            // update existing
            note.setUpdatedAt(Instant.now());
            if (note.getCreatedAt() == null) {
                note.setCreatedAt(Instant.now());
            }
        }
        store.put(note.getId(), cloneNote(note));
        return cloneNote(note);
    }

    public Optional<Note> findById(Long id) {
        Note n = store.get(id);
        return Optional.ofNullable(n == null ? null : cloneNote(n));
    }

    public List<Note> findAll() {
        List<Note> list = new ArrayList<>();
        for (Note n : store.values()) {
            list.add(cloneNote(n));
        }
        return list;
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    private Note cloneNote(Note n) {
        return new Note(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt());
    }
}
