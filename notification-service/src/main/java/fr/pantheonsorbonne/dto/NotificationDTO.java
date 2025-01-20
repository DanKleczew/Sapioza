package fr.pantheonsorbonne.dto;

import java.sql.Date;

public record NotificationDTO (
        Long notifiedUserId, // ID of the user receiving the notification
        Long paperId, // ID of the paper associated with the notification
        Long authorId,
        String paperTitle,
        Date dateNotification )
{}
