# API Development Constitution

## Core Principles

### 1. Design-First (Contract-Driven)
- Every public API MUST have an OpenAPI/AsyncAPI (or equivalent) contract as the source of truth.
- Contract changes require a visible diff, migration notes, and updated contract tests.

### 2. Semantic Versioning & Compatibility
- Use semantic versioning for releases. Encode API version in the URL or a stable header.
- Backwards-compatible changes (minor/patch) are allowed without breaking consumers.
- Breaking changes require a MAJOR version bump, migration guide, and a deprecation window.

### 3. Secure By Default
- Transport must use TLS. All endpoints requiring authentication must be behind standardized auth (OAuth2, JWT, or mTLS).
- Sensitive data must be redacted in logs. Secrets must not be committed.

### 4. Consistent Error Model
- Responses MUST follow a structured error format (e.g., RFC7807 `application/problem+json`): `type`, `title`, `status`, `detail`, `instance`, and optional `errors` array.

### 5. Observability
- Include request IDs for tracing. Emit structured logs, basic metrics (request count, latency, errors), and support distributed tracing headers.

### 6. Test-First and Contract Tests
- Implement unit tests, integration tests, and contract tests against the OpenAPI spec. CI must run contract tests and fail on regressions.

### 7. Documentation & Examples
- Publish the API spec and runnable examples (curl / httpie). Auto-generate docs from the contract; keep examples for common flows.

### 8. Minimal Performance and Reliability Expectations
- Document expected SLA (e.g., 99.9% uptime) and typical latency targets. Use pagination, caching, and rate limits where appropriate.

### 9. Simplicity and Principle of Least Surprise
- Keep endpoints focused and predictable. Prefer explicit fields over ambiguous polymorphism.

## Additional Constraints

- Data format: JSON by default; support content negotiation if alternative formats are required (e.g., protobuf) and documented.
- Pagination: adopt a single consistent pattern (cursor preferred; fallback limit/offset) and document in the contract.
- Idempotency: non-safe operations should support idempotency keys where needed.
- Caching: provide clear cache-control headers and document which responses are cachable.

## Database Integration

- Schema & Migrations:
	- All schema changes MUST be implemented via a versioned migration system (e.g., Flyway, Liquibase, Alembic, Rails migrations, or equivalent).
	- Migrations must be reversible where practical and include a migration plan for large data migrations (backfills, batch jobs).
	- Database migrations that change contracts (field removals/renames/type changes) require a compatibility plan and a deprecation window.

- Connection Management & Pooling:
	- Services must use connection pooling and tune pool sizes for expected concurrency to avoid exhausting DB connections.
	- Timeouts and retry policies for transient errors must be defined and implemented consistently.

- Secrets & Credentials:
	- Database credentials and connection strings MUST be stored in a secrets manager (do not commit to source control).
	- Use least-privilege database roles for application workloads; separate roles for migrations/administration.

- Backup, Restore & Retention:
	- Define backup schedules, retention policies, and documented restore procedures. Perform regular restore drills.
	- Critical data must have point-in-time recovery or equivalent where supported.

- Transactions & Data Integrity:
	- Use transactions for multi-step updates to maintain consistency. Prefer idempotent operations for retries.
	- Enforce constraints (FKs, unique indexes) at the database level where appropriate.

- Monitoring & Alerts:
	- Monitor DB connectivity, replication lag, long-running queries, connection pool saturation, and storage usage.
	- Alert on thresholds that indicate degraded service (e.g., high error rate, replication lag, low disk space).

- Performance & Scaling:
	- Document expected load patterns and the scaling strategy (vertical scaling, read replicas, partitioning/sharding).
	- Use indices carefully and review query plans for slow queries; track slow query logs.

- Privacy & Security:
	- Encrypt sensitive data at rest where required and limit access via RBAC.
	- Mask or redact PII in logs and backups when required by policy/compliance.

- CI/CD & Migrations:
	- CI must run migration checks (linting, dry-run) and validate that migrations can be applied non-destructively.
	- Production migrations must follow an approved deployment process and include rollback/runbook steps.

## Development Workflow

- Any change to the API MUST include:
	- Updated OpenAPI/contract file.
	- Contract tests exercising the changed behavior.
	- A short migration note (if applicable) in the PR description.
- CI gates: lint contract, run unit tests, run contract tests, run security/static analysis.
- Review: at least one reviewer with API/domain knowledge must approve changes.

## Governance

- Amendments to this constitution must be proposed via PR that includes:
	- A short rationale.
	- Backwards-compatibility assessment.
	- Migration plan and timeline for affected consumers.
- Changes are ratified after at least one approving review from the API owners and passing CI.

**Version**: 1.0.0 | **Ratified**: 2025-11-29 | **Last Amended**: 2025-11-29

