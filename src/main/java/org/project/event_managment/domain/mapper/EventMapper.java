package org.project.event_managment.domain.mapper;

import org.project.event_managment.controller.EventResponseDTO;
import org.project.event_managment.domain.EventEntity;
import org.project.event_managment.domain.Status;
import org.project.event_managment.infrastructure.EventJpaEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventMapper {
    public EventResponseDTO toDTO(EventEntity domain) {
        if (domain == null) return null;
        return new EventResponseDTO(
                domain.getId(),
                domain.getType(),
                domain.getStatus() != null ? domain.getStatus().name() : null,
                domain.getCreatedAt()
        );
    }

    public EventEntity toDomain(EventJpaEntity jpa) {
        if (jpa == null) return null;
        return new EventEntity(
                jpa.getId().toString(),
                Status.valueOf(jpa.getStatus()),
                jpa.getCreatedAt().toString(),
                jpa.getType()
        );
    }

    public EventJpaEntity toJpa(EventEntity domain) {
        if (domain == null) return null;
        return new EventJpaEntity(
                null,
                domain.getType(),
                domain.getStatus() != null ? domain.getStatus().name() : null,
                domain.getCreatedAt() != null ? LocalDateTime.parse(domain.getCreatedAt()) : null
        );
    }
}