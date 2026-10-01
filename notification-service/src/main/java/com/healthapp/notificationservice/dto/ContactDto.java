package com.healthapp.notificationservice.dto;

/** Mirrors contact-service's ContactResponse — the shape returned by its internal endpoint. */
public record ContactDto(
        Long id,
        Long userId,
        String contactName,
        String relationship,
        String phone,
        Integer priorityOrder
) {
}
