package com.healthapp.locationservice.controller;

import com.healthapp.locationservice.dto.LocationPingResponse;
import com.healthapp.locationservice.dto.PingRequest;
import com.healthapp.locationservice.service.LocationPingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationPingService service;

    public LocationController(LocationPingService service) {
        this.service = service;
    }

    @PostMapping("/{emergencyId}/ping")
    public ResponseEntity<LocationPingResponse> ping(@PathVariable Long emergencyId,
                                                       @Valid @RequestBody PingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.recordPing(emergencyId, request));
    }

    @GetMapping("/{emergencyId}/latest")
    public ResponseEntity<LocationPingResponse> latest(@PathVariable Long emergencyId) {
        return service.latest(emergencyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{emergencyId}/history")
    public ResponseEntity<List<LocationPingResponse>> history(@PathVariable Long emergencyId) {
        return ResponseEntity.ok(service.history(emergencyId));
    }
}
