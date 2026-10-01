package com.healthapp.facilityservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

/** Consumed from the "emergency-events" Kafka topic. */
public record EmergencyTriggeredEvent(
        String eventType,
        Long emergencyId,
        Long userId,
        BigDecimal latitude,
        BigDecimal longitude,
        Instant timestamp
) {
}
