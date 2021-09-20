package com.lavanya.api.model;

/**
 * Bean representing a Notification.
 * Notification has all attributes required to send an email notification to a specific user.
 * @author lavanya
 */
public class Notification {

    private String fromFullId;
    private String fromEmail;

    public Notification() {

    }

    public Notification(String fromFullId, String fromEmail) {
        this.fromFullId = fromFullId;
        this.fromEmail = fromEmail;
    }

    public String getFromFullId() {
        return fromFullId;
    }

    public void setFromFullId(String fromFullId) {
        this.fromFullId = fromFullId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
}
