package com.healthapp.contactservice.exception;

public class AccessDeniedForContactException extends RuntimeException {
    public AccessDeniedForContactException() {
        super("You do not have permission to access this contact");
    }
}
