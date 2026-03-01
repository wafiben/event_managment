package org.project.event_managment.end2end;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.event_managment.controller.EventResponseDTO;
import org.project.event_managment.domain.EventEntity;
import org.project.event_managment.domain.Status;
import org.project.event_managment.infrastructure.EventJpaRepository;
import org.project.event_managment.infrastructure.EventStoreRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetEventsEndToEndTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate;

    @Autowired
    private EventStoreRepoImpl eventStoreRepoImpl;

    @Autowired
    private EventJpaRepository eventJpaRepository;


    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        eventJpaRepository.deleteAll();
    }

    @Test
    void shouldGetEvents() {
        // Given — save un événement en base
        EventEntity event = new EventEntity(
                UUID.randomUUID().toString(),
                Status.PUBLISHED,
                "2024-01-01T10:00:00",
                "UserRegisteredEvent"
        );
        eventStoreRepoImpl.saveEvent(event);

        // When — appel GET /events
        String url = "http://localhost:" + port + "/events";
        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List>() {
                }
        );

        // Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void shouldFilterByType() {
        // Given
        eventStoreRepoImpl.saveEvent(new EventEntity(UUID.randomUUID().toString(), Status.PUBLISHED, "2024-01-01T10:00:00", "UserRegisteredEvent"));
        eventStoreRepoImpl.saveEvent(new EventEntity(UUID.randomUUID().toString(), Status.PUBLISHED, "2024-01-01T11:00:00", "OrderCreatedEvent"));

        // When
        String url = "http://localhost:" + port + "/events?type=UserRegisteredEvent";
        ResponseEntity<List> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List>() {
                }
        );

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldFilterByStatus() {
        // Given
        eventStoreRepoImpl.saveEvent(new EventEntity(UUID.randomUUID().toString(), Status.PUBLISHED, "2024-01-01T10:00:00", "UserRegisteredEvent"));
        eventStoreRepoImpl.saveEvent(new EventEntity(UUID.randomUUID().toString(), Status.FAILED, "2024-01-01T11:00:00", "OrderCreatedEvent"));

        // When
        String url = "http://localhost:" + port + "/events?status=FAILED";
        ResponseEntity<List> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List>() {
                }
        );

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldFilterByDateRange() {
        // Given
        eventStoreRepoImpl.saveEvent(new EventEntity(UUID.randomUUID().toString(), Status.PUBLISHED, "2024-01-01T08:00:00", "UserRegisteredEvent"));
        eventStoreRepoImpl.saveEvent(new EventEntity(UUID.randomUUID().toString(), Status.PUBLISHED, "2024-01-01T12:00:00", "OrderCreatedEvent"));
        eventStoreRepoImpl.saveEvent(new EventEntity(UUID.randomUUID().toString(), Status.PUBLISHED, "2024-01-01T18:00:00", "OrderCreatedEvent"));

        // When
        String url = "http://localhost:" + port + "/events?from=2024-01-01T10:00:00&to=2024-01-01T15:00:00";
        ResponseEntity<List<EventResponseDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<EventResponseDTO>>() {
                }
        );

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());

        EventResponseDTO event = response.getBody().get(0);
        assertEquals("OrderCreatedEvent", event.getType());
        assertEquals("PUBLISHED", event.getStatus());
    }
}