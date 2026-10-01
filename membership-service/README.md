# membership-service

Membership plans (FREE/PREMIUM/FAMILY, seeded via Flyway) and per-user subscriptions.

**Design note:** every plan gets identical baseline emergency dispatch — tiers only
change extras (`maxEmergencyContacts`, `facilitySearchRadiusKm`), never whether or
how fast someone gets help. See the discussion earlier in this project's history for
why that's a deliberate choice for a safety-critical product.

## Endpoints

| Method | Path | Notes |
|---|---|---|
| GET | `/api/memberships/{userId}` | Current plan + limits (self only) |
| POST | `/api/memberships/subscribe` | Body: `{"planName": "PREMIUM"}` |

Both require `Authorization: Bearer <accessToken>`.

## Not yet wired

- `contact-service` doesn't enforce `maxEmergencyContacts` yet
- `facility-service`'s `/nearest` doesn't use `facilitySearchRadiusKm` as a default yet

Both would need this service's data available to them (a REST call, or a
membership-tier claim added to the JWT at login time).

## Run via Docker

```bash
docker compose up --build membership-service
```

## Try it

```bash
curl -X POST http://localhost:8080/api/memberships/subscribe \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <accessToken>" \
  -d '{"planName": "PREMIUM"}'

curl http://localhost:8080/api/memberships/<userId> \
  -H "Authorization: Bearer <accessToken>"
```
