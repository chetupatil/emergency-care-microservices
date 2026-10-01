package com.healthapp.membershipservice.exception;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(String planName) {
        super("No membership plan named '" + planName + "'");
    }
}
