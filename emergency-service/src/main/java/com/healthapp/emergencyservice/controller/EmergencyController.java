package com.healthapp.emergencyservice.controller;

import com.healthapp.emergencyservice.dto.EmergencyEventResponse;
import com.healthapp.emergencyservice.dto.TriggerEmergencyRequest;
import com.healthapp.emergencyservice.service.EmergencyEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {

    private final EmergencyEventService service;

    public EmergencyController(EmergencyEventService service) {
        this.service = service;
    }

    @PostMapping("/trigger")
    public ResponseEntity<EmergencyEventResponse> trigger(@AuthenticationPrincipal Long userId,
                                                            @Valid @RequestBody TriggerEmergencyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.trigger(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyEventResponse> getById(@AuthenticationPrincipal Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(userId, id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<EmergencyEventResponse>> myHistory(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(service.myHistory(userId));
    }

    @PostMapping("/{id}/resolve")
    public ResponseEntity<EmergencyEventResponse> resolve(@AuthenticationPrincipal Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(service.resolve(userId, id));
    }
}
