# Quickstart — Flight Recommendations (local prototype)

Prerequisites:
- Java 17 (or later)
- Maven
- sqlite3 client (optional, for inspecting the DB)

Run locally (development):

1. Build the project:

```bash
mvn clean package -DskipTests
```

2. Initialize / seed the local SQLite database (project root)

The prototype uses a file `data/recommendations.db` by default. We provide a simple seed script to populate ~5 days of mocked data.

```bash
# from repo root
mkdir -p data
./scripts/seed-mock-data.sh data/recommendations.db
```

3. Run the application:

```bash
java -jar target/recommendations-0.1.0.jar --spring.config.location=classpath:/application.yml,./application-local.yml
```

4. Open the OpenAPI docs (after app starts):

Visit `http://localhost:8080/swagger-ui.html` (springdoc default) to explore the contract defined at `specs/001-flight-recommendations/contracts/openapi.yaml`.

5. Example requests:

```bash
# Single date
curl 'http://localhost:8080/recommendations?origin=JFK&destination=LAX&date=2025-12-01'

# Date range
curl 'http://localhost:8080/recommendations?origin=JFK&destination=LAX&start_date=2025-12-01&end_date=2025-12-05'

# Flight details
curl 'http://localhost:8080/flights/<flight_id>'
```

Testing:

- Unit tests: `mvn test`
- Integration tests (CI): run tests against seeded DB; ensure seed script runs in CI before integration tests.

Notes & Production Checklist:
- The prototype is intentionally unauthenticated for local development. Before production deploy:
  - Add TLS termination and enforce HTTPS.
  - Add standardized auth (OAuth2 / JWT / mTLS) and restrict API keys/credentials in a secrets manager.
  - Replace SQLite with a production-grade DB (Postgres/MySQL) and ensure migrations are run via CI/CD pipeline.
