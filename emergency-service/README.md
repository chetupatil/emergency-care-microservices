# emergency-service

The heart of the system. Receives the SOS trigger, records it, and publishes an
`EmergencyTriggered` event to Kafka so `facility-service` and `notification-service`
can react in parallel. Also consumes `FacilityDispatched` back from `facility-service`
to update the event's status.

## Endpoints

| Method | Path | Notes |
|---|---|---|
| POST | `/api/emergency/trigger` | Body: `{latitude, longitude}`. Creates the event and publishes to Kafka |
| GET | `/api/emergency/{id}` | Fetch one event you own |
| GET | `/api/emergency/my` | Your emergency history, most recent first |
| POST | `/api/emergency/{id}/resolve` | Mark an event resolved |

All require `Authorization: Bearer <accessToken>`.

## Kafka

- **Produces** `EMERGENCY_TRIGGERED` on topic `emergency-events` (keyed by `userId`)
- **Consumes** `FACILITY_DISPATCHED` on topic `facility-events`, sets status to `DISPATCHED`

Topics are auto-created on startup (`KafkaTopicConfig`) with 3 partitions.

## Run via Docker

Part of the root `docker-compose.yml` — needs `mysql`, `kafka`, and
`discovery-server` healthy first.

```bash
docker compose up --build emergency-service
```

## Try it

```bash
curl -X POST http://localhost:8080/api/emergency/trigger \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <accessToken>" \
  -d '{"latitude": -37.8136, "longitude": 144.9631}'
```

Watch `docker compose logs -f facility-service notification-service` right after —
you should see both react to the event within a second or two.
