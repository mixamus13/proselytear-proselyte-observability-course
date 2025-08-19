# 🧠 Proselyte Observability Course

## Описание

Проект демонстрирует внедрение observability (наблюдаемости) в микросервисной архитектуре на практике:

* `event-producer` — сервис-поставщик событий (Kafka)
* `event-consumer` — сервис-потребитель событий с записью в PostgreSQL
* `event-public-api` — публичный gateway для взаимодействия с системой
* `monitoring` — инфраструктура мониторинга и трассировки: Grafana, Prometheus, Loki, Tempo, Alloy

В проекте реализованы:

- Сбор, хранение и визуализация метрик сервисов через Prometheus и Grafana
- Централизованное логирование (Loki)
- Трассировка распределённых запросов (Tempo, Alloy)
- Отдельные Grafana dashboards для сервисов и общей инфраструктуры

## Архитектура

```
                   +-------------------+
                   |  event-public-api |
                   +--------+----------+
                            |
                            |
                            |
                            |
 +-----v-----+         +-----v-----+
 | event-    | Kafka   | event-    |
 | producer  +-------> | consumer  +--> PostgreSQL
 +-----------+         +-----------+
        \                     /
+--------------------------------------------------------+
observability stack
(Prometheus, Grafana, Loki, Tempo, Alloy)
````

## Эндпоинты

📘 Документация OpenAPI:
* 🔗 [event-public-api](event-public-api/openapi.yaml)
* 🔗 [event-consumer](event-consumer/openapi.yaml)

Пример запроса создания события:

```bash
curl -X POST http://localhost:8092/api/v1/events \
  -H 'Content-Type: application/json' \
  -d '{"subject": "Test event", "description": "This is a test"}'
````

## Быстрый старт через Docker Compose

```bash
docker-compose up --build -d
```

Запускаются:

* event-producer (`localhost:8090`)
* event-consumer (`localhost:8091`)
* event-public-api (`localhost:8092`)
* PostgreSQL (`localhost:5432`)
* Kafka1 (`localhost:9092`)
* Kafka2 (`localhost:9093`)
* Kafka3 (`localhost:9094`)
* Kafka UI (`localhost:8070`)
* Grafana (`localhost:3000`, логин: admin/admin)
* Prometheus (`localhost:9090`)
* Tempo (`localhost:3200`)
* Loki (`localhost:3100`)
* Alloy (`localhost:12345`)
* MinIO (`localhost:9001`, логин: tempo/tempo-tempo)

## Структура проекта

```
proselyte-observability-course/
├── docker-compose.yml
├── event-producer/
│   ├── build.gradle
│   ├── Dockerfile
│   └── src/... (Spring Boot producer)
├── event-consumer/
│   ├── build.gradle
│   ├── Dockerfile
│   └── src/... (Spring Boot consumer)
├── event-public-api/
│   ├── build.gradle
│   ├── Dockerfile
│   └── src/... (Spring Boot gateway)
├── monitoring/
│   ├── prometheus/...
│   ├── grafana/...
│   ├── loki/...
│   ├── tempo/...
│   └── alloy/...
├── EventSystemMetrics.json
├── EventsSystemInfrastructureDash.json
└── README.md
```

## Ветки разработки

* `STEP-1`  — initial configs for prometheus
* `STEP-2`  — configured remote-ubuntu with node-exporter
* `STEP-3`  — added alerts configs for prometheus
* `STEP-4`  — added Grafana to the docker-compose.yml
* `STEP-5`  — added docker compose with all required infrastructure
* `STEP-6`  — added event-producer and basic configs
* `STEP-7`  — added event-consumer with basic configs
* `STEP-8`  — added EventMetrics.json for Grafana dashboard
* `STEP-9`  — configured logging and alloy + loki integration
* `STEP-10` — added MDC params for event-consumer service logs
* `STEP-11` — added event-public-api and basic configurations
* `STEP-12` — added tempo and tracing configurations
* `STEP-13` — added tempo+minio configurations

## Технологии

* Java 24
* Spring Boot 3 (Web, Kafka, Data, Actuator)
* PostgreSQL
* Kafka
* Prometheus / Grafana
* Loki (логирование)
* Tempo / Alloy (tracing)
* Docker / Docker Compose
* MinIO
* 
## Автор
[Eugene Suleimanov](https://github.com/proselytear)
[Software Engineering Telegram](https://t.me/esuleimanov)

## GitHub
https://github.com/mixamus13/proselytear-proselyte-observability-course

## YouTube
https://www.youtube.com/watch?v=PqU1FL-OfPo