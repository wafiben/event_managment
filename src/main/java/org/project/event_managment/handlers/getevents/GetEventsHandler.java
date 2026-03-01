package org.project.event_managment.handlers.getevents;

import org.project.event_managment.domain.EventEntity;
import org.project.event_managment.port.out.EventStoreRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetEventsHandler {
    private final EventStoreRepo eventStoreRepo;

    public GetEventsHandler(EventStoreRepo eventStoreRepo) {
        this.eventStoreRepo = eventStoreRepo;
    }

    public List<EventEntity> getEvents(GetEventsQuery query) {
        return eventStoreRepo.listEvents(query);
    }
}
