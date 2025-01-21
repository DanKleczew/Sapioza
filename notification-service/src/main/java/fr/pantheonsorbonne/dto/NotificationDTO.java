package fr.pantheonsorbonne.dto;

import java.sql.Date;

public record NotificationDTO (
        Long notifiedUserId,
        Long paperId,
        String authorLastName,
        String authorFirstName,
        String paperTitle,
        Date dateNotification )
{}
