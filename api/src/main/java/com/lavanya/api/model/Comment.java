package com.lavanya.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Bean representing a Comment.
 * Comment object is declared as a JPA entity with the corresponding annotation.
 * @author lavanya
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_commented_id", nullable=false, referencedColumnName = "id")
    private User userCommented;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_comment_id", nullable=false, referencedColumnName = "id")
    private User userComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="childcare_id", nullable=false, referencedColumnName = "id")
    private Childcare childcare;

    public Comment() {
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

    public User getUserCommented() {
        return userCommented;
    }

    public void setUserCommented(User userCommented) {
        this.userCommented = userCommented;
    }

    public User getUserComment() {
        return userComment;
    }

    public void setUserComment(User userComment) {
        this.userComment = userComment;
    }

    public Childcare getChildcare() {
        return childcare;
    }

    public void setChildcare(Childcare childcare) {
        this.childcare = childcare;
    }
}
