package com.healthapp.notificationservice.kafka;

import com.healthapp.notificationservice.dto.EmergencyTriggeredEvent;
import com.healthapp.notificationservice.service.NotificationDispatchService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmergencyTriggeredConsumer {

    private final NotificationDispatchService dispatchService;

    public EmergencyTriggeredConsumer(NotificationDispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @KafkaListener(topics = "emergency-events", groupId = "notification-service")
    public void onEmergencyTriggered(EmergencyTriggeredEvent event) {
        if (!"EMERGENCY_TRIGGERED".equals(event.eventType())) {
            return;
        }
        dispatchService.notifyFamilyOfEmergency(event.emergencyId(), event.userId());
    }
}
