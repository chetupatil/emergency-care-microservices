# location-service

Stores live GPS pings tied to an active emergency, for real-time tracking during
dispatch.

## Endpoints

| Method | Path | Notes |
|---|---|---|
| POST | `/api/location/{emergencyId}/ping` | Body: `{latitude, longitude}` — the frontend calls this every few seconds during an active SOS |
| GET | `/api/location/{emergencyId}/latest` | Most recent position |
| GET | `/api/location/{emergencyId}/history` | Full trail for that emergency |

All require `Authorization: Bearer <accessToken>`.

## Known simplification

This service doesn't verify that the caller actually owns `emergencyId` (that data
lives in `emergency-service`'s separate database — a cross-service check would need
a REST call there, similar to how `notification-service` calls `contact-service`).
Fine for local dev; worth closing before any real deployment.

## Run via Docker

```bash
docker compose up --build location-service
```

## Try it

```bash
curl -X POST http://localhost:8080/api/location/1/ping \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <accessToken>" \
  -d '{"latitude": -37.8140, "longitude": 144.9633}'

curl http://localhost:8080/api/location/1/latest \
  -H "Authorization: Bearer <accessToken>"
```
