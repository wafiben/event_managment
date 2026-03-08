package org.project.event_managment.domain;

public class EventEntity {
    private String id;
    private Status status;
    private String createdAt;
    private String type;


    public EventEntity(String id, Status status, String createdAt, String type) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}