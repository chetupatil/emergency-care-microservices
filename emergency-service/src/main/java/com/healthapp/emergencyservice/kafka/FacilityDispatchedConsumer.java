package com.healthapp.emergencyservice.kafka;

import com.healthapp.emergencyservice.dto.FacilityDispatchedEvent;
import com.healthapp.emergencyservice.entity.EmergencyEvent;
import com.healthapp.emergencyservice.entity.EmergencyStatus;
import com.healthapp.emergencyservice.repository.EmergencyEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FacilityDispatchedConsumer {

    private static final Logger log = LoggerFactory.getLogger(FacilityDispatchedConsumer.class);

    private final EmergencyEventRepository repository;

    public FacilityDispatchedConsumer(EmergencyEventRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "facility-events", groupId = "emergency-service")
    @Transactional
    public void onFacilityDispatched(FacilityDispatchedEvent event) {
        if (!"FACILITY_DISPATCHED".equals(event.eventType())) {
            return;
        }
        repository.findById(event.emergencyId()).ifPresentOrElse(emergencyEvent -> {
            emergencyEvent.setStatus(EmergencyStatus.DISPATCHED);
            repository.save(emergencyEvent);
            log.info("Emergency {} marked DISPATCHED (facility {}, ETA {} min)",
                    event.emergencyId(), event.facilityId(), event.estimatedArrivalMinutes());
        }, () -> log.warn("Received FacilityDispatched for unknown emergencyId {}", event.emergencyId()));
    }
}
