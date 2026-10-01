package com.healthapp.membershipservice.exception;

public class MembershipNotFoundException extends RuntimeException {
    public MembershipNotFoundException(Long userId) {
        super("No membership found for user " + userId);
    }
}
