# notification-service

Reacts to an emergency by looking up the user's emergency contacts (via a
service-to-service call to `contact-service`'s internal API) and "sending" each of
them an SMS — stubbed for local dev, logged either way.

## Kafka

- **Consumes** `EMERGENCY_TRIGGERED` on `emergency-events`
- For each event: calls `contact-service` at `http://contact-service/internal/users/{userId}/contacts`
  (resolved via Eureka, authenticated with a shared `X-Internal-Api-Key` header —
  not a user JWT, since there's no end-user in a Kafka consumer's request context)
- Logs a `NotificationLog` row per contact

## Endpoints

| Method | Path | Notes |
|---|---|---|
| GET | `/api/notifications/emergency/{emergencyEventId}` | Who was notified for a given emergency |

## Wiring a real SMS/call provider

`NotificationDispatchService.sendStub()` is where Twilio (or similar) goes — it
currently always "succeeds" without making any external call.

## Run via Docker

```bash
docker compose up --build notification-service
```

## Try it

Trigger an emergency via `emergency-service`, then check who got notified:
```bash
curl http://localhost:8080/api/notifications/emergency/1
```

## Environment variables

`INTERNAL_API_KEY` must match `contact-service`'s value exactly — this is the shared
secret for the internal contacts lookup, not a user credential.
