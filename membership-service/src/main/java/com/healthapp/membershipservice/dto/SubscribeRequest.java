package com.healthapp.membershipservice.dto;

import jakarta.validation.constraints.NotBlank;

public record SubscribeRequest(
        @NotBlank(message = "Plan name is required")
        String planName
) {
}
