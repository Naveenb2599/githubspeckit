BEGIN TRANSACTION;

-- Clear existing data
DELETE FROM fare;
DELETE FROM flight;

-- Seed flights and fares for 5 days (2025-12-01 .. 2025-12-05)

-- Day 1: 2025-12-01
INSERT INTO flight (flight_id, airline, origin, destination, date, departure_time, arrival_time, duration_minutes) VALUES
('F20251201A', 'ExampleAir', 'JFK', 'LAX', '2025-12-01', '08:00', '11:00', 360),
('F20251201B', 'SampleAir', 'JFK', 'LAX', '2025-12-01', '12:00', '15:00', 360),
('F20251201C', 'DemoAir', 'JFK', 'LAX', '2025-12-01', '18:00', '21:00', 360);

INSERT INTO fare (flight_id, fare_type, price_cents, currency, refundable, rules) VALUES
('F20251201A','saver',50000,'USD',0,'Non-refundable'),
('F20251201A','roundtrip_saver',90000,'USD',0,'Roundtrip saver'),
('F20251201A','flex',70000,'USD',1,'Changeable'),
('F20251201A','business',150000,'USD',1,'Business class seating'),
('F20251201A','first_class',250000,'USD',1,'First class seating'),

('F20251201B','saver',48000,'USD',0,'Non-refundable'),
('F20251201B','flex',68000,'USD',1,'Changeable'),
('F20251201B','business',145000,'USD',1,'Business'),
('F20251201B','first_class',240000,'USD',1,'First'),
('F20251201B','roundtrip_saver',88000,'USD',0,'Roundtrip saver'),

('F20251201C','saver',51000,'USD',0,'Non-refundable'),
('F20251201C','flex',71000,'USD',1,'Changeable'),
('F20251201C','business',152000,'USD',1,'Business'),
('F20251201C','first_class',255000,'USD',1,'First'),
('F20251201C','roundtrip_saver',92000,'USD',0,'Roundtrip saver');

-- Day 2: 2025-12-02
INSERT INTO flight (flight_id, airline, origin, destination, date, departure_time, arrival_time, duration_minutes) VALUES
('F20251202A', 'ExampleAir', 'JFK', 'LAX', '2025-12-02', '08:30', '11:30', 360),
('F20251202B', 'SampleAir', 'JFK', 'LAX', '2025-12-02', '13:00', '16:00', 360);

INSERT INTO fare (flight_id, fare_type, price_cents, currency, refundable, rules) VALUES
('F20251202A','saver',50500,'USD',0,'Non-refundable'),
('F20251202A','flex',70500,'USD',1,'Changeable'),
('F20251202A','business',151000,'USD',1,'Business'),
('F20251202A','first_class',251000,'USD',1,'First'),
('F20251202A','roundtrip_saver',90500,'USD',0,'Roundtrip saver'),

('F20251202B','saver',49000,'USD',0,'Non-refundable'),
('F20251202B','flex',69000,'USD',1,'Changeable'),
('F20251202B','business',148000,'USD',1,'Business'),
('F20251202B','first_class',242000,'USD',1,'First'),
('F20251202B','roundtrip_saver',88500,'USD',0,'Roundtrip saver');

-- Day 3: 2025-12-03
INSERT INTO flight (flight_id, airline, origin, destination, date, departure_time, arrival_time, duration_minutes) VALUES
('F20251203A', 'DemoAir', 'JFK', 'LAX', '2025-12-03', '09:00', '12:00', 360);

INSERT INTO fare (flight_id, fare_type, price_cents, currency, refundable, rules) VALUES
('F20251203A','saver',52000,'USD',0,'Non-refundable'),
('F20251203A','flex',72000,'USD',1,'Changeable'),
('F20251203A','business',155000,'USD',1,'Business'),
('F20251203A','first_class',260000,'USD',1,'First'),
('F20251203A','roundtrip_saver',94000,'USD',0,'Roundtrip saver');

-- Day 4: 2025-12-04
INSERT INTO flight (flight_id, airline, origin, destination, date, departure_time, arrival_time, duration_minutes) VALUES
('F20251204A', 'ExampleAir', 'JFK', 'LAX', '2025-12-04', '07:00', '10:00', 360);

INSERT INTO fare (flight_id, fare_type, price_cents, currency, refundable, rules) VALUES
('F20251204A','saver',47000,'USD',0,'Non-refundable'),
('F20251204A','flex',67000,'USD',1,'Changeable'),
('F20251204A','business',140000,'USD',1,'Business'),
('F20251204A','first_class',235000,'USD',1,'First'),
('F20251204A','roundtrip_saver',86000,'USD',0,'Roundtrip saver');

-- Day 5: 2025-12-05
INSERT INTO flight (flight_id, airline, origin, destination, date, departure_time, arrival_time, duration_minutes) VALUES
('F20251205A', 'SampleAir', 'JFK', 'LAX', '2025-12-05', '17:00', '20:00', 360);

INSERT INTO fare (flight_id, fare_type, price_cents, currency, refundable, rules) VALUES
('F20251205A','saver',53000,'USD',0,'Non-refundable'),
('F20251205A','flex',73000,'USD',1,'Changeable'),
('F20251205A','business',160000,'USD',1,'Business'),
('F20251205A','first_class',265000,'USD',1,'First'),
('F20251205A','roundtrip_saver',98000,'USD',0,'Roundtrip saver');

COMMIT;
