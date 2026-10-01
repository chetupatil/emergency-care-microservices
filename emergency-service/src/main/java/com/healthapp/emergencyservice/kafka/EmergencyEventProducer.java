package com.healthapp.emergencyservice.kafka;

import com.healthapp.emergencyservice.dto.EmergencyTriggeredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmergencyEventProducer {

    private static final String TOPIC = "emergency-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EmergencyEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(EmergencyTriggeredEvent event) {
        // Keyed by userId so all events for the same user land on the same partition (ordering).
        kafkaTemplate.send(TOPIC, String.valueOf(event.userId()), event);
    }
}
