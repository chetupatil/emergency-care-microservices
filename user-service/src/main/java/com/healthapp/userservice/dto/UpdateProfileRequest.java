package com.healthapp.userservice.dto;

import java.time.LocalDate;

public record UpdateProfileRequest(
        String fullName,
        LocalDate dateOfBirth,
        String bloodGroup,
        String knownAllergies,
        String knownConditions,
        String address
) {
}
