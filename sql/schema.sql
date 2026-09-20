-- =========================================================
-- EcoAccess Database Schema
-- PostgreSQL
-- =========================================================

-- =========================================================
-- 1. PASSENGERS
-- =========================================================

CREATE TABLE passengers (
    id              VARCHAR(20) PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    mobile          VARCHAR(15) NOT NULL UNIQUE,
    email           VARCHAR(150) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    reward_points   INTEGER NOT NULL DEFAULT 0 CHECK (reward_points >= 0),
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =========================================================
-- 2. STAFF
-- =========================================================

CREATE TABLE staff (
    id              VARCHAR(20) PRIMARY KEY,
    employee_id     VARCHAR(20) NOT NULL UNIQUE,
    name            VARCHAR(100) NOT NULL,
    password        VARCHAR(255) NOT NULL,

    role            VARCHAR(30) NOT NULL
                    CHECK (role IN ('Porter', 'Wheelchair', 'Vehicle')),

    status          VARCHAR(30) NOT NULL DEFAULT 'Available'
                    CHECK (status IN ('Available', 'Unavailable', 'Busy', 'Offline')),

    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =========================================================
-- 3. ADMINS
-- =========================================================

CREATE TABLE admins (
    id              VARCHAR(20) PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =========================================================
-- 4. STATIONS
-- =========================================================

CREATE TABLE stations (
    id              VARCHAR(20) PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE
);


-- =========================================================
-- 5. TRAINS
-- =========================================================

CREATE TABLE trains (
    id              VARCHAR(20) PRIMARY KEY,
    train_number    VARCHAR(10) NOT NULL UNIQUE,
    train_name      VARCHAR(150)
);


-- =========================================================
-- 6. JOURNEYS
-- =========================================================

CREATE TABLE journeys (
    id              VARCHAR(20) PRIMARY KEY,

    pnr             VARCHAR(10) NOT NULL UNIQUE,

    train_id        VARCHAR(20) NOT NULL,
    station_id      VARCHAR(20) NOT NULL,

    platform        INTEGER NOT NULL
                    CHECK (platform BETWEEN 1 AND 20),

    journey_date    DATE NOT NULL,
    journey_time    TIME NOT NULL,

    source          VARCHAR(100) NOT NULL,
    destination     VARCHAR(100) NOT NULL,

    coach           VARCHAR(20),
    travel_class    VARCHAR(20),

    CONSTRAINT fk_journey_train
        FOREIGN KEY (train_id)
        REFERENCES trains(id),

    CONSTRAINT fk_journey_station
        FOREIGN KEY (station_id)
        REFERENCES stations(id)
);


-- =========================================================
-- 7. JOURNEY VALIDATIONS
-- =========================================================

CREATE TABLE journey_validations (
    id              VARCHAR(20) PRIMARY KEY,

    passenger_id    VARCHAR(20) NOT NULL,
    journey_id      VARCHAR(20) NOT NULL,

    validated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_validation_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id),

    CONSTRAINT fk_validation_journey
        FOREIGN KEY (journey_id)
        REFERENCES journeys(id),

    CONSTRAINT uq_passenger_journey
        UNIQUE (passenger_id, journey_id)
);


-- =========================================================
-- 8. WHEELCHAIRS
-- =========================================================

CREATE TABLE wheelchairs (
    id              VARCHAR(20) PRIMARY KEY,

    station_id      VARCHAR(20) NOT NULL,

    quantity        INTEGER NOT NULL DEFAULT 0
                    CHECK (quantity >= 0),

    CONSTRAINT fk_wheelchair_station
        FOREIGN KEY (station_id)
        REFERENCES stations(id),

    CONSTRAINT uq_wheelchair_station
        UNIQUE (station_id)
);


-- =========================================================
-- 9. VEHICLES
-- =========================================================

CREATE TABLE vehicles (
    id              VARCHAR(20) PRIMARY KEY,

    station_id      VARCHAR(20) NOT NULL,

    quantity        INTEGER NOT NULL DEFAULT 0
                    CHECK (quantity >= 0),

    CONSTRAINT fk_vehicle_station
        FOREIGN KEY (station_id)
        REFERENCES stations(id),

    CONSTRAINT uq_vehicle_station
        UNIQUE (station_id)
);


-- =========================================================
-- 10. BOOKINGS
-- =========================================================

CREATE TABLE bookings (
    id                  VARCHAR(30) PRIMARY KEY,

    passenger_id        VARCHAR(20) NOT NULL,
    journey_id          VARCHAR(20) NOT NULL,

    service             VARCHAR(30) NOT NULL
                        CHECK (
                            service IN (
                                'Porter',
                                'Wheelchair',
                                'Inter Vehicle'
                            )
                        ),

    station_id          VARCHAR(20) NOT NULL,

    platform            INTEGER
                        CHECK (platform BETWEEN 1 AND 20),

    booking_date        DATE NOT NULL,
    booking_time        TIME NOT NULL,

    pickup_point        VARCHAR(200) NOT NULL,
    drop_platform       VARCHAR(100) NOT NULL,

    passenger_count     INTEGER NOT NULL DEFAULT 1
                        CHECK (passenger_count BETWEEN 1 AND 8),

    bags                INTEGER DEFAULT 0
                        CHECK (bags >= 0 AND bags <= 20),

    weight_range        VARCHAR(100),

    base_fare           NUMERIC(10,2) NOT NULL
                        CHECK (base_fare >= 0),

    tax_amount          NUMERIC(10,2) NOT NULL
                        CHECK (tax_amount >= 0),

    gross_fare          NUMERIC(10,2) NOT NULL
                        CHECK (gross_fare >= 0),

    discount            NUMERIC(10,2) NOT NULL DEFAULT 0
                        CHECK (discount >= 0),

    final_fare          NUMERIC(10,2) NOT NULL
                        CHECK (final_fare >= 0),

    status              VARCHAR(30) NOT NULL DEFAULT 'Booked'
                        CHECK (
                            status IN (
                                'Booked',
                                'Assigned',
                                'Accepted',
                                'Reached Passenger',
                                'Service Started',
                                'Completed',
                                'Rejected'
                            )
                        ),

    staff_id            VARCHAR(20),

    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_booking_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id),

    CONSTRAINT fk_booking_journey
        FOREIGN KEY (journey_id)
        REFERENCES journeys(id),

    CONSTRAINT fk_booking_station
        FOREIGN KEY (station_id)
        REFERENCES stations(id),

    CONSTRAINT fk_booking_staff
        FOREIGN KEY (staff_id)
        REFERENCES staff(id)
);


-- =========================================================
-- 11. BOOKING STATUS HISTORY
-- =========================================================

CREATE TABLE booking_status_history (
    id              BIGSERIAL PRIMARY KEY,

    booking_id      VARCHAR(30) NOT NULL,

    old_status      VARCHAR(30),
    new_status      VARCHAR(30) NOT NULL,

    changed_by      VARCHAR(20),
    changed_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_status_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
        ON DELETE CASCADE
);


-- =========================================================
-- 12. PAYMENTS
-- =========================================================

CREATE TABLE payments (
    id              VARCHAR(30) PRIMARY KEY,

    booking_id      VARCHAR(30) NOT NULL UNIQUE,

    amount          NUMERIC(10,2) NOT NULL
                    CHECK (amount >= 0),

    payment_method  VARCHAR(20) NOT NULL
                    CHECK (payment_method IN ('UPI', 'CARD')),

    payment_status  VARCHAR(20) NOT NULL DEFAULT 'SUCCESS'
                    CHECK (
                        payment_status IN (
                            'PENDING',
                            'SUCCESS',
                            'FAILED'
                        )
                    ),

    transaction_id  VARCHAR(50),

    paid_at         TIMESTAMP,

    CONSTRAINT fk_payment_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
);


-- =========================================================
-- 13. WASTE SUBMISSIONS
-- =========================================================

CREATE TABLE waste_submissions (
    id              VARCHAR(30) PRIMARY KEY,

    passenger_id    VARCHAR(20) NOT NULL,

    photo           TEXT NOT NULL,

    status          VARCHAR(20) NOT NULL DEFAULT 'Pending'
                    CHECK (
                        status IN (
                            'Pending',
                            'Accepted',
                            'Rejected'
                        )
                    ),

    reward_points   INTEGER NOT NULL DEFAULT 0
                    CHECK (reward_points >= 0),

    remark          VARCHAR(500),

    submitted_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviewed_at     TIMESTAMP,

    reviewed_by     VARCHAR(20),

    CONSTRAINT fk_waste_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id),

    CONSTRAINT fk_waste_admin
        FOREIGN KEY (reviewed_by)
        REFERENCES admins(id)
);


