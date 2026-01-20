package org.project.event_managment.service;

import org.project.event_managment.events.UserRegisteredEvent;
import org.project.event_managment.port.UserRegisteredEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class EventPublisherService implements UserRegisteredEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void publishUserRegistered(String userId, String email, String username) {
        System.out.println("✅ Publishing to notification service");

        rabbitTemplate.convertAndSend(
                "notification.exchange",           // ✅ DIFFERENT exchange
                "user.notification.sent",       // ✅ DIFFERENT routing key
                new UserRegisteredEvent(        // ✅ Send proper object, not string
                        userId,
                        email,
                        username
                )
        );
    }
}