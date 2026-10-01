package com.healthapp.emergencyservice.exception;

public class AccessDeniedForEmergencyException extends RuntimeException {
    public AccessDeniedForEmergencyException() {
        super("You do not have permission to access this emergency event");
    }
}
