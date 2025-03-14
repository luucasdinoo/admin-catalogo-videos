package com.dino.admin.catalogo.infrastructure.config;

import com.dino.admin.catalogo.infrastructure.config.annotations.VideoCreatedQueue;
import com.dino.admin.catalogo.infrastructure.config.properties.amqp.QueueProperties;
import com.dino.admin.catalogo.infrastructure.services.EventService;
import com.dino.admin.catalogo.infrastructure.services.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    EventService videoCreatedEventService(
            @VideoCreatedQueue final QueueProperties props,
            final RabbitOperations ops
            ){
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
