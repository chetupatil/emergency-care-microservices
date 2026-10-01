package com.healthapp.emergencyservice.dto;

import com.healthapp.emergencyservice.entity.EmergencyEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmergencyEventResponse(
        Long id,
        Long userId,
        String status,
        BigDecimal latitude,
        BigDecimal longitude,
        LocalDateTime triggeredAt,
        LocalDateTime resolvedAt
) {
    public static EmergencyEventResponse from(EmergencyEvent event) {
        return new EmergencyEventResponse(
                event.getId(),
                event.getUserId(),
                event.getStatus().name(),
                event.getLatitude(),
                event.getLongitude(),
                event.getTriggeredAt(),
                event.getResolvedAt()
        );
    }
}
