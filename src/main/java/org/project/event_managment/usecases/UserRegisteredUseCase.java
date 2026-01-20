package org.project.event_managment.usecases;

import org.project.event_managment.events.UserRegisteredEvent;
import org.project.event_managment.port.UserRegisteredEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class UserRegisteredUseCase {
    private final UserRegisteredEventPublisher eventPublisher;

    public UserRegisteredUseCase(UserRegisteredEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        // Call the publisher interface → decoupled from RabbitMQ
        System.out.println("eeeeeeeeeeeeeeee");
        eventPublisher.publishUserRegistered(
                event.getUserId(),
                event.getEmail(),
                event.getUsername()
        );
    }
}