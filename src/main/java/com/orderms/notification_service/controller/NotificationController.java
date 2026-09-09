package com.orderms.notification_service.controller;

import com.orderms.notification_service.dto.NotificationRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@Slf4j
public class NotificationController {

    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationRequestDto notificationRequestDto) {

        log.info("Notification received - userId:{},type :{},message:{}",
                notificationRequestDto.getUserId(),
                notificationRequestDto.getMessage(),
                notificationRequestDto.getType()
        );

        return ResponseEntity.ok("NOTIFICATION SENT");
    }
}