-- =========================================================
-- 14. REWARD TRANSACTIONS
-- =========================================================

CREATE TABLE reward_transactions (
    id              VARCHAR(30) PRIMARY KEY,

    passenger_id    VARCHAR(20) NOT NULL,

    transaction_type VARCHAR(20) NOT NULL
                     CHECK (
                         transaction_type IN (
                             'EARN',
                             'REDEEM',
                             'REFUND'
                         )
                     ),

    points          INTEGER NOT NULL
                    CHECK (points > 0),

    reference_type  VARCHAR(30),

    reference_id    VARCHAR(30),

    description     VARCHAR(255),

    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reward_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id)
);


-- =========================================================
-- 15. COUPONS
-- =========================================================

CREATE TABLE coupons (
    id                  VARCHAR(30) PRIMARY KEY,

    code                VARCHAR(50) NOT NULL UNIQUE,

    passenger_id        VARCHAR(20) NOT NULL,

    points_redeemed     INTEGER NOT NULL
                        CHECK (points_redeemed > 0),

    value               NUMERIC(10,2) NOT NULL
                        CHECK (value >= 0),

    remaining           NUMERIC(10,2) NOT NULL
                        CHECK (remaining >= 0),

    status              VARCHAR(20) NOT NULL DEFAULT 'Active'
                        CHECK (
                            status IN (
                                'Active',
                                'Used',
                                'Expired'
                            )
                        ),

    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    expires_at          TIMESTAMP NOT NULL,

    used_for            VARCHAR(20),

    used_on             VARCHAR(50),

    used_at             TIMESTAMP,

    CONSTRAINT fk_coupon_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id)
);


