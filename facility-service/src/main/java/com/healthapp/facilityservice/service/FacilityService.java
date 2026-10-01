package com.healthapp.facilityservice.service;

import com.healthapp.facilityservice.dto.FacilityResponse;
import com.healthapp.facilityservice.entity.Facility;
import com.healthapp.facilityservice.repository.FacilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {

    private final FacilityRepository repository;
    private final GeoDistanceCalculator distanceCalculator;

    public FacilityService(FacilityRepository repository, GeoDistanceCalculator distanceCalculator) {
        this.repository = repository;
        this.distanceCalculator = distanceCalculator;
    }

    @Transactional(readOnly = true)
    public List<FacilityResponse> nearest(BigDecimal lat, BigDecimal lng, double radiusKm) {
        return repository.findByAvailableTrue().stream()
                .map(f -> new Object[]{f, distanceCalculator.distanceKm(lat, lng, f.getLatitude(), f.getLongitude())})
                .filter(pair -> (Double) pair[1] <= radiusKm)
                .sorted(Comparator.comparingDouble(pair -> (Double) pair[1]))
                .map(pair -> FacilityResponse.from((Facility) pair[0], (Double) pair[1]))
                .toList();
    }

    /** Used by the Kafka consumer — finds the single closest available facility, regardless of radius. */
    @Transactional(readOnly = true)
    public Optional<Facility> findNearestAvailable(BigDecimal lat, BigDecimal lng) {
        return repository.findByAvailableTrue().stream()
                .min(Comparator.comparingDouble(f -> distanceCalculator.distanceKm(lat, lng, f.getLatitude(), f.getLongitude())));
    }
}
