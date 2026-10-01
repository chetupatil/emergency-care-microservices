package com.healthapp.facilityservice.dto;

/** Published to the "facility-events" Kafka topic once a facility is chosen. */
public record FacilityDispatchedEvent(
        String eventType,
        Long emergencyId,
        Long facilityId,
        Integer estimatedArrivalMinutes
) {
    public static FacilityDispatchedEvent of(Long emergencyId, Long facilityId, Integer etaMinutes) {
        return new FacilityDispatchedEvent("FACILITY_DISPATCHED", emergencyId, facilityId, etaMinutes);
    }
}
