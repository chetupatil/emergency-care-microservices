# discovery-server

Netflix Eureka service registry. Every other service registers itself here so the
API Gateway (and other services) can find it by name instead of a hardcoded host:port.

## Run standalone (no Docker)

```bash
mvn spring-boot:run
```

Runs on port `8761`.

## Run via Docker

```bash
docker build -t discovery-server .
docker run -p 8761:8761 discovery-server
```

## Verify it's up

Open http://localhost:8761 in a browser — you should see the Eureka dashboard.
As services come online they'll appear in the "Instances currently registered" list.

```bash
curl http://localhost:8761/actuator/health
```
