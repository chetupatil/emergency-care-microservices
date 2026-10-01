package com.healthapp.contactservice.dto;

public record UpdateContactRequest(
        String contactName,
        String relationship,
        String phone,
        Integer priorityOrder
) {
}
