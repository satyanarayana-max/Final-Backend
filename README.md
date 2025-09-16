# College Learning Backend (Spring Boot + PostgreSQL)

This project implements the backend for the role-based college learning platform.

## Quick Start

1. Ensure PostgreSQL is running and create a database (default `college`).
2. Set env vars (optional):
   - `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
   - `JWT_SECRET`, `JWT_EXPIRATION`
3. Build and run:

```bash
./mvnw spring-boot:run
# or
mvn spring-boot:run
```

Default admin is created:
- email: `admin@college.com`
- password: `admin123`

## API Summary

- `POST /api/auth/student/register`
- `POST /api/auth/teacher/register` (admin can also create via `/api/admin/teachers`)
- `POST /api/auth/login` → returns `{ token, fullName, role, userId }`

### Admin (Authorization: `Bearer <token>`, role ADMIN)
- `GET /api/admin/teachers`
- `GET /api/admin/students`
- `POST /api/admin/teachers`
- `DELETE /api/admin/users/{id}`

### Teacher (role TEACHER)
- `POST /api/teacher/courses`
- `PUT /api/teacher/courses/{id}`
- `DELETE /api/teacher/courses/{id}`
- `GET /api/teacher/courses`
- `POST /api/teacher/quizzes`
- `PUT /api/teacher/quizzes/{id}`
- `DELETE /api/teacher/quizzes/{id}`
- `GET /api/teacher/courses/{courseId}/quizzes`

### Student (role STUDENT)
- `GET /api/student/courses` (browse all courses)
- `POST /api/student/courses/{courseId}/enroll`
- `GET /api/student/my-courses`
- `GET /api/student/quizzes`
- `POST /api/student/quizzes/submit`
- `GET /api/student/performance`

Swagger UI: `/swagger-ui/index.html`

## CORS & JWT
- CORS is open (`*`); tighten in production.
- JWT is expected in `Authorization: Bearer <token>` headers on protected routes.
- Tokens include `role` and `userId` claims.
