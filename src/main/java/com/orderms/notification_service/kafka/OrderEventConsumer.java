package com.orderms.notification_service.kafka;

import com.orderms.notification_service.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@Slf4j
public class OrderEventConsumer {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-service-group"
    )


    public void consumerOrderPlacedEvent(OrderPlacedEvent event,
                                         ConsumerRecord<String, OrderPlacedEvent> record) {

        log.info(">>> NOTIFICATION CONSUMER RECEIVED RECORD: topic={}, partition={}, offset={}",
                record.topic(),
                record.partition(),
                record.offset());

        String correlationId =null;
        var header = record.headers().lastHeader(CORRELATION_ID_HEADER);

        if(header != null){
            correlationId = new String(header.value(), StandardCharsets.UTF_8);
        }

        if(correlationId == null || correlationId.isBlank()) {
            correlationId = "missing-correlation-id";
        }

        try {
            MDC.put("correlationId", correlationId);

            log.info(">>> NOTIFICATION RECEIVED CORRELATION ID: {}", correlationId);

            log.info("Order Placed Event Received - Order Id : {}, user Id : {}, product Id : {}, status: {}, timestamp: {}",
                    event.orderId(), event.userId(), event.productId(), event.status(), event.timeStamp());


            // Simulated notification — real email/SMS integration comes later
            log.info("Email sent to user {} for order {}", event.userId(), event.orderId());

//        throw new RuntimeException("Test DLT");
        }finally {
            MDC.remove("correlationId");
        }

    }
}
