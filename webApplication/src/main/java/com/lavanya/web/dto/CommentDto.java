package com.lavanya.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Bean representing a data transfer Object CommentDto.
 * CommentDto has all attributes required to display a comment.
 * @author lavanya
 */
public class CommentDto {

    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    @NotBlank(message="Veuillez remplir ce champs")
    @Size(min = 5, max = 600, message
            = "Votre message doit comprendre entre 5 et 600 caractères!")
    private String content;
    private UserDto userCommented;
    private UserDto userComment;
    private ChildcareDto childcareDto;

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

    public ChildcareDto getChildcareDto() {
        return childcareDto;
    }

    public void setChildcareDto(ChildcareDto childcareDto) {
        this.childcareDto = childcareDto;
    }
}
