package com.lavanya.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CommentDto {

    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private String content;
    private UserDto userCommented;
    private UserDto userComment;

    public CommentDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDto getUserCommented() {
        return userCommented;
    }

    public void setUserCommented(UserDto userCommented) {
        this.userCommented = userCommented;
    }

    public UserDto getUserComment() {
        return userComment;
    }

    public void setUserComment(UserDto userComment) {
        this.userComment = userComment;
    }
}
