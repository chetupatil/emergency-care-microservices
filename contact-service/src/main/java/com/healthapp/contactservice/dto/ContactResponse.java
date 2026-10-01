package com.healthapp.contactservice.dto;

import com.healthapp.contactservice.entity.EmergencyContact;

public record ContactResponse(
        Long id,
        Long userId,
        String contactName,
        String relationship,
        String phone,
        Integer priorityOrder
) {
    public static ContactResponse from(EmergencyContact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getUserId(),
                contact.getContactName(),
                contact.getRelationship(),
                contact.getPhone(),
                contact.getPriorityOrder()
        );
    }
}
