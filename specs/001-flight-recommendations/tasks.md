
---
description: "Tasks for Flight Recommendations feature"
---

# Tasks: Flight Recommendations (001-flight-recommendations)

**Input**: Design documents from `/specs/001-flight-recommendations/`  
**Prerequisites**: `plan.md`, `spec.md`, `research.md`, `data-model.md`, `contracts/openapi.yaml`

## Phase 1: Setup (Shared Infrastructure)

- [x] T001 [P] Create Maven Spring Boot project skeleton with `pom.xml` at project root (`pom.xml`).
- [x] T002 [P] Add application entrypoint `src/main/java/com/example/recommendations/Application.java`.
- [x] T003 [P] Add Spring Boot web and JPA dependencies, `springdoc-openapi`, Flyway, and SQLite JDBC to `pom.xml`.
- [x] T004 [P] Add a local configuration file `application-local.yml` in `src/main/resources/application-local.yml` and default `application.yml`.
- [x] T005 [P] Add `.gitignore` and minimal `README.md` with a reference to `specs/001-flight-recommendations/quickstart.md`.

---

## Phase 2: Foundational (Blocking Prerequisites)

- [x] T006 Setup Flyway migrations directory and add initial migration file `src/main/resources/db/migration/V1__create_flight_and_fare_tables.sql`.
- [x] T007 [P] Add JPA entity classes: `src/main/java/com/example/recommendations/model/Flight.java` and `src/main/java/com/example/recommendations/model/Fare.java`.
- [x] T008 [P] Add JPA repositories: `src/main/java/com/example/recommendations/repository/FlightRepository.java` and `src/main/java/com/example/recommendations/repository/FareRepository.java`.
- [x] T009 Configure SQLite datasource for local development in `src/main/resources/application-local.yml` and ensure Flyway uses it.
- [x] T010 Implement global error handling and RFC7807 problem responses: `src/main/java/com/example/recommendations/api/ProblemDetails.java` and `src/main/java/com/example/recommendations/api/ExceptionAdvice.java`.
- [x] T011 [P] Add `springdoc-openapi` configuration (if needed) so the running app exposes Swagger UI; verify mapping with `specs/001-flight-recommendations/contracts/openapi.yaml`.
- [x] T012 Create a `scripts/seed-mock-data.sh` at `scripts/seed-mock-data.sh` and a seed SQL file `specs/001-flight-recommendations/seed/seed-data.sql` to populate ~5 days of mocked data.
- [x] T013 [P] Add basic logging and request ID filter `src/main/java/com/example/recommendations/config/RequestIdFilter.java`.

**Checkpoint**: Run the app locally and confirm Flyway applies `V1__create_flight_and_fare_tables.sql` and the DB file is created.

---

## Phase 3: User Story 1 - Get flight recommendations (Priority: P1) 🎯 MVP

**Goal**: Implement `GET /recommendations` to return flight recommendations with fares for a date or date range.

**Independent Test**: Start the app with seeded DB and call `GET /recommendations?origin=JFK&destination=LAX&date=YYYY-MM-DD` → expect 200 and `results` array with Flight + Fare entries.

### Tests

- [x] T014 [P] [US1] Add contract verification test that validates `GET /recommendations` adheres to `specs/001-flight-recommendations/contracts/openapi.yaml` (tests/contract/RecommendationContractTest.java).
- [x] T015 [P] [US1] Add integration test `src/test/java/com/example/recommendations/integration/RecommendationIntegrationTest.java` that seeds DB and verifies recommendations for single date and range.

### Implementation

- [x] T016 [P] [US1] Add DTOs: `src/main/java/com/example/recommendations/dto/FlightDto.java`, `src/main/java/com/example/recommendations/dto/FareDto.java`, `src/main/java/com/example/recommendations/dto/RecommendationDto.java`.
- [x] T017 [US1] Implement `RecommendationService` in `src/main/java/com/example/recommendations/service/RecommendationService.java` (query DB, assemble Recommendation projection).
- [x] T018 [US1] Implement REST controller `src/main/java/com/example/recommendations/api/RecommendationsController.java` with `GET /recommendations` endpoint and validation for `origin`, `destination`, and date params.
- [x] T019 [US1] Add input validation for date format and IATA codes in `src/main/java/com/example/recommendations/validation/RequestValidators.java`.
- [x] T020 [US1] Add logging and structured response for successful requests in `RecommendationsController`.

