package org.project.event_managment.infrastructure;

import org.project.event_managment.domain.EventEntity;
import org.project.event_managment.handlers.getevents.GetEventsQuery;
import org.project.event_managment.port.out.EventStoreRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventStoreRepoInMemory implements EventStoreRepo {

    private List<EventEntity> eventEntities = new ArrayList<EventEntity>();

    public List<EventEntity> listEvents(GetEventsQuery query) {
        return eventEntities.stream()
                .filter(event -> query.type() == null || query.type().equals(event.getType()))
                .filter(event -> query.status() == null || query.status().equals(event.getStatus().name()))
                .filter(event -> query.from() == null || !LocalDateTime.parse(event.getCreatedAt()).isBefore(query.from()))
                .filter(event -> query.to() == null || !LocalDateTime.parse(event.getCreatedAt()).isAfter(query.to()))
                .toList();
    }

    public void saveEvent(EventEntity event) {
        eventEntities.add(event);
    }
}
