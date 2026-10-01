package com.healthapp.userservice.dto;

import com.healthapp.userservice.entity.User;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        LocalDate dateOfBirth,
        String bloodGroup,
        String knownAllergies,
        String knownConditions,
        String address
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getDateOfBirth(),
                user.getBloodGroup(),
                user.getKnownAllergies(),
                user.getKnownConditions(),
                user.getAddress()
        );
    }
}
