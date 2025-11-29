# Research: Technology Decisions

Decision: Implement the prototype with Java 17 + Spring Boot (Maven), Spring Data JPA, Flyway for migrations, and SQLite for local storage.

Rationale:
- Spring Boot (Java) is a mature, well-documented framework for building REST APIs; it integrates easily with OpenAPI generators (springdoc) and testing tools (JUnit, Spring Boot Test).
- Maven provides a familiar build lifecycle and dependency management across Java teams.
- Spring Data JPA simplifies data access and lets us model `Flight`/`Fare` entities quickly while still permitting hand-written queries for performance-sensitive paths.
- Flyway offers simple, versioned SQL migrations that are repository-friendly and work with SQLite via JDBC.
- SQLite satisfies the requirement for a local, file-backed database without needing external services; it is portable between developer machines and CI.

Alternatives considered:
- H2 (in-memory) — pros: easy for tests and fast; cons: H2 SQL and SQLite dialects differ and the user requested SQLite specifically. Decision: prefer SQLite per user request; H2 remains an option for pure in-memory CI runs.
- Liquibase vs Flyway — Liquibase is powerful for advanced change sets; Flyway is simpler for small projects and aligns well with SQL-first migrations. Decision: Flyway chosen for simplicity.
- Plain JDBC vs JPA — plain JDBC gives explicit control; JPA increases developer velocity and maps well to domain entities for this prototype. Use JPA with option to add custom queries if needed.

Unknowns / Clarifications resolved:
- Database choice clarified by user: SQLite — resolved.
- Language/framework clarified by user: Java + Spring Boot + Maven — resolved.
- Migrations tool: selected Flyway (documented here).  

Operational notes:
- For seeding mocked data, we'll provide a `scripts/seed-mock-data.sh` that runs an SQL script or a small Java runner to populate the SQLite DB with ~5 days of data (at least one example of each fare type per day).
- OpenAPI spec will be stored under `specs/001-flight-recommendations/contracts/openapi.yaml` and used as the contract source in CI.

Decision summary:
- Proceed with Spring Boot + Maven + SQLite + Flyway + Spring Data JPA. This keeps the prototype small, reproducible, and consistent with the user's requested stack.
