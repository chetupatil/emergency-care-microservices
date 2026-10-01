package com.healthapp.notificationservice.service;

import com.healthapp.notificationservice.client.ContactServiceClient;
import com.healthapp.notificationservice.dto.ContactDto;
import com.healthapp.notificationservice.entity.NotificationLog;
import com.healthapp.notificationservice.repository.NotificationLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationDispatchService {

    private static final Logger log = LoggerFactory.getLogger(NotificationDispatchService.class);

    private final ContactServiceClient contactServiceClient;
    private final NotificationLogRepository repository;

    public NotificationDispatchService(ContactServiceClient contactServiceClient, NotificationLogRepository repository) {
        this.contactServiceClient = contactServiceClient;
        this.repository = repository;
    }

    @Transactional
    public void notifyFamilyOfEmergency(Long emergencyEventId, Long userId) {
        List<ContactDto> contacts = contactServiceClient.getContactsForUser(userId);

        if (contacts.isEmpty()) {
            log.warn("No emergency contacts found for user {} — nobody to notify for emergency {}", userId, emergencyEventId);
            return;
        }

        for (ContactDto contact : contacts) {
            // Twilio (or similar) integration goes here — stubbed for local dev.
            boolean sent = sendStub(contact);

            NotificationLog logEntry = NotificationLog.builder()
                    .emergencyEventId(emergencyEventId)
                    .recipientType("FAMILY")
                    .recipientContact(contact.phone())
                    .channel("SMS")
                    .status(sent ? "SENT" : "FAILED")
                    .build();

            repository.save(logEntry);
            log.info("Notified {} ({}) for emergency {} — status {}",
                    contact.contactName(), contact.phone(), emergencyEventId, logEntry.getStatus());
        }
    }

    private boolean sendStub(ContactDto contact) {
        // Replace with a real Twilio/SMS/call API call. Always "succeeds" in local dev.
        return true;
    }
}
