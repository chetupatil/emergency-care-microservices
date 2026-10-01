CREATE TABLE location_pings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    emergency_event_id BIGINT NOT NULL,
    latitude DECIMAL(10,7) NOT NULL,
    longitude DECIMAL(10,7) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_location_pings_emergency_event_id (emergency_event_id)
);
