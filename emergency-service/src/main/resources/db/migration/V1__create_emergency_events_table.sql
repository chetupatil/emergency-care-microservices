CREATE TABLE emergency_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'TRIGGERED',
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    triggered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,
    INDEX idx_emergency_events_user_id (user_id)
);
