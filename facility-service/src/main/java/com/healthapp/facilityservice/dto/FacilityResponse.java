package com.healthapp.facilityservice.dto;

import com.healthapp.facilityservice.entity.Facility;

import java.math.BigDecimal;

public record FacilityResponse(
        Long id,
        String name,
        String type,
        BigDecimal latitude,
        BigDecimal longitude,
        String phone,
        boolean available,
        Double distanceKm
) {
    public static FacilityResponse from(Facility f, Double distanceKm) {
        return new FacilityResponse(f.getId(), f.getName(), f.getType(), f.getLatitude(), f.getLongitude(),
                f.getPhone(), f.isAvailable(), distanceKm);
    }
}
