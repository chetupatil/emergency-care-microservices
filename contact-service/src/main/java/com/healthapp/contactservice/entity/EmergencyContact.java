package com.healthapp.contactservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;   // references user-service's users.id — no cross-service FK

    @Column(name = "contact_name", nullable = false, length = 150)
    private String contactName;

    @Column(length = 50)
    private String relationship;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "priority_order")
    private Integer priorityOrder;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.priorityOrder == null) {
            this.priorityOrder = 1;
        }
    }
}
