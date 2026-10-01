package com.healthapp.contactservice.service;

import com.healthapp.contactservice.dto.ContactResponse;
import com.healthapp.contactservice.dto.CreateContactRequest;
import com.healthapp.contactservice.dto.UpdateContactRequest;
import com.healthapp.contactservice.entity.EmergencyContact;
import com.healthapp.contactservice.exception.AccessDeniedForContactException;
import com.healthapp.contactservice.exception.ContactNotFoundException;
import com.healthapp.contactservice.repository.EmergencyContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmergencyContactService {

    private final EmergencyContactRepository repository;

    public EmergencyContactService(EmergencyContactRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ContactResponse create(Long authenticatedUserId, Long pathUserId, CreateContactRequest request) {
        requireSelf(authenticatedUserId, pathUserId);

        EmergencyContact contact = EmergencyContact.builder()
                .userId(pathUserId)
                .contactName(request.contactName())
                .relationship(request.relationship())
                .phone(request.phone())
                .priorityOrder(request.priorityOrder())
                .build();

        return ContactResponse.from(repository.save(contact));
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> listForUser(Long authenticatedUserId, Long pathUserId) {
        requireSelf(authenticatedUserId, pathUserId);

        return repository.findByUserIdOrderByPriorityOrderAsc(pathUserId).stream()
                .map(ContactResponse::from)
                .toList();
    }

    @Transactional
    public ContactResponse update(Long authenticatedUserId, Long contactId, UpdateContactRequest request) {
        EmergencyContact contact = repository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));

        requireSelf(authenticatedUserId, contact.getUserId());

        if (request.contactName() != null) contact.setContactName(request.contactName());
        if (request.relationship() != null) contact.setRelationship(request.relationship());
        if (request.phone() != null) contact.setPhone(request.phone());
        if (request.priorityOrder() != null) contact.setPriorityOrder(request.priorityOrder());

        return ContactResponse.from(repository.save(contact));
    }

    @Transactional
    public void delete(Long authenticatedUserId, Long contactId) {
        EmergencyContact contact = repository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));

        requireSelf(authenticatedUserId, contact.getUserId());

        repository.delete(contact);
    }

    private void requireSelf(Long authenticatedUserId, Long resourceOwnerUserId) {
        if (!authenticatedUserId.equals(resourceOwnerUserId)) {
            throw new AccessDeniedForContactException();
        }
    }
}
