package org.project.event_managment.handlers.getevents;

import java.time.LocalDateTime;

public record GetEventsQuery(
        String type,
        String status,
        LocalDateTime from,
        LocalDateTime to
) {
}