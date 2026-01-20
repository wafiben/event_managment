package org.project.event_managment.usecases;

import org.project.event_managment.config.RabbitMQConfig;
import org.project.event_managment.events.UserRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RabbitEventListener {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        System.out.println("✅ RABBITMQ RECEIVED: " + event.getEmail());
        System.out.println("✅ RABBITMQ RECEIVED: " + event.getUserId());
        System.out.println("✅ RABBITMQ RECEIVED: " + event.getUsername());
        eventPublisher.publishEvent(event);
    }
}
