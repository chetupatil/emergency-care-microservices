package com.healthapp.facilityservice.controller;

import com.healthapp.facilityservice.dto.FacilityResponse;
import com.healthapp.facilityservice.service.FacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/api/facilities/nearest")
    public ResponseEntity<List<FacilityResponse>> nearest(@RequestParam("lat") BigDecimal lat,
                                                            @RequestParam("lng") BigDecimal lng,
                                                            @RequestParam(value = "radiusKm", defaultValue = "10") double radiusKm) {
        return ResponseEntity.ok(facilityService.nearest(lat, lng, radiusKm));
    }
}
