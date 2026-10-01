package com.healthapp.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emergency_event_id", nullable = false)
    private Long emergencyEventId;

    @Column(name = "recipient_type", length = 20)
    private String recipientType; // FAMILY, FACILITY

    @Column(name = "recipient_contact", length = 150)
    private String recipientContact;

    @Column(length = 20)
    private String channel; // SMS, CALL, PUSH

    @Column(length = 20)
    private String status; // SENT, FAILED, DELIVERED

    @Column(name = "sent_at", updatable = false)
    private LocalDateTime sentAt;

    @PrePersist
    void onCreate() {
        this.sentAt = LocalDateTime.now();
    }
}
