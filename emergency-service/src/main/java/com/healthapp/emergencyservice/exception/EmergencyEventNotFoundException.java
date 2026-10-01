package com.healthapp.emergencyservice.exception;

public class EmergencyEventNotFoundException extends RuntimeException {
    public EmergencyEventNotFoundException(Long id) {
        super("No emergency event found with id " + id);
    }
}
