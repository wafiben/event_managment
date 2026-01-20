package org.project.event_managment.port;

@FunctionalInterface
public interface UserRegisteredEventPublisher {
    void publishUserRegistered(String userId, String email, String username);
}