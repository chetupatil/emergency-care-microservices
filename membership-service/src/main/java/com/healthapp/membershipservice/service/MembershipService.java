package com.healthapp.membershipservice.service;

import com.healthapp.membershipservice.dto.MembershipResponse;
import com.healthapp.membershipservice.entity.MembershipPlan;
import com.healthapp.membershipservice.entity.MembershipStatus;
import com.healthapp.membershipservice.entity.UserMembership;
import com.healthapp.membershipservice.exception.AccessDeniedForMembershipException;
import com.healthapp.membershipservice.exception.MembershipNotFoundException;
import com.healthapp.membershipservice.exception.PlanNotFoundException;
import com.healthapp.membershipservice.repository.MembershipPlanRepository;
import com.healthapp.membershipservice.repository.UserMembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembershipService {

    private final MembershipPlanRepository planRepository;
    private final UserMembershipRepository membershipRepository;

    public MembershipService(MembershipPlanRepository planRepository, UserMembershipRepository membershipRepository) {
        this.planRepository = planRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional(readOnly = true)
    public MembershipResponse getForUser(Long authenticatedUserId, Long pathUserId) {
        requireSelf(authenticatedUserId, pathUserId);
        UserMembership membership = membershipRepository.findTopByUserIdOrderByIdDesc(pathUserId)
                .orElseThrow(() -> new MembershipNotFoundException(pathUserId));
        return MembershipResponse.from(membership);
    }

    @Transactional
    public MembershipResponse subscribe(Long authenticatedUserId, String planName) {
        MembershipPlan plan = planRepository.findByPlanNameIgnoreCase(planName)
                .orElseThrow(() -> new PlanNotFoundException(planName));

        UserMembership membership = UserMembership.builder()
                .userId(authenticatedUserId)
                .plan(plan)
                .status(MembershipStatus.ACTIVE)
                .build();

        return MembershipResponse.from(membershipRepository.save(membership));
    }

    private void requireSelf(Long authenticatedUserId, Long pathUserId) {
        if (!authenticatedUserId.equals(pathUserId)) {
            throw new AccessDeniedForMembershipException();
        }
    }
}
