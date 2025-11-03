# Notes Backend (In-Memory)

A simple Spring Boot REST API providing CRUD operations for notes, using in-memory storage only. No external database is used.

## Features
- In-memory store using ConcurrentHashMap and AtomicLong for IDs
- CRUD endpoints under `/api/notes`
- Validation on request data (title required, 1–200; content up to 5000)
- Filtering (search in title/content), sorting (createdAt, updatedAt, title), order (asc/desc)
- Pagination (page, size)
- CORS enabled for development (all origins, common methods)
- Proper HTTP status codes (201 on create with Location header, 200 OK, 204 No Content, 404 Not Found, 400 Bad Request)
- ISO-8601 dates, not numeric timestamps

## Run
Using Gradle wrapper:

```bash
cd in-memory-notes-service-183170-183179/notes_backend
./gradlew bootRun
```

The app listens on port `3001`.

## API

Base URL: `http://localhost:3001/api/notes`

### Create
```bash
curl -i -X POST http://localhost:3001/api/notes \
  -H "Content-Type: application/json" \
  -d '{"title":"First note","content":"Hello"}'
```
- Response: `201 Created`
- Location header: `/api/notes/{id}`

### Get by ID
```bash
curl -i http://localhost:3001/api/notes/1
```
- Response: `200 OK`
- Body:
```json
{
  "id": 1,
  "title": "First note",
  "content": "Hello",
  "createdAt": "2024-01-01T00:00:00Z",
  "updatedAt": "2024-01-01T00:00:00Z"
}
```

### List (filter/sort/paginate)
```bash
curl -s "http://localhost:3001/api/notes?search=first&sort=updatedAt&order=desc&page=0&size=10" | jq
```

### Update
```bash
curl -i -X PUT http://localhost:3001/api/notes/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"First note (edited)","content":"Updated"}'
```
- Response: `200 OK`

### Delete
```bash
curl -i -X DELETE http://localhost:3001/api/notes/1
```
- Response: `204 No Content`

## Validation errors
```bash
curl -i -X POST http://localhost:3001/api/notes \
  -H "Content-Type: application/json" \
  -d '{"title":""}'
```
- Response: `400 Bad Request`
- Body (example):
```json
{
  "timestamp": "2025-01-01T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": {
    "title": "title must not be blank"
  }
}
```

## Notes
- Data is volatile and will be lost when the application restarts.
