package com.healthapp.contactservice.controller;

import com.healthapp.contactservice.dto.ContactResponse;
import com.healthapp.contactservice.dto.CreateContactRequest;
import com.healthapp.contactservice.dto.UpdateContactRequest;
import com.healthapp.contactservice.service.EmergencyContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmergencyContactController {

    private final EmergencyContactService contactService;

    public EmergencyContactController(EmergencyContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/api/users/{userId}/contacts")
    public ResponseEntity<ContactResponse> create(@AuthenticationPrincipal Long authenticatedUserId,
                                                    @PathVariable Long userId,
                                                    @Valid @RequestBody CreateContactRequest request) {
        ContactResponse created = contactService.create(authenticatedUserId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/api/users/{userId}/contacts")
    public ResponseEntity<List<ContactResponse>> list(@AuthenticationPrincipal Long authenticatedUserId,
                                                        @PathVariable Long userId) {
        return ResponseEntity.ok(contactService.listForUser(authenticatedUserId, userId));
    }

    @PutMapping("/api/contacts/{id}")
    public ResponseEntity<ContactResponse> update(@AuthenticationPrincipal Long authenticatedUserId,
                                                    @PathVariable Long id,
                                                    @Valid @RequestBody UpdateContactRequest request) {
        return ResponseEntity.ok(contactService.update(authenticatedUserId, id, request));
    }

    @DeleteMapping("/api/contacts/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long authenticatedUserId,
                                        @PathVariable Long id) {
        contactService.delete(authenticatedUserId, id);
        return ResponseEntity.noContent().build();
    }
}
