ios/ or android/
# Implementation Plan: Flight Recommendations (001-flight-recommendations)

**Branch**: `001-flight-recommendations` | **Date**: 2025-11-29 | **Spec**: `spec.md`
**Input**: Feature specification from `/specs/001-flight-recommendations/spec.md`

## Summary

Deliver a small Java Spring Boot service that exposes two endpoints:

- `GET /recommendations` — returns flight recommendations for a given origin, destination and date or date range (up to 5 days seeded). Responses include available fares (saver, roundtrip_saver, flex, business, first_class) and minimal ranking metadata.
- `GET /flights/{flight_id}` — returns detailed fare breakdown and basic rules for each fare type.

Implementation will be a lightweight Spring Boot application (Maven) using SQLite for local storage and Flyway for schema migrations. OpenAPI (via springdoc-openapi) will provide the contract and auto-generated docs. Tests: JUnit + Spring Boot Test; contract checks run in CI against the OpenAPI spec.

## Technical Context

**Language/Version**: Java 17 (LTS)  
**Primary Dependencies**: Spring Boot 3.x (spring-boot-starter-web, spring-boot-starter-data-jpa), springdoc-openapi-ui, Flyway (flyway-core), `org.xerial:sqlite-jdbc`  
**Storage**: SQLite local database file for development and CI seeding  
**Migrations**: Flyway-based versioned SQL migrations in `src/main/resources/db/migration`  
**Testing**: JUnit 5, Spring Boot Test, contract tests (OpenAPI-based verification), simple integration tests that run against a seeded SQLite DB  
**Target Platform**: macOS / Linux development and CI runners (packaging as a runnable JAR)  
**Project Type**: Backend web service (Spring Boot Maven layout)  
**Performance Goals**: p95 response time < 1s for local CI seeded dataset; dataset size small (5 days of mocked data).  
**Constraints**: Local development will use SQLite; production readiness (TLS, auth, scaling) is out of scope for this feature but must be added before production rollout.  
**Scale/Scope**: Prototype: <10k records; single-service local deployment.

## Constitution Check

Gates evaluated against `.specify/memory/constitution.md`:

- Contract-driven: PASS — we will produce an OpenAPI spec in `contracts/openapi.yaml` and generate docs from it.
- Migrations: PASS — using Flyway for versioned migrations; migrations will live in the repo and CI will run dry-runs.
- Test-First / Contract Tests: PASS — unit + integration tests and contract checks planned in CI.
- Observability: PARTIAL — For local dev, emit request IDs and structured logs; distributed tracing and metrics integration can be added later.
- Security (TLS/Auth): DEVIATION — the MVP is unauthenticated for local development per spec assumptions. Justification: user requested local mocked dataset and a simple developer-facing MVP; production must enforce TLS and standardized auth (OAuth2/JWT/mTLS) per the constitution. Mitigation: document required production controls in `quickstart.md` and in PR notes; add gate in PR checklist to require auth/TLS before production merge.
- Secrets Management: DEVIATION (local only) — SQLite uses a local file with no secrets; justify as local dev. Production DB credentials must be stored in a secrets manager.

Decision: Constitution violations are limited, documented, and justified for a local-development MVP. They must be re-checked before any production deployment.

## Project Structure

Chosen structure: standard Maven Spring Boot layout.

```
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/example/recommendations/   # controllers, services, repositories, models
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/                    # Flyway SQL migrations
│   └── test/
│       └── java/...                             # unit & integration tests
├── scripts/
│   └── seed-mock-data.sh                        # seeds SQLite with ~5 days of data
└── specs/001-flight-recommendations/
    ├── contracts/openapi.yaml
    ├── data-model.md
    ├── quickstart.md
    └── research.md
```

**Structure Decision**: Use a single Maven project (no frontend) — keeps distribution small and matches Spring Boot conventions.

## Complexity Tracking

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| Security (no auth in MVP) | Fast local development & testing; user requested unauthenticated local prototype | Adding OAuth2 or JWT would increase implementation time; produce a production checklist to require auth before deployment.

