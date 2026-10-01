package com.healthapp.emergencyservice.repository;

import com.healthapp.emergencyservice.entity.EmergencyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyEventRepository extends JpaRepository<EmergencyEvent, Long> {
    List<EmergencyEvent> findByUserIdOrderByTriggeredAtDesc(Long userId);
}