-- =========================================================
-- 16. COUPON USAGE
-- =========================================================

CREATE TABLE coupon_usage (
    id              VARCHAR(30) PRIMARY KEY,

    coupon_id       VARCHAR(30) NOT NULL,

    amount_used     NUMERIC(10,2) NOT NULL
                    CHECK (amount_used > 0),

    usage_type      VARCHAR(20) NOT NULL
                    CHECK (
                        usage_type IN (
                            'BOOKING',
                            'TRAIN'
                        )
                    ),

    reference_id    VARCHAR(50),

    used_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_coupon_usage_coupon
        FOREIGN KEY (coupon_id)
        REFERENCES coupons(id)
);


-- =========================================================
-- 17. REDEMPTIONS
-- =========================================================

CREATE TABLE redemptions (
    id              VARCHAR(30) PRIMARY KEY,

    passenger_id    VARCHAR(20) NOT NULL,

    coupon_id       VARCHAR(30),

    points          INTEGER NOT NULL
                    CHECK (points > 0),

    coupon_value    NUMERIC(10,2) NOT NULL
                    CHECK (coupon_value >= 0),

    status          VARCHAR(20) NOT NULL DEFAULT 'Issued'
                    CHECK (
                        status IN (
                            'Pending',
                            'Approved',
                            'Rejected',
                            'Issued'
                        )
                    ),

    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    expires_at      TIMESTAMP,

    CONSTRAINT fk_redemption_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id),

    CONSTRAINT fk_redemption_coupon
        FOREIGN KEY (coupon_id)
        REFERENCES coupons(id)
);


-- =========================================================
-- 18. COMPLAINTS
-- =========================================================

CREATE TABLE complaints (
    id              VARCHAR(30) PRIMARY KEY,

    passenger_id    VARCHAR(20) NOT NULL,

    booking_id      VARCHAR(30),

    subject         VARCHAR(200) NOT NULL,

    description     VARCHAR(1000) NOT NULL,

    rating          INTEGER
                    CHECK (rating BETWEEN 1 AND 5),

    status          VARCHAR(20) NOT NULL DEFAULT 'Open'
                    CHECK (
                        status IN (
                            'Open',
                            'Resolved',
                            'Closed'
                        )
                    ),

    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_complaint_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id),

    CONSTRAINT fk_complaint_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
);


-- =========================================================
-- 19. FEEDBACK
-- =========================================================

CREATE TABLE feedback (
    id              VARCHAR(30) PRIMARY KEY,

    passenger_id    VARCHAR(20) NOT NULL,

    booking_id      VARCHAR(30),

    rating          INTEGER
                    CHECK (rating BETWEEN 1 AND 5),

    subject         VARCHAR(200) NOT NULL,

    description     VARCHAR(1000) NOT NULL,

    status          VARCHAR(20) NOT NULL DEFAULT 'Submitted'
                    CHECK (
                        status IN (
                            'Submitted',
                            'Reviewed'
                        )
                    ),

    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_feedback_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passengers(id),

    CONSTRAINT fk_feedback_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
);


-- =========================================================
-- INDEXES
-- =========================================================

CREATE INDEX idx_passenger_mobile
    ON passengers(mobile);

CREATE INDEX idx_passenger_email
    ON passengers(email);

CREATE INDEX idx_staff_employee
    ON staff(employee_id);

CREATE INDEX idx_journey_pnr
    ON journeys(pnr);

CREATE INDEX idx_booking_passenger
    ON bookings(passenger_id);

CREATE INDEX idx_booking_status
    ON bookings(status);

CREATE INDEX idx_booking_staff
    ON bookings(staff_id);

CREATE INDEX idx_booking_station
    ON bookings(station_id);

CREATE INDEX idx_waste_passenger
    ON waste_submissions(passenger_id);

CREATE INDEX idx_waste_status
    ON waste_submissions(status);

CREATE INDEX idx_coupon_passenger
    ON coupons(passenger_id);

CREATE INDEX idx_coupon_status
    ON coupons(status);

CREATE INDEX idx_complaint_passenger
    ON complaints(passenger_id);

CREATE INDEX idx_complaint_status
    ON complaints(status);