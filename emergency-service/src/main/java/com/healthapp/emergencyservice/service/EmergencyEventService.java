package com.healthapp.emergencyservice.service;

import com.healthapp.emergencyservice.dto.EmergencyEventResponse;
import com.healthapp.emergencyservice.dto.EmergencyTriggeredEvent;
import com.healthapp.emergencyservice.dto.TriggerEmergencyRequest;
import com.healthapp.emergencyservice.entity.EmergencyEvent;
import com.healthapp.emergencyservice.entity.EmergencyStatus;
import com.healthapp.emergencyservice.exception.AccessDeniedForEmergencyException;
import com.healthapp.emergencyservice.exception.EmergencyEventNotFoundException;
import com.healthapp.emergencyservice.kafka.EmergencyEventProducer;
import com.healthapp.emergencyservice.repository.EmergencyEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmergencyEventService {

    private final EmergencyEventRepository repository;
    private final EmergencyEventProducer producer;

    public EmergencyEventService(EmergencyEventRepository repository, EmergencyEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public EmergencyEventResponse trigger(Long userId, TriggerEmergencyRequest request) {
        EmergencyEvent event = EmergencyEvent.builder()
                .userId(userId)
                .status(EmergencyStatus.TRIGGERED)
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();

        event = repository.save(event);

        producer.publish(EmergencyTriggeredEvent.of(event.getId(), userId, request.latitude(), request.longitude()));

        return EmergencyEventResponse.from(event);
    }

    @Transactional(readOnly = true)
    public EmergencyEventResponse getById(Long authenticatedUserId, Long id) {
        EmergencyEvent event = repository.findById(id).orElseThrow(() -> new EmergencyEventNotFoundException(id));
        requireSelf(authenticatedUserId, event.getUserId());
        return EmergencyEventResponse.from(event);
    }

    @Transactional(readOnly = true)
    public List<EmergencyEventResponse> myHistory(Long authenticatedUserId) {
        return repository.findByUserIdOrderByTriggeredAtDesc(authenticatedUserId).stream()
                .map(EmergencyEventResponse::from)
                .toList();
    }

    @Transactional
    public EmergencyEventResponse resolve(Long authenticatedUserId, Long id) {
        EmergencyEvent event = repository.findById(id).orElseThrow(() -> new EmergencyEventNotFoundException(id));
        requireSelf(authenticatedUserId, event.getUserId());
        event.setStatus(EmergencyStatus.RESOLVED);
        event.setResolvedAt(java.time.LocalDateTime.now());
        return EmergencyEventResponse.from(repository.save(event));
    }

    private void requireSelf(Long authenticatedUserId, Long ownerUserId) {
        if (!authenticatedUserId.equals(ownerUserId)) {
            throw new AccessDeniedForEmergencyException();
        }
    }
}