**Checkpoint**: Integration test (T015) passes and contract test (T014) passes.

---

## Phase 4: User Story 2 - Flight details (Priority: P2)

**Goal**: Implement `GET /flights/{flight_id}` to return detailed fare breakdown and rules.

**Independent Test**: Call `GET /flights/{flight_id}` for an existing seeded flight → expect 200 with fares array.

### Tests

- [x] T021 [P] [US2] Add contract verification test for `GET /flights/{flight_id}` (tests/contract/FlightContractTest.java).
- [x] T022 [P] [US2] Add integration test `src/test/java/com/example/recommendations/integration/FlightDetailIntegrationTest.java` verifying fare attributes (price, currency, refundable, rules).

### Implementation

- [x] T023 [P] [US2] Implement `FlightService` method to fetch flight and fares in `src/main/java/com/example/recommendations/service/FlightService.java`.
- [x] T024 [US2] Implement controller method in `src/main/java/com/example/recommendations/api/FlightsController.java` for `GET /flights/{flight_id}`.
- [x] T025 [US2] Add mapping from JPA entities to `FlightWithFaresDto` in `src/main/java/com/example/recommendations/mapper/FlightMapper.java`.

**Checkpoint**: Integration test (T022) and contract test (T021) pass.

---

## Phase 5: User Story 3 - Seed & reset mocked data (Priority: P3)

**Goal**: Provide reproducible seeding and reset scripts for local development and CI.

**Independent Test**: Run `scripts/seed-mock-data.sh data/recommendations.db` → verify DB contains 5 distinct dates and each fare type per day.

### Implementation & Tests

- [x] T026 [P] [US3] Implement `scripts/seed-mock-data.sh` to create DB file and load `specs/001-flight-recommendations/seed/seed-data.sql`.
- [x] T027 [US3] Add a verification script `scripts/verify-seed.sh` that runs SQL queries and exits non-zero on failures (checks counts per date and fare types).
- [x] T028 [US3] Add integration test `src/test/java/com/example/recommendations/integration/SeedDataIntegrationTest.java` that runs seed script and asserts expected rows.

**Checkpoint**: Seed verification (T027) and integration test (T028) pass locally/CI.

---

## Phase 6: Polish & Cross-Cutting Concerns

- [ ] T029 [P] Add/update `specs/001-flight-recommendations/contracts/openapi.yaml` to reflect any implementation details discovered during development.
- [ ] T030 [P] Add `specs/001-flight-recommendations/README.md` summarizing how to run seed, tests, and sample API calls.
- [ ] T031 Security: Add notes and a checklist file `specs/001-flight-recommendations/production-checklist.md` describing TLS, auth, and secrets management required for production.
- [ ] T032 [P] Add CI workflow file `/.github/workflows/ci.yml` to run `mvn test`, seed DB, and run integration/contract tests.
- [ ] T033 [P] Ensure code formatting and linting: add `spotless` plugin configuration in `pom.xml` or equivalent.

---

## Dependencies & Execution Order

- Setup tasks (T001–T005) can run in parallel.  
- Foundational tasks (T006–T013) block user story implementation and must complete before T016, T017, etc.  
- User stories (T016–T025) can proceed after foundational tasks complete; tests (T014–T015, T021–T022, T026–T028) can run in parallel where marked [P].

## Parallel Opportunities

- Initialize project files, add dependencies, and create application entrypoint in parallel (T001–T005).  
- Entity and repository creation can be parallel (T007, T008).  
- Contract tests and integration tests for separate stories can run in parallel (T014/T015 vs T021/T022).  

## Implementation Strategy

- MVP: deliver Phase 1 + Phase 2 + Phase 3 (User Story 1). Validate, then add User Story 2 and 3.

## Files referenced by tasks (quick index)

- `pom.xml`  
- `src/main/java/com/example/recommendations/Application.java`  
- `src/main/resources/application-local.yml`  
- `src/main/resources/db/migration/V1__create_flight_and_fare_tables.sql`  
- `src/main/java/com/example/recommendations/model/Flight.java`  
- `src/main/java/com/example/recommendations/model/Fare.java`  
- `src/main/java/com/example/recommendations/repository/FlightRepository.java`  
- `src/main/java/com/example/recommendations/api/RecommendationsController.java`  
- `src/main/java/com/example/recommendations/api/FlightsController.java`  
- `scripts/seed-mock-data.sh`  
- `specs/001-flight-recommendations/contracts/openapi.yaml`
