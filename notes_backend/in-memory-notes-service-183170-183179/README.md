# in-memory-notes-service-183170-183179

This workspace contains a Spring Boot in-memory Notes backend.

- Container: notes_backend
- Path: in-memory-notes-service-183170-183179/notes_backend

Run locally:
```bash
cd in-memory-notes-service-183170-183179/notes_backend
./gradlew bootRun
```

API base: http://localhost:3001/api/notes

Example:
```bash
curl -i -X POST http://localhost:3001/api/notes \
  -H "Content-Type: application/json" \
  -d '{"title":"My note","content":"Hello"}'
```
