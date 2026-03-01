package org.project.event_managment.controller;

public class EventResponseDTO {
    private String id;
    private String type;
    private String status;
    private String createdAt;

    public EventResponseDTO() {
    }

    public EventResponseDTO(String id, String type, String status, String createdAt) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}