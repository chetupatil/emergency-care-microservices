package com.healthapp.locationservice.repository;

import com.healthapp.locationservice.entity.LocationPing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationPingRepository extends JpaRepository<LocationPing, Long> {
    List<LocationPing> findByEmergencyEventIdOrderByRecordedAtAsc(Long emergencyEventId);
    Optional<LocationPing> findTopByEmergencyEventIdOrderByRecordedAtDesc(Long emergencyEventId);
}
