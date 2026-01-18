package org.project.event_managment.events;

public class UserRegisteredEvent {
    private String userId;
    private String email;
    private String username;

    public UserRegisteredEvent() {
    }

    public UserRegisteredEvent(String userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    // ✅ Setters (required by Jackson)
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}