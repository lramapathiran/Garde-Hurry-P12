package com.lavanya.api.model;

/**
 * Bean representing a Notification.
 * Notification has all attributes required to send an email notification to a specific user.
 * @author lavanya
 */
public class Notification {

    private String toFullId;
    private String toEmail;

    public Notification() {

    }

    public Notification(String toFullId, String toEmail) {
        this.toFullId = toFullId;
        this.toEmail = toEmail;
    }

    public String getToFullId() {
        return toFullId;
    }

    public void setToFullId(String toFullId) {
        this.toFullId = toFullId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
}
