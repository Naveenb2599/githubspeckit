# Feature Specification: Flight Recommendations API

**Feature Branch**: `001-flight-recommendations`  
**Created**: 2025-11-29  
**Status**: Draft  
**Input**: User description: "I am building an API that returns flight recommendations for users based on dates. It should include fares: saver, roundtrip saver, flex (economy), business, and first class. Provide ~5 days of mocked data stored in a local database; do not pull from any real feed."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Get flight recommendations for a date range (Priority: P1)

An end user requests flight recommendations for a specific origin, destination, and date (or date range). The API returns a ranked list of recommended flight options for the requested dates, including available fare types.

**Why this priority**: This is the core user-facing capability delivering the feature's primary value.

**Independent Test**: Call the recommendations endpoint with a valid origin/destination and date → assert HTTP 200, response contains up to N recommendations (N≥1), each with one of the required fare types and required fields.

**Acceptance Scenarios**:

1. **Given** the database contains mocked flight data for the requested dates, **When** the user requests recommendations for a single date, **Then** the API returns recommendations for that date with fare breakdowns (saver, roundtrip saver, flex, business, first class).
2. **Given** the request selects a date range of up to 5 days, **When** the user requests recommendations, **Then** the API returns recommendations for each date in the range (or clearly labels dates with no results).

---

### User Story 2 - View detailed fare breakdown for a single recommended flight (Priority: P2)

After receiving recommendations, the user selects a specific flight to view detailed fare pricing and rules for each fare type.

**Why this priority**: Provides transparency to users so they can make booking decisions.

**Independent Test**: Call the flight detail endpoint with a recommendation id → assert HTTP 200 and the response includes fare types with price, currency, refundable flag, and any basic rules.

**Acceptance Scenarios**:

1. **Given** a recommended flight id returned by the recommendations endpoint, **When** the user requests details for that id, **Then** the API returns a payload containing each fare type and its attributes.

---

### User Story 3 - Developer can seed and reset local mocked data (Priority: P3)

Developers need an easy way to load the ~5 days of mocked data into a local database and reset it for tests.

**Why this priority**: Enables reliable local testing and CI runs without relying on external feeds.

**Independent Test**: Run the provided seed script → assert the database contains the expected 5 days of flight records and sample queries return expected counts.

**Acceptance Scenarios**:

1. **Given** a fresh local database, **When** the developer runs `scripts/seed-mock-data`, **Then** the database contains flight records covering the specified date range and fare types.

---

### Edge Cases

- Request for dates outside the seeded 5-day window → API returns 200 with empty results and clear message indicating no data for those dates.
- Invalid origin/destination codes → API returns 400 with structured error payload explaining the invalid parameter.
- Date format/timezone ambiguity → API requires ISO 8601 dates (YYYY-MM-DD) and documents timezone assumptions; if timezones are provided, the API normalizes them.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The system MUST expose an endpoint `GET /recommendations` that accepts `origin`, `destination`, and `date` or `start_date` + `end_date` and returns flight recommendations.
- **FR-002**: Each recommendation MUST include: `flight_id`, `date`, `departure_time`, `arrival_time`, `duration`, `airline`, and available `fares` (saver, roundtrip_saver, flex, business, first_class) with `price` and `currency`.
- **FR-003**: The system MUST expose an endpoint `GET /flights/{flight_id}` to return detailed fare breakdown and basic rules for each fare type.
- **FR-004**: The system MUST store mocked flight data in a local database and provide a seed/reset script that loads ~5 days of sample data.
- **FR-005**: The API MUST respond with appropriate HTTP status codes and a structured error model (`application/problem+json`) for errors.
- **FR-006**: The API MUST validate input and return 400 for malformed requests (invalid dates, missing required params).
- **FR-007**: The seeded mock dataset MUST include at least one example of each fare type for each sample day.
- **FR-008**: CI MUST run unit tests and integration tests against the local database seed and fail on regressions.

### Key Entities *(include if feature involves data)*

- **Flight**: Represents a scheduled flight. Key attributes: `flight_id`, `airline`, `origin`, `destination`, `date`, `departure_time`, `arrival_time`, `duration`.
- **Fare**: Represents a fare option for a flight. Key attributes: `fare_type` (saver, roundtrip_saver, flex, business, first_class), `price`, `currency`, `refundable` (boolean), `rules` (brief summary).
- **Recommendation**: A view combining a `Flight` with selected `Fare` options and ranking metadata (score, reasons).
- **SeedRecord**: Metadata for the mocked dataset (date range covered, number of flights per day).

## Assumptions

- Date inputs use ISO 8601 date format (`YYYY-MM-DD`). Timezone is UTC unless the client specifies an offset; API normalizes dates to UTC for matching seeded records.
- No external flight data feeds are used; all data is mocked and stored locally for this feature.
- Authentication/authorization is out of scope for the MVP; endpoints are unauthenticated for local development and CI testing.
- Local development will use a lightweight local database for seeding and CI integration tests; production concerns (scaling, HA) are out of scope for this spec.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: API returns recommendations in under 1s for local development environment (measured in CI with seeded dataset).
- **SC-002**: Seed script populates at least 5 distinct dates of sample data and CI integration tests validate that at least one flight per fare type exists for each seeded date.
- **SC-003**: 100% of automated contract tests (if using OpenAPI-based contract tests) pass in CI for the endpoints defined.
- **SC-004**: Error responses follow the structured error model and include helpful `detail` messages for validation failures.

