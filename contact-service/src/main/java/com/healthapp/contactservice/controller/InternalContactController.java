package com.healthapp.contactservice.controller;

import com.healthapp.contactservice.config.InternalApiProperties;
import com.healthapp.contactservice.dto.ContactResponse;
import com.healthapp.contactservice.repository.EmergencyContactRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Service-to-service endpoints — not exposed through the Gateway's public routes.
 * Trusted callers (other backend services) authenticate with a shared API key
 * instead of a user JWT, since there's no end-user in the request context
 * (e.g. a Kafka consumer reacting to an event).
 */
@RestController
public class InternalContactController {

    private static final String API_KEY_HEADER = "X-Internal-Api-Key";

    private final EmergencyContactRepository repository;
    private final InternalApiProperties internalApiProperties;

    public InternalContactController(EmergencyContactRepository repository, InternalApiProperties internalApiProperties) {
        this.repository = repository;
        this.internalApiProperties = internalApiProperties;
    }

    @GetMapping("/internal/users/{userId}/contacts")
    public ResponseEntity<?> getContactsForUser(@PathVariable Long userId, HttpServletRequest request) {
        String providedKey = request.getHeader(API_KEY_HEADER);
        if (providedKey == null || !providedKey.equals(internalApiProperties.key())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing internal API key");
        }

        List<ContactResponse> contacts = repository.findByUserIdOrderByPriorityOrderAsc(userId).stream()
                .map(ContactResponse::from)
                .toList();

        return ResponseEntity.ok(contacts);
    }
}
