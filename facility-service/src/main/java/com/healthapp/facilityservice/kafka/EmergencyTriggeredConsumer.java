package com.healthapp.facilityservice.kafka;

import com.healthapp.facilityservice.dto.EmergencyTriggeredEvent;
import com.healthapp.facilityservice.dto.FacilityDispatchedEvent;
import com.healthapp.facilityservice.entity.Facility;
import com.healthapp.facilityservice.service.FacilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmergencyTriggeredConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmergencyTriggeredConsumer.class);
    private static final String OUT_TOPIC = "facility-events";
    // Mock ETA: fixed base + a small amount per km, standing in for a real routing/traffic API.
    private static final int BASE_ETA_MINUTES = 4;

    private final FacilityService facilityService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EmergencyTriggeredConsumer(FacilityService facilityService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.facilityService = facilityService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "emergency-events", groupId = "facility-service")
    public void onEmergencyTriggered(EmergencyTriggeredEvent event) {
        if (!"EMERGENCY_TRIGGERED".equals(event.eventType())) {
            return;
        }

        Optional<Facility> nearest = facilityService.findNearestAvailable(event.latitude(), event.longitude());

        if (nearest.isEmpty()) {
            log.warn("No available facility found for emergency {}", event.emergencyId());
            return;
        }

        Facility facility = nearest.get();
        int etaMinutes = BASE_ETA_MINUTES + (int) (Math.random() * 6); // mock — replace with real routing later

        FacilityDispatchedEvent dispatched = FacilityDispatchedEvent.of(event.emergencyId(), facility.getId(), etaMinutes);
        kafkaTemplate.send(OUT_TOPIC, String.valueOf(event.userId()), dispatched);

        log.info("Dispatched facility {} ({}) for emergency {} — ETA {} min",
                facility.getId(), facility.getName(), event.emergencyId(), etaMinutes);
    }
}
