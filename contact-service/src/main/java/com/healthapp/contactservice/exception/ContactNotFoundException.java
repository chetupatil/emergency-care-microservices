package com.healthapp.contactservice.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(Long id) {
        super("No emergency contact found with id " + id);
    }
}
