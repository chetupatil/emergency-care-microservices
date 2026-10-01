# user-service

Registration, login, and profile management. Issues a short-lived JWT **access token**
(15 min) plus an opaque, server-stored, revocable **refresh token** (7 days) on
register/login. Passwords are hashed with BCrypt.

## Endpoints

| Method | Path | Auth required | Notes |
|---|---|---|---|
| POST | `/api/users/register` | No | Creates account, returns token pair |
| POST | `/api/users/login` | No | Returns token pair |
| POST | `/api/users/refresh` | No (body carries refresh token) | Rotates refresh token, returns a new pair |
| POST | `/api/users/logout` | No (body carries refresh token) | Revokes the given refresh token |
| GET | `/api/users/me` | Yes | Profile of the caller, resolved from the JWT |
| GET | `/api/users/{id}` | Yes | Fetch a profile by id |
| PUT | `/api/users/{id}` | Yes | Update profile fields |

Send the access token as `Authorization: Bearer <token>` on protected routes.

## Run standalone (no Docker)

Requires a MySQL reachable at `localhost:3307` with a `user_db` schema and an
`app_user`/`app_password` login (matches the root `infra/mysql-init` script).

```bash
mvn spring-boot:run
```

Flyway runs automatically on startup and creates `users` + `refresh_tokens`.

## Run via Docker

Runs as part of the root `docker-compose.yml` — it needs `mysql` and
`discovery-server` healthy first (already wired via `depends_on`).

```bash
docker compose up --build user-service
```

## Try it

```bash
# Register
curl -X POST http://localhost:8081/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Chetana Patil","email":"chetana@example.com","phone":"+61400000000","password":"SuperSecret123"}'

# Login
curl -X POST http://localhost:8081/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"chetana@example.com","password":"SuperSecret123"}'

# Use the access token from the login response
curl http://localhost:8081/api/users/me -H "Authorization: Bearer <accessToken>"
```

Once routed through the Gateway (step 3 wiring), the same calls work at
`http://localhost:8080/api/users/...` instead of `:8081` directly.

## Environment variables (set by Docker Compose)

| Var | Default (local, non-Docker) |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3307/user_db` |
| `DB_USERNAME` | `app_user` |
| `DB_PASSWORD` | `app_password` |
| `EUREKA_URI` | `http://localhost:8761/eureka/` |
| `CONFIG_SERVER_URI` | `http://localhost:8888` |
| `JWT_SECRET` | dev-only default — **override this in any real deployment** |
