package com.healthapp.notificationservice.client;

import com.healthapp.notificationservice.config.InternalApiProperties;
import com.healthapp.notificationservice.dto.ContactDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ContactServiceClient {

    private static final Logger log = LoggerFactory.getLogger(ContactServiceClient.class);
    private static final String API_KEY_HEADER = "X-Internal-Api-Key";

    private final RestTemplate restTemplate;
    private final InternalApiProperties internalApiProperties;

    public ContactServiceClient(RestTemplate restTemplate, InternalApiProperties internalApiProperties) {
        this.restTemplate = restTemplate;
        this.internalApiProperties = internalApiProperties;
    }

    public List<ContactDto> getContactsForUser(Long userId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(API_KEY_HEADER, internalApiProperties.key());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // "contact-service" is resolved via Eureka by the @LoadBalanced RestTemplate.
            ContactDto[] contacts = restTemplate.exchange(
                    "http://contact-service/internal/users/{userId}/contacts",
                    HttpMethod.GET, entity, ContactDto[].class, userId
            ).getBody();

            return contacts == null ? List.of() : List.of(contacts);
        } catch (Exception ex) {
            log.error("Failed to fetch contacts for user {}: {}", userId, ex.getMessage());
            return List.of();
        }
    }
}
