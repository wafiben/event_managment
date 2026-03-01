package org.project.event_managment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventJpaRepository extends JpaRepository<EventJpaEntity, UUID> {

    @Query("""
            SELECT e FROM EventJpaEntity e
            WHERE (:type IS NULL OR e.type = :type)
            AND (:status IS NULL OR e.status = :status)
            AND (CAST(:from AS java.time.LocalDateTime) IS NULL OR e.createdAt >= :from)
            AND (CAST(:to AS java.time.LocalDateTime) IS NULL OR e.createdAt <= :to)
            """)
    List<EventJpaEntity> findByFilters(
            @Param("type") String type,
            @Param("status") String status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}