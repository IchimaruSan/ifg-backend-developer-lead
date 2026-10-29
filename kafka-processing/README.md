# IFG Technical Test — Question 1: Kafka Transaction Processor

A Java 17 + Quarkus service that consumes transaction events from Kafka, validates and transforms the data, and stores the processed result in PostgreSQL.

## Architecture

`transactions-in` Kafka topic → Quarkus consumer → validation and calculation → PostgreSQL `processed_transactions`

The application exposes `GET /transactions` to inspect processed records.

## Business rule

This demo calculates a 2% service fee:

- `fee = amount × 2%`
- `netAmount = amount − fee`

This is an example rule for the technical test, not a real IFG business rule.

## Tech stack

- Java 17
- Quarkus 3.15.3
- Maven
- SmallRye Reactive Messaging Kafka connector
- PostgreSQL 17
- Hibernate ORM with Panache
- Docker Compose (PostgreSQL and Redpanda as a local Kafka-compatible broker)

## Prerequisites

- JDK 17
- Maven 3.9+
- Docker Desktop / Docker Engine with Compose

## Run locally

1. Start infrastructure:

   ```bash
   docker compose up -d
   ```

2. Create the input topic:

   ```bash
   docker exec -it ifg-redpanda rpk topic create transactions-in
   ```

   If the topic already exists, continue.

3. Start Quarkus in dev mode:

   ```bash
   mvn quarkus:dev
   ```

4. Publish a sample event from another terminal:

   ```bash
   docker exec -i ifg-redpanda rpk topic produce transactions-in
   ```

   Paste the following one-line JSON and press Enter:

   ```json
   {"eventId":"evt-1001","customerId":"cust-2001","amount":100000.00,"currency":"IDR"}
   ```

5. Inspect saved records:

   ```bash
   curl http://localhost:8080/transactions
   ```

   Expected calculated values for this event are `amount=100000.00`, `fee=2000.00`, and `netAmount=98000.00`.

## Run unit tests

```bash
mvn test
```

The unit tests verify the calculation and basic validation rules without requiring Kafka or PostgreSQL.

## API

### `GET /transactions`

Returns all processed transaction records as JSON. Intended for local test/demo use.

## Reliability notes

- `eventId` is the idempotency key; a duplicate delivery is ignored if its record already exists.
- The incoming connector is configured with a dead-letter queue topic (`transactions-in-dlq`) for messages that fail processing.
- PostgreSQL writes are transactional.
- Kafka consumption and the database commit are not one distributed atomic transaction. For production-grade delivery guarantees, consider a carefully designed retry/DLQ policy and an inbox/outbox strategy as appropriate.
- `quarkus.hibernate-orm.database.generation=update` is convenient for a technical-test demo. Production deployments should use versioned database migrations.
- Credentials in `application.properties` and `docker-compose.yml` are local demo credentials only; do not use them in production.

## Repository layout

```text
src/main/java/id/ifg/technicaltest/transaction/
├── api/             # Inspection endpoint
├── entity/          # PostgreSQL entity
├── messaging/       # Kafka consumer
├── model/           # Input and processed data models
└── service/         # Validation, calculation, and persistence
src/test/java/       # Unit tests
examples/            # Sample Kafka event
```
