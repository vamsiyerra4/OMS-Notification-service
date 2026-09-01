package com.orderms.notification_service.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaCosumerConfig {


    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(
            KafkaTemplate<Object,Object> kafkaTemplate
    ){

        return new DeadLetterPublishingRecoverer(
                kafkaTemplate,(record,exception) ->
                new TopicPartition(
                        record.topic() + ".DLT",
                        record.partition()
                )
        );
    }

    @Bean
    public DefaultErrorHandler kafkaErrorHandler(
           DeadLetterPublishingRecoverer deadLetterPublishingRecoverer
    ){

//        ExponentialBackOff backOff = new ExponentialBackOff(
//                1000L,2.0
//        );
//        backOff.setMaxElapsedTime(10000L);

        FixedBackOff backOff = new FixedBackOff(1000L,3L);

        return new DefaultErrorHandler(deadLetterPublishingRecoverer, backOff);
    }
}
