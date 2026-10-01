package com.healthapp.userservice.exception;

public class PhoneAlreadyExistsException extends RuntimeException {
    public PhoneAlreadyExistsException(String phone) {
        super("An account with phone '" + phone + "' already exists");
    }
}
