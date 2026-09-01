package com.orderms.notification_service.kafka;

import com.orderms.notification_service.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-service-group"
    )
    public void consumerOrderPlacedEvent(OrderPlacedEvent event){

        log.info("Order Placed Event Received - Order Id : {}, user Id : {}, product Id : {}, status: {}, timestamp: {}",
                event.orderId(), event.userId(), event.productId(), event.status(), event.timeStamp());



        // Simulated notification — real email/SMS integration comes later
        log.info("Email sent to user {} for order {}", event.userId(), event.orderId());

//        throw new RuntimeException("Test DLT");

    }
}
