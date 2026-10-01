package com.healthapp.locationservice.service;

import com.healthapp.locationservice.dto.LocationPingResponse;
import com.healthapp.locationservice.dto.PingRequest;
import com.healthapp.locationservice.entity.LocationPing;
import com.healthapp.locationservice.repository.LocationPingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocationPingService {

    private final LocationPingRepository repository;

    public LocationPingService(LocationPingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public LocationPingResponse recordPing(Long emergencyEventId, PingRequest request) {
        LocationPing ping = LocationPing.builder()
                .emergencyEventId(emergencyEventId)
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();
        return LocationPingResponse.from(repository.save(ping));
    }

    @Transactional(readOnly = true)
    public Optional<LocationPingResponse> latest(Long emergencyEventId) {
        return repository.findTopByEmergencyEventIdOrderByRecordedAtDesc(emergencyEventId)
                .map(LocationPingResponse::from);
    }

    @Transactional(readOnly = true)
    public List<LocationPingResponse> history(Long emergencyEventId) {
        return repository.findByEmergencyEventIdOrderByRecordedAtAsc(emergencyEventId).stream()
                .map(LocationPingResponse::from)
                .toList();
    }
}
