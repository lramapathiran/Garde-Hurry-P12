package com.lavanya.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Bean representing a Childcare.
 * Childcare object is declared as a JPA entity with the corresponding annotation.
 * @author lavanya
 */
@Entity
public class Childcare {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "time_start")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private LocalTime timeStart;

    @Column(name = "time_end")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private LocalTime timeEnd;
    private String description;

    @Column(name = "is_complete")
    private Boolean isComplete;

    @ManyToOne
    @JoinColumn(name="user_in_need_id", nullable=false, referencedColumnName = "id")
    @JsonBackReference
    private User userInNeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_in_charge_id", nullable=false, referencedColumnName = "id")
    @JsonBackReference
    private User userWatching;

    @Column(name = "number_of_children")
    private Integer numberOfChildren;

    @Column(name = "childcare_validated")
    private Boolean isValidated;

    @Column(name="is_accomplished")
    private  Boolean isAccomplished;

    @Column(name="user_in_need_feedback")
    private Boolean inNeedComment;

    @Column(name="user_in_charge_feedback")
    private Boolean inChargeComment;

    @ManyToMany(mappedBy = "childcares")
    private List<Children> childrenToWatch;

    @OneToMany(mappedBy="childcare")
    private List<Comment> comments;

    public Childcare() {
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

    public User getUserInNeed() {
        return userInNeed;
    }

    public void setUserInNeed(User userInNeed) {
        this.userInNeed = userInNeed;
    }

    public User getUserWatching() {
        return userWatching;
    }

    public void setUserWatching(User userWatching) {
        this.userWatching = userWatching;
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

    public void setAccomplished(Boolean accomplished) {
        isAccomplished = accomplished;
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

    public List<Children> getChildrenToWatch() {
        return childrenToWatch;
    }

    public void setChildrenToWatch(List<Children> childrenToWatch) {
        this.childrenToWatch = childrenToWatch;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
