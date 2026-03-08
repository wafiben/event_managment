package org.project.event_managment.getevents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.event_managment.domain.EventEntity;
import org.project.event_managment.domain.Status;
import org.project.event_managment.handlers.getevents.GetEventsHandler;
import org.project.event_managment.handlers.getevents.GetEventsQuery;
import org.project.event_managment.infrastructure.EventStoreRepoInMemory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetEventsUnitTests {

    private GetEventsHandler getEventsHandler;
    private EventStoreRepoInMemory eventStoreRepoInMemory;

    @BeforeEach
    void setUp() {
        this.eventStoreRepoInMemory = new EventStoreRepoInMemory();
        this.getEventsHandler = new GetEventsHandler(eventStoreRepoInMemory);
    }

    @Test
    void shouldReturnEmptyListWhenNoEventsFound() {
        // Given
        GetEventsQuery query = new GetEventsQuery(null, null, null, null);
        // When
        List<EventEntity> result = getEventsHandler.getEvents(query);
        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFilterByStatus() {
        // Given
        eventStoreRepoInMemory.saveEvent(new EventEntity("1", Status.PUBLISHED, "2024-01-01T10:00:00", "UserRegisteredEvent"));
        eventStoreRepoInMemory.saveEvent(new EventEntity("2", Status.FAILED,    "2024-01-01T11:00:00", "OrderCreatedEvent"));

        GetEventsQuery query = new GetEventsQuery(null, "PUBLISHED", null, null);
        // When
        List<EventEntity> result = getEventsHandler.getEvents(query);
        // Then
        assertEquals(1, result.size());
        assertEquals(Status.PUBLISHED, result.get(0).getStatus());
    }

    @Test
    void shouldFilterByType() {
        // Given
        eventStoreRepoInMemory.saveEvent(new EventEntity("1", Status.PUBLISHED, "2024-01-01T10:00:00", "UserRegisteredEvent"));
        eventStoreRepoInMemory.saveEvent(new EventEntity("2", Status.PUBLISHED, "2024-01-01T11:00:00", "OrderCreatedEvent"));

        GetEventsQuery query = new GetEventsQuery("UserRegisteredEvent", null, null, null);
        // When
        List<EventEntity> result = getEventsHandler.getEvents(query);
        // Then
        assertEquals(1, result.size());
        assertEquals("UserRegisteredEvent", result.get(0).getType());
    }

    @Test
    void shouldFilterByDateRange() {
        // Given
        eventStoreRepoInMemory.saveEvent(new EventEntity("1", Status.PUBLISHED, "2024-01-01T08:00:00", "UserRegisteredEvent"));
        eventStoreRepoInMemory.saveEvent(new EventEntity("2", Status.PUBLISHED, "2024-01-01T12:00:00", "OrderCreatedEvent"));
        eventStoreRepoInMemory.saveEvent(new EventEntity("3", Status.PUBLISHED, "2024-01-01T18:00:00", "OrderCreatedEvent"));

        GetEventsQuery query = new GetEventsQuery(
                null, null,
                LocalDateTime.parse("2024-01-01T10:00:00"),
                LocalDateTime.parse("2024-01-01T15:00:00")
        );
        // When
        List<EventEntity> result = getEventsHandler.getEvents(query);
        // Then
        assertEquals(1, result.size());
        assertEquals("2", result.get(0).getId());
    }
}