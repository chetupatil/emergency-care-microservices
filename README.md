# Health Emergency App 

This is the first slice of the build: infrastructure (MySQL, Kafka, Zookeeper) plus
the three platform services (Config Server, Eureka, API Gateway). No business
services yet (`user-service`, `emergency-service`, etc. come next, once you confirm
this slice boots cleanly).

## Prerequisites

- Docker + Docker Compose installed locally
- Ports free on your machine: `3307`, `2181`, `9092`, `8888`, `8761`, `8080`
  (MySQL is mapped to host port `3307` — not `3306` — to avoid clashing with a
  local MySQL install; inside Docker, other services still reach it at `mysql:3306`)

## Run it

```bash
docker-compose up --build
```

First build will take a few minutes (Maven downloads dependencies inside the build
stage for each service). Subsequent builds are cached and much faster.

## What "working" looks like

Check these in order — each depends on the last:

1. **MySQL** — `docker exec -it mysql mysql -uroot -prootpassword -e "SHOW DATABASES;"`
   You should see `user_db`, `contact_db`, `membership_db`, `emergency_db`,
   `facility_db`, `notification_db`, `location_db`.
2. **Kafka** — `docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list`
   (empty list is fine — no topics created yet, that happens in step 5).
3. **Config Server** — http://localhost:8888/actuator/health → `{"status":"UP"}`.
   Then http://localhost:8888/api-gateway/default should return the gateway's config,
   proving the native config-repo is being served correctly.
4. **Discovery Server (Eureka)** — http://localhost:8761 → dashboard loads. Under
   "Instances currently registered" you should see `API-GATEWAY` appear once it's up.
5. **API Gateway** — http://localhost:8080/actuator/health → `{"status":"UP"}`.

If a service won't go healthy, check its logs:

```bash
docker-compose logs -f <service-name>
```

## Tear down

```bash
docker-compose down          # stop containers, keep MySQL data
docker-compose down -v       # stop containers AND wipe MySQL data volume
```

## What's next

Once all five checks above pass, the next slice is `user-service`:
registration/login/profile, its own Flyway-managed `user_db` schema, registered
with Eureka, and routed through the Gateway. Say the word and I'll scaffold it the
same way.
# emergency-care-microservices
