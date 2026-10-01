package com.healthapp.emergencyservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

/** Published to the "emergency-events" Kafka topic when an SOS is triggered. */
public record EmergencyTriggeredEvent(
        String eventType,
        Long emergencyId,
        Long userId,
        BigDecimal latitude,
        BigDecimal longitude,
        Instant timestamp
) {
    public static EmergencyTriggeredEvent of(Long emergencyId, Long userId, BigDecimal lat, BigDecimal lng) {
        return new EmergencyTriggeredEvent("EMERGENCY_TRIGGERED", emergencyId, userId, lat, lng, Instant.now());
    }
}
