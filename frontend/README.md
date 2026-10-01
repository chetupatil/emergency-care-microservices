# frontend

React (Vite) app: registration, login, contact management, and the SOS button.
Talks directly to the API Gateway at `http://localhost:8080` (the Gateway allows
wide-open CORS for local dev — see `config-repo/api-gateway.yml`).

## Screens

- **Register / Login** — creates an account or signs in, stores the JWT pair in `localStorage`
- **Dashboard** — the big red SOS button (uses browser geolocation, calls `emergency-service`
  via the Gateway), plus your emergency contacts list with add/remove

## Run standalone (no Docker) — for active frontend development

```bash
npm install
npm run dev
```

Opens at `http://localhost:3000` with hot reload. Requires the backend
(`docker compose up`) already running so `:8080` responds.

## Run via Docker

Builds a static production bundle and serves it with nginx.

```bash
docker compose up --build frontend
```

Open `http://localhost:3000`.

## Auth token handling

- Access token (15 min) sent as `Authorization: Bearer <token>` on every API call
- On a 401, the client silently calls `/api/users/refresh` once and retries — if that
  also fails, it clears storage and redirects to `/login`

## Known simplifications

- Tokens live in `localStorage` (fine for local dev; consider httpOnly cookies for
  a real deployment to reduce XSS exposure)
- No membership/plan UI yet — `membership-service` exists but isn't wired into any screen
- No live-location map during an active SOS yet — `location-service` exists but the
  frontend doesn't call it
