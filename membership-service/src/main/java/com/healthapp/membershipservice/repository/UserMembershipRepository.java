package com.healthapp.membershipservice.repository;

import com.healthapp.membershipservice.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
    Optional<UserMembership> findTopByUserIdOrderByIdDesc(Long userId);
}
