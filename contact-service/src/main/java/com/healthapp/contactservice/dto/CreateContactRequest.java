package com.healthapp.contactservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateContactRequest(
        @NotBlank(message = "Contact name is required")
        String contactName,

        String relationship,

        @NotBlank(message = "Phone is required")
        String phone,

        Integer priorityOrder
) {
}
