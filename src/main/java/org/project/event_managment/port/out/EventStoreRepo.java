package org.project.event_managment.port.out;

import org.project.event_managment.domain.EventEntity;
import org.project.event_managment.handlers.getevents.GetEventsQuery;

import java.util.List;

public interface EventStoreRepo {
    List<EventEntity> listEvents(GetEventsQuery query);
}
