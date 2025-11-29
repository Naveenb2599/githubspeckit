#!/usr/bin/env bash
set -euo pipefail

DB_FILE="${1:-data/recommendations.db}"

if ! command -v sqlite3 >/dev/null 2>&1; then
  echo "sqlite3 not available" >&2
  exit 2
fi

echo "Verifying seed data in $DB_FILE"

# Check there are flights for the seeded dates
cnt=$(sqlite3 "$DB_FILE" "SELECT COUNT(*) FROM flight;")
echo "Flight rows: $cnt"
if [ "$cnt" -lt 5 ]; then
  echo "Expected at least 5 flights across dates" >&2
  exit 3
fi

# Check at least one fare_type per day exists (sample check)
for d in 2025-12-01 2025-12-02 2025-12-03 2025-12-04 2025-12-05; do
  c=$(sqlite3 "$DB_FILE" "SELECT COUNT(*) FROM fare f JOIN flight fl ON f.flight_id = fl.flight_id WHERE fl.date = '$d';")
  echo "$d -> $c fares"
  if [ "$c" -lt 5 ]; then
    echo "Expected at least 5 fares on $d" >&2
    exit 4
  fi
done

echo "Seed verification passed"
