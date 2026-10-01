package com.healthapp.locationservice.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PingRequest(
        @NotNull(message = "Latitude is required")
        BigDecimal latitude,

        @NotNull(message = "Longitude is required")
        BigDecimal longitude
) {
}
