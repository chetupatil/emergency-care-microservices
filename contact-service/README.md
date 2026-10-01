# contact-service

Manages each user's emergency contacts (family members to notify when SOS triggers).
Trusts JWTs issued by `user-service` — verifies them with the **same shared secret**
(`JWT_SECRET`), but never issues tokens itself.

Every endpoint enforces that the authenticated user (from the JWT) can only
read/modify their own contacts — a 403 is returned otherwise.

## Endpoints

| Method | Path | Notes |
|---|---|---|
| POST | `/api/users/{userId}/contacts` | Add a contact — `userId` must match the JWT's subject |
| GET | `/api/users/{userId}/contacts` | List contacts, ordered by `priorityOrder` |
| PUT | `/api/contacts/{id}` | Update a contact you own |
| DELETE | `/api/contacts/{id}` | Delete a contact you own |

All require `Authorization: Bearer <accessToken>`.

## Run standalone (no Docker)

Requires MySQL at `localhost:3307` with a `contact_db` schema (from
`infra/mysql-init`), and `JWT_SECRET` matching whatever `user-service` uses.

```bash
mvn spring-boot:run
```

## Run via Docker

Part of the root `docker-compose.yml` — needs `mysql` and `discovery-server`
healthy first.

```bash
docker compose up --build contact-service
```

## Try it

```bash
# 1. Log in via user-service (through the Gateway) to get a token + your user id
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"chetana@example.com","password":"SuperSecret123"}'

# 2. Add a contact (replace <userId> and <accessToken>)
curl -X POST http://localhost:8082/api/users/<userId>/contacts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <accessToken>" \
  -d '{"contactName":"Priya Patil","relationship":"Sister","phone":"+61400000099","priorityOrder":1}'

# 3. List contacts
curl http://localhost:8082/api/users/<userId>/contacts \
  -H "Authorization: Bearer <accessToken>"
```

Once routed through the Gateway, use `:8080` instead of `:8082`.

## Not yet enforced

The design doc's `membership.max_emergency_contacts` limit isn't checked here yet —
that'll be wired in once `membership-service` exists and this service can call it
(or consume a membership-tier claim added to the JWT).

## Environment variables (set by Docker Compose)

| Var | Default (local, non-Docker) |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3307/contact_db` |
| `DB_USERNAME` | `app_user` |
| `DB_PASSWORD` | `app_password` |
| `EUREKA_URI` | `http://localhost:8761/eureka/` |
| `CONFIG_SERVER_URI` | `http://localhost:8888` |
| `JWT_SECRET` | must match `user-service`'s value exactly |
