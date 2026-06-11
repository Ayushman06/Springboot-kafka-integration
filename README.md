
# springboot-kafka-demo

A hands-on multi-module Spring Boot demo demonstrating an end-to-end Kafka flow: a Wikimedia event producer and a consumer that maps events with MapStruct and persists them via Spring Data JPA. Designed as a learning playground for event-driven integration, mapping best-practices, and troubleshooting.

## Contents
- `kafka-producer-wikimedia/` — Kafka producer that publishes Wikimedia events using `KafkaTemplate`.
- `kafka-consumer-database/` — Kafka consumer that maps DTOs to JPA entities (MapStruct) and persists to a database.
- Root `pom.xml` — multi-module Maven build that lists both modules.

## Tech stack
Java 21 \| Spring Boot (root parent 4.x) \| Spring Kafka \| Spring Data JPA \| MapStruct \| Maven \| Lombok \| H2/MySQL

## Prerequisites
- Java JDK 21 (matching `pom.xml`)
- Maven 3.6+
- Kafka broker (default: `localhost:9092`)
- Use MySQL or in-memory H2 for local dev

## Quick start

1. Build everything:
   ```
   mvn clean install
   ```

2. Start Kafka broker locally (or use Docker).

3. Run producer:
   ```
   mvn -pl kafka-producer-wikimedia spring-boot:run
   ```

4. Run consumer:
   ```
   mvn -pl kafka-consumer-database spring-boot:run
   ```

5. Produce a test message (producer app will publish automatically if configured) or use:
   ```
   kafka-console-producer --broker-list localhost:9092 --topic your-topic
   ```

6. Verify:
   - Producer logs show Event data.
   - Consumer logs show "Event message received" and mapping logs.
   - DB records appear in H2 console or configured MySQL.

## Configuration
- Each module has `application.properties`.
- Important properties that must match across modules:
  - `spring.kafka.bootstrap-servers`
  - `spring.kafka.topic.name`
  - `spring.kafka.consumer.group-id`
- Datasource properties for `kafka-consumer-database`:
  - `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`, `spring.datasource.driver-class-name`

## Development conventions (repo-specific)
- Package root: `com.practice.[modulename]`
- Constructor-based DI preferred
- DTOs for messages and inter-module payloads; never expose entities across boundaries

---
