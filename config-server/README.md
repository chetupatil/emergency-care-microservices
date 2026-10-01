# config-server

Spring Cloud Config Server. Serves shared and per-service configuration from
`src/main/resources/config-repo` (native profile — no external Git repo needed for local dev).

## Run standalone (no Docker)

```bash
mvn spring-boot:run
```

Runs on port `8888`.

## Run via Docker

```bash
docker build -t config-server .
docker run -p 8888:8888 config-server
```

## Verify it's up

```bash
curl http://localhost:8888/actuator/health
# Fetch the config a client (e.g. api-gateway) would receive:
curl http://localhost:8888/api-gateway/default
```

## Add config for a new service

Drop a `<service-name>.yml` file into `config-repo/` — it's picked up automatically,
no restart required for new files (Spring re-reads the native search location on each request).
