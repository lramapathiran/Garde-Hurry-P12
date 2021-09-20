package com.lavanya.emailBatch.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Bean representing a data transfer Object NotificationDto.
 * NotificationDto has all attributes required to send alert notification to user of interest
 * @author lavanya
 */
public class NotificationDto {

    private String fromFullId;
    private String fromEmail;
    private String  toFullId;
    private String toEmail;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public NotificationDto() {

    }

    public NotificationDto(String toFullId, String toEmail) {
        this.toFullId = toFullId;
        this.toEmail = toEmail;
    }

    public NotificationDto(String fromFullId, String fromEmail, String toFullId, String toEmail) {
        this.fromFullId = fromFullId;
        this.fromEmail = fromEmail;
        this.toFullId = toFullId;
        this.toEmail = toEmail;
    }

    public NotificationDto(String fromFullId, String fromEmail, String toFullId, String toEmail, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        this.fromFullId = fromFullId;
        this.fromEmail = fromEmail;
        this.toFullId = toFullId;
        this.toEmail = toEmail;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }
}
