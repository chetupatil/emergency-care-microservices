CREATE TABLE notification_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    emergency_event_id BIGINT NOT NULL,
    recipient_type VARCHAR(20),
    recipient_contact VARCHAR(150),
    channel VARCHAR(20),
    status VARCHAR(20),
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_notification_logs_emergency_event_id (emergency_event_id)
);
