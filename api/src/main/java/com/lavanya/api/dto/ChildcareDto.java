package com.lavanya.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ChildcareDto {

    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private LocalTime timeStart;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private LocalTime timeEnd;
    private String description;
    private Boolean isComplete;
    private UserDto userDtoInNeed;
    private UserDto userDtoWatching;
    private Integer numberOfChildren;
    private Boolean isValidated;
    private List<ChildrenDto> childrenToWatch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public UserDto getUserDtoInNeed() {
        return userDtoInNeed;
    }

    public void setUserDtoInNeed(UserDto userDtoInNeed) {
        this.userDtoInNeed = userDtoInNeed;
    }

    public UserDto getUserDtoWatching() {
        return userDtoWatching;
    }

    public void setUserDtoWatching(UserDto userDtoWatching) {
        this.userDtoWatching = userDtoWatching;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
    }

    public List<ChildrenDto> getChildrenToWatch() {
        return childrenToWatch;
    }

    public void setChildrenToWatch(List<ChildrenDto> childrenToWatch) {
        this.childrenToWatch = childrenToWatch;
    }
}
