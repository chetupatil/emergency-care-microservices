package com.healthapp.notificationservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record EmergencyTriggeredEvent(
        String eventType,
        Long emergencyId,
        Long userId,
        BigDecimal latitude,
        BigDecimal longitude,
        Instant timestamp
) {
}
