package com.healthapp.notificationservice.repository;

import com.healthapp.notificationservice.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    List<NotificationLog> findByEmergencyEventId(Long emergencyEventId);
}
