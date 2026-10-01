package com.healthapp.notificationservice.controller;

import com.healthapp.notificationservice.dto.NotificationLogResponse;
import com.healthapp.notificationservice.repository.NotificationLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    private final NotificationLogRepository repository;

    public NotificationController(NotificationLogRepository repository) {
        this.repository = repository;
    }

    /** Lets the frontend show "who's been notified" for a given emergency. */
    @GetMapping("/api/notifications/emergency/{emergencyEventId}")
    public ResponseEntity<List<NotificationLogResponse>> forEmergency(@PathVariable Long emergencyEventId) {
        List<NotificationLogResponse> logs = repository.findByEmergencyEventId(emergencyEventId).stream()
                .map(NotificationLogResponse::from)
                .toList();
        return ResponseEntity.ok(logs);
    }
}
