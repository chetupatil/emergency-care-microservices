package com.healthapp.membershipservice.exception;

public class AccessDeniedForMembershipException extends RuntimeException {
    public AccessDeniedForMembershipException() {
        super("You do not have permission to access this membership");
    }
}
