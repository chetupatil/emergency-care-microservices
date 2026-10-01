package com.healthapp.membershipservice.controller;

import com.healthapp.membershipservice.dto.MembershipResponse;
import com.healthapp.membershipservice.dto.SubscribeRequest;
import com.healthapp.membershipservice.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MembershipResponse> getForUser(@AuthenticationPrincipal Long authenticatedUserId,
                                                           @PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.getForUser(authenticatedUserId, userId));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<MembershipResponse> subscribe(@AuthenticationPrincipal Long authenticatedUserId,
                                                          @Valid @RequestBody SubscribeRequest request) {
        return ResponseEntity.ok(membershipService.subscribe(authenticatedUserId, request.planName()));
    }
}
