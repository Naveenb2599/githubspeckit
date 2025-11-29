-- Create flights table
CREATE TABLE IF NOT EXISTS flight (
  flight_id TEXT PRIMARY KEY,
  airline TEXT NOT NULL,
  origin TEXT NOT NULL,
  destination TEXT NOT NULL,
  date TEXT NOT NULL,
  departure_time TEXT,
  arrival_time TEXT,
  duration_minutes INTEGER,
  created_at TEXT DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT DEFAULT CURRENT_TIMESTAMP
);

-- Create fares table
CREATE TABLE IF NOT EXISTS fare (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  flight_id TEXT NOT NULL,
  fare_type TEXT NOT NULL,
  price_cents INTEGER NOT NULL,
  currency TEXT NOT NULL,
  refundable INTEGER NOT NULL DEFAULT 0,
  rules TEXT,
  FOREIGN KEY (flight_id) REFERENCES flight(flight_id)
);

CREATE INDEX IF NOT EXISTS idx_flight_origin_dest_date ON flight(origin, destination, date);
