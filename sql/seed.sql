-- =========================================================
-- EcoAccess Demo / Seed Data
-- =========================================================

-- =========================
-- PASSENGERS
-- =========================

INSERT INTO passengers
(id, name, mobile, email, password, reward_points)
VALUES
('P1001', 'Ashish Sharma', '+919876543210', 'ashish@gmail.com', 'Test@123', 320),
('P1002', 'Yogesh Bhangale', '+919988776655', 'yogesh@gmail.com', 'Test@123', 180);


-- =========================
-- STAFF
-- =========================

INSERT INTO staff
(id, employee_id, name, password, role, status)
VALUES
('STF1001', 'STF1001', 'Aditya Chavan', 'Test@123', 'Porter', 'Available'),
('STF1002', 'STF1002', 'Neeraj', 'Test@123', 'Wheelchair', 'Available');


-- =========================
-- ADMIN
-- =========================

INSERT INTO admins
(id, name, email, password)
VALUES
('ADM1', 'Administrator', 'admin@ecoaccess.com', 'Test@123');


-- =========================
-- STATIONS
-- =========================

INSERT INTO stations
(id, name)
VALUES
('STN001', 'Mumbai Central'),
('STN002', 'Thane'),
('STN003', 'Pune Junction');


-- =========================
-- TRAINS
-- =========================

INSERT INTO trains
(id, train_number, train_name)
VALUES
('TRN001', '12951', 'Mumbai Central - New Delhi'),
('TRN002', '11010', 'Thane - Pune'),
('TRN003', '12127', 'Pune - Mumbai CSMT');


-- =========================
-- JOURNEYS / PNR DATA
-- =========================

INSERT INTO journeys
(
    id,
    pnr,
    train_id,
    station_id,
    platform,
    journey_date,
    journey_time,
    source,
    destination,
    coach,
    travel_class
)
VALUES
(
    'JRN001',
    '4521987630',
    'TRN001',
    'STN001',
    4,
    '2026-09-20',
    '10:30:00',
    'Mumbai Central',
    'New Delhi',
    'B2',
    '3A'
),
(
    'JRN002',
    '6109873421',
    'TRN002',
    'STN002',
    2,
    '2026-09-22',
    '16:00:00',
    'Thane',
    'Pune Junction',
    'D1',
    'CC'
),
(
    'JRN003',
    '8234561907',
    'TRN003',
    'STN003',
    1,
    '2026-09-25',
    '08:15:00',
    'Pune Junction',
    'Mumbai CSMT',
    'A1',
    '2A'
);


-- =========================
-- WHEELCHAIRS
-- =========================

INSERT INTO wheelchairs
(id, station_id, quantity)
VALUES
('WC1', 'STN001', 8),
('WC2', 'STN002', 6);


-- =========================
-- VEHICLES
-- =========================

INSERT INTO vehicles
(id, station_id, quantity)
VALUES
('V1', 'STN001', 6),
('V2', 'STN002', 5);


-- =========================
-- SAMPLE BOOKINGS
-- =========================

INSERT INTO bookings
(
    id,
    passenger_id,
    journey_id,
    service,
    station_id,
    platform,
    booking_date,
    booking_time,
    pickup_point,
    drop_platform,
    passenger_count,
    bags,
    weight_range,
    base_fare,
    tax_amount,
    gross_fare,
    discount,
    final_fare,
    status,
    staff_id
)
VALUES
(
    'BK-240101',
    'P1001',
    'JRN001',
    'Wheelchair',
    'STN001',
    4,
    '2026-09-15',
    '10:30:00',
    'Main entrance Gate 2',
    'Platform 5 waiting hall',
    1,
    0,
    NULL,
    50.00,
    2.50,
    52.50,
    0.00,
    52.50,
    'Assigned',
    'STF1002'
),
(
    'BK-240102',
    'P1002',
    'JRN002',
    'Porter',
    'STN002',
    2,
    '2026-09-16',
    '16:00:00',
    'Taxi stand',
    'Platform 1',
    1,
    1,
    '1-10 kg',
    50.00,
    2.50,
    52.50,
    0.00,
    52.50,
    'Booked',
    NULL
);


-- =========================
-- BOOKING STATUS HISTORY
-- =========================

INSERT INTO booking_status_history
(
    booking_id,
    old_status,
    new_status,
    changed_by
)
VALUES
(
    'BK-240101',
    NULL,
    'Booked',
    'P1001'
),
(
    'BK-240101',
    'Booked',
    'Assigned',
    'STF1002'
),
(
    'BK-240102',
    NULL,
    'Booked',
    'P1002'
);


-- =========================
-- PAYMENTS
-- =========================

INSERT INTO payments
(
    id,
    booking_id,
    amount,
    payment_method,
    payment_status,
    transaction_id,
    paid_at
)
VALUES
(
    'PAY001',
    'BK-240101',
    52.50,
    'UPI',
    'SUCCESS',
    'TXN-WC-001',
    CURRENT_TIMESTAMP
),
(
    'PAY002',
    'BK-240102',
    52.50,
    'CARD',
    'SUCCESS',
    'TXN-PT-001',
    CURRENT_TIMESTAMP
);


-- =========================
-- REWARD TRANSACTIONS
-- =========================

INSERT INTO reward_transactions
(
    id,
    passenger_id,
    transaction_type,
    points,
    reference_type,
    reference_id,
    description
)
VALUES
(
    'RT001',
    'P1001',
    'EARN',
    320,
    'INITIAL',
    'P1001',
    'Initial demo reward points'
),
(
    'RT002',
    'P1002',
    'EARN',
    180,
    'INITIAL',
    'P1002',
    'Initial demo reward points'
);


-- =========================
-- REWARD CATALOG
-- =========================
-- Note:
-- The current database design does not have a separate
-- reward_catalog table. These frontend reward examples
-- will be handled later in the service layer.


-- =========================
-- SAMPLE COMPLAINT
-- =========================

INSERT INTO complaints
(
    id,
    passenger_id,
    booking_id,
    subject,
    description,
    rating,
    status
)
VALUES
(
    'CMP001',
    'P1001',
    'BK-240101',
    'Wheelchair Service',
    'Demo complaint for testing.',
    4,
    'Open'
);


-- =========================
-- SAMPLE FEEDBACK
-- =========================

INSERT INTO feedback
(
    id,
    passenger_id,
    booking_id,
    rating,
    subject,
    description,
    status
)
VALUES
(
    'FDB001',
    'P1001',
    'BK-240101',
    5,
    'Service Feedback',
    'Demo feedback for testing.',
    'Submitted'
);
