package com.healthapp.notificationservice.dto;

import com.healthapp.notificationservice.entity.NotificationLog;

import java.time.LocalDateTime;

public record NotificationLogResponse(
        Long id,
        Long emergencyEventId,
        String recipientType,
        String recipientContact,
        String channel,
        String status,
        LocalDateTime sentAt
) {
    public static NotificationLogResponse from(NotificationLog log) {
        return new NotificationLogResponse(log.getId(), log.getEmergencyEventId(), log.getRecipientType(),
                log.getRecipientContact(), log.getChannel(), log.getStatus(), log.getSentAt());
    }
}
