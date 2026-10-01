package com.healthapp.emergencyservice.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TriggerEmergencyRequest(
        @NotNull(message = "Latitude is required")
        BigDecimal latitude,

        @NotNull(message = "Longitude is required")
        BigDecimal longitude
) {
}
