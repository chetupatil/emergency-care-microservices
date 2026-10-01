package com.healthapp.facilityservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic emergencyEventsTopic() {
        return TopicBuilder.name("emergency-events").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic facilityEventsTopic() {
        return TopicBuilder.name("facility-events").partitions(3).replicas(1).build();
    }
}
