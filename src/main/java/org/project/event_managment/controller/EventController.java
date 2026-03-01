package org.project.event_managment.controller;

import org.project.event_managment.domain.mapper.EventMapper;
import org.project.event_managment.handlers.getevents.GetEventsHandler;
import org.project.event_managment.handlers.getevents.GetEventsQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private GetEventsHandler getEventsHandler;
    private EventMapper eventMapper;

    public EventController(GetEventsHandler getEventsHandler, EventMapper eventMapper) {
        this.getEventsHandler = getEventsHandler;
        this.eventMapper = eventMapper;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponseDTO> listEvents(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        GetEventsQuery query = new GetEventsQuery(type, status, from, to);
        return getEventsHandler.getEvents(query).stream().map(eventMapper::toDTO).toList();
    }
}
