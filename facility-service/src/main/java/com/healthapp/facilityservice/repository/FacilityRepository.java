package com.healthapp.facilityservice.repository;

import com.healthapp.facilityservice.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByAvailableTrue();
}
