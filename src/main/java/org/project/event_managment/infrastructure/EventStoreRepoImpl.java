package org.project.event_managment.infrastructure;

import org.project.event_managment.domain.EventEntity;
import org.project.event_managment.domain.mapper.EventMapper;
import org.project.event_managment.handlers.getevents.GetEventsQuery;
import org.project.event_managment.port.out.EventStoreRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventStoreRepoImpl implements EventStoreRepo {

    private final EventJpaRepository eventJpaRepository;
    private final EventMapper eventJpaMapper;

    public EventStoreRepoImpl(EventJpaRepository eventJpaRepository, EventMapper eventJpaMapper) {
        this.eventJpaRepository = eventJpaRepository;
        this.eventJpaMapper = eventJpaMapper;
    }

    @Override
    public List<EventEntity> listEvents(GetEventsQuery query) {
        return eventJpaRepository.findByFilters(
                        query.type(),
                        query.status(),
                        query.from(),
                        query.to()
                ).stream()
                .map(eventJpaMapper::toDomain)
                .toList();
    }


    public void saveEvent(EventEntity event) {
        eventJpaRepository.save(eventJpaMapper.toJpa(event));
    }
}