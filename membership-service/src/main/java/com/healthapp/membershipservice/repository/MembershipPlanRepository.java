package com.healthapp.membershipservice.repository;

import com.healthapp.membershipservice.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
    Optional<MembershipPlan> findByPlanNameIgnoreCase(String planName);
}
