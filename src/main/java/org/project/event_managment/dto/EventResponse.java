package org.project.event_managment.dto;

import java.time.Instant;
import java.util.Objects;

class EventResponse {
    private boolean success;
    private String message;
    private String eventId;
    private Instant publishedAt;

    // No-args constructor
    public EventResponse() {
    }

    public EventResponse(boolean success, String message, String eventId, Instant publishedAt) {
        this.success = success;
        this.message = message;
        this.eventId = eventId;
        this.publishedAt = publishedAt;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResponse that = (EventResponse) o;
        return success == that.success &&
                Objects.equals(message, that.message) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(publishedAt, that.publishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, eventId, publishedAt);
    }
}