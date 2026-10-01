package com.healthapp.membershipservice.dto;

import com.healthapp.membershipservice.entity.UserMembership;

import java.time.LocalDate;

public record MembershipResponse(
        Long userId,
        String planName,
        Integer maxEmergencyContacts,
        Integer facilitySearchRadiusKm,
        String status,
        LocalDate startDate,
        LocalDate endDate
) {
    public static MembershipResponse from(UserMembership m) {
        return new MembershipResponse(
                m.getUserId(),
                m.getPlan().getPlanName(),
                m.getPlan().getMaxEmergencyContacts(),
                m.getPlan().getFacilitySearchRadiusKm(),
                m.getStatus().name(),
                m.getStartDate(),
                m.getEndDate()
        );
    }
}
