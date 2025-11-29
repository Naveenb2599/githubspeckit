# Data Model

Entities and fields for the Flight Recommendations prototype.

1) Flight
- `id` (UUID / String): primary identifier for the scheduled flight (flight_id)
- `airline` (String): IATA or carrier display name
- `origin` (String): IATA airport code (3 chars)
- `destination` (String): IATA airport code (3 chars)
- `date` (date): flight date (YYYY-MM-DD)
- `departure_time` (time or string): local departure time (ISO time)
- `arrival_time` (time or string): local arrival time (ISO time)
- `duration_minutes` (int): flight duration in minutes
- `created_at` (timestamp)
- `updated_at` (timestamp)

Indexes & Constraints:
- Unique constraint on (`airline`, `origin`, `destination`, `date`, `departure_time`) where appropriate to avoid duplicate seed lines.
- Index on (`origin`, `destination`, `date`) to speed recommendations queries.

2) Fare
- `id` (auto / UUID)
- `flight_id` (FK -> Flight.id)
- `fare_type` (Enum): [saver, roundtrip_saver, flex, business, first_class]
- `price_cents` (int): store prices as integer cents to avoid floating point
- `currency` (String): ISO currency code (e.g., USD)
- `refundable` (boolean)
- `rules` (text): short human-readable rules summary

Relationships:
- One `Flight` has many `Fare` records.

3) Recommendation (response view)
- Not persisted as a separate table in the prototype. A Recommendation is a projection combining Flight + Fare(s) + ranking metadata.

Validation rules:
- `origin`/`destination`: 3-letter IATA codes, uppercase.
- `date`: must be valid ISO date; range limited to seeded window for demonstration.
- `fare_type`: must be one of the enumerated values.

Seeding expectations:
- Seed ~5 days of data with 3–8 flights per day; ensure at least one of each `fare_type` per day.
