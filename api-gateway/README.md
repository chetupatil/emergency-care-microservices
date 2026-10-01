# api-gateway

Spring Cloud Gateway. Single entry point for the React frontend — routes requests to
the right backend microservice by looking it up in Eureka (`lb://service-name`).

Route definitions live in the Config Server (`config-repo/api-gateway.yml`) and get
added as each downstream service comes online (step 3+). Until then, discovery
locator is enabled, so any registered service is auto-routable at
`/<service-id>/**`.

## Run standalone (no Docker)

Requires `discovery-server` and `config-server` reachable at `localhost:8761` /
`localhost:8888` (or override via `EUREKA_URI` / `CONFIG_SERVER_URI` env vars).

```bash
mvn spring-boot:run
```

Runs on port `8080`.

## Run via Docker

```bash
docker build -t api-gateway .
docker run -p 8080:8080 \
  -e EUREKA_URI=http://discovery-server:8761/eureka/ \
  -e CONFIG_SERVER_URI=http://config-server:8888 \
  --network health-emergency-app_health-app-network \
  api-gateway
```

(When run via the root `docker-compose.yml`, networking and env vars are already wired up.)

## Verify it's up

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/gateway/routes
```

It should also show up as a registered instance on the Eureka dashboard at
http://localhost:8761.
