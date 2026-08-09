package com.orderms.notification_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {

    @NotNull(message = "UserId is required")
    private Long userId;

    @NotBlank(message = "Message is required")
    private String message;

    @NotBlank(message = "Notification type is required")
    private String type;



}
