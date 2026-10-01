# facility-service

Hospital/ambulance directory with geo lookup (haversine distance — no external maps
API needed for local dev). Seeded with 6 sample Melbourne-area facilities via Flyway
(one marked unavailable, to exercise the availability filter).

## Endpoints

| Method | Path | Auth | Notes |
|---|---|---|---|
| GET | `/api/facilities/nearest?lat=&lng=&radiusKm=10` | No | Available facilities within radius, nearest first |

## Kafka

- **Consumes** `EMERGENCY_TRIGGERED` on `emergency-events` — finds the single nearest
  available facility (ignores radius, always dispatches *someone*) and mocks an ETA
- **Produces** `FACILITY_DISPATCHED` on `facility-events`

The ETA calculation is a stub (`4 + random(0-6)` minutes) — swap in a real
routing/traffic API when you're past local dev.

## Run via Docker

```bash
docker compose up --build facility-service
```

## Try it

```bash
curl "http://localhost:8080/api/facilities/nearest?lat=-37.8136&lng=144.9631&radiusKm=15"
```

To see the Kafka flow in action, trigger an emergency via `emergency-service` and
watch this service's logs:
```bash
docker compose logs -f facility-service
```
