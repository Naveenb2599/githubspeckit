#!/usr/bin/env bash
set -euo pipefail

DB_FILE="${1:-data/recommendations.db}"
SEED_SQL="$(pwd)/specs/001-flight-recommendations/seed/seed-data.sql"

mkdir -p "$(dirname "$DB_FILE")"

if command -v sqlite3 >/dev/null 2>&1; then
  echo "Seeding database at $DB_FILE using $SEED_SQL"
  sqlite3 "$DB_FILE" < "$SEED_SQL"
  echo "Seed completed. DB: $DB_FILE"
else
  echo "sqlite3 CLI not found. Creating file and copying SQL as fallback."
  cp "$SEED_SQL" "${DB_FILE}.seed.sql"
  echo "Please run: sqlite3 $DB_FILE < ${DB_FILE}.seed.sql" >&2
  exit 1
fi
