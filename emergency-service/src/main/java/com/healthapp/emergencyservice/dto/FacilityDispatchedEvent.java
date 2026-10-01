package com.healthapp.emergencyservice.dto;

/** Consumed from the "facility-events" Kafka topic once a facility accepts dispatch. */
public record FacilityDispatchedEvent(
        String eventType,
        Long emergencyId,
        Long facilityId,
        Integer estimatedArrivalMinutes
) {
}
