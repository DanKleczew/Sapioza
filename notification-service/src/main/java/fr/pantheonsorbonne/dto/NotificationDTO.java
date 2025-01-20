package fr.pantheonsorbonne.dto;

import java.sql.Date;

public record NotificationDTO (
        Long notifiedUserId,
        Long paperId,
        Long authorId,
        String paperTitle,
        Date dateNotification )
{}
