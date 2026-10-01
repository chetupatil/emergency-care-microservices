package com.healthapp.contactservice.repository;

import com.healthapp.contactservice.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    List<EmergencyContact> findByUserIdOrderByPriorityOrderAsc(Long userId);

    long countByUserId(Long userId);
}
