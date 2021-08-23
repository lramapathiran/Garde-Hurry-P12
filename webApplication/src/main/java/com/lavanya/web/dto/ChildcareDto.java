package com.lavanya.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ChildcareDto {

    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ce champs est obligatoire")
    private LocalDate date;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    @NotNull(message = "Ce champs est obligatoire")
    private LocalTime timeStart;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    @NotNull(message = "Ce champs est obligatoire")
    private LocalTime timeEnd;
    private String description;
    private Boolean isComplete;
    private UserDto userDtoInNeed;
    private UserDto userDtoWatching;
    @NotNull(message = "Ce champs est obligatoire")
    private Integer numberOfChildren;
    private Boolean isValidated;
    private  Boolean isAccomplished;
    private Boolean inNeedComment;
    private Boolean inChargeComment;
    private List<ChildrenDto> childrenToWatch;
    private List<CommentDto> commentDtos;

    public ChildcareDto() {
    }

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

    public Boolean getAccomplished() {
        return isAccomplished;
    }

    public Boolean getInNeedComment() {
        return inNeedComment;
    }

    public void setInNeedComment(Boolean inNeedComment) {
        this.inNeedComment = inNeedComment;
    }

    public Boolean getInChargeComment() {
        return inChargeComment;
    }

    public void setInChargeComment(Boolean inChargeComment) {
        this.inChargeComment = inChargeComment;
    }

    public void setAccomplished(Boolean accomplished) {
        isAccomplished = accomplished;
    }

    public List<ChildrenDto> getChildrenToWatch() {
        return childrenToWatch;
    }

    public void setChildrenToWatch(List<ChildrenDto> childrenToWatch) {
        this.childrenToWatch = childrenToWatch;
    }

    public List<CommentDto> getCommentDtos() {
        return commentDtos;
    }

    public void setCommentDtos(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }
}
