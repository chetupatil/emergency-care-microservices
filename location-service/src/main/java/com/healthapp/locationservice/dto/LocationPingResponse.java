package com.healthapp.locationservice.dto;

import com.healthapp.locationservice.entity.LocationPing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LocationPingResponse(
        Long id,
        Long emergencyEventId,
        BigDecimal latitude,
        BigDecimal longitude,
        LocalDateTime recordedAt
) {
    public static LocationPingResponse from(LocationPing p) {
        return new LocationPingResponse(p.getId(), p.getEmergencyEventId(), p.getLatitude(), p.getLongitude(), p.getRecordedAt());
    }
}
