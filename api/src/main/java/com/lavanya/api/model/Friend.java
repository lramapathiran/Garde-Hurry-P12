package com.lavanya.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate date;

    @Column(name="is_accepted")
    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
    @JsonBackReference
    private User userWhoInvite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="friend_id", nullable=false, referencedColumnName = "id")
    @JsonBackReference
    private User userInvited;

    public Friend() {
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

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public User getUserWhoInvite() {
        return userWhoInvite;
    }

    public void setUserWhoInvite(User userWhoInvite) {
        this.userWhoInvite = userWhoInvite;
    }

    public User getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(User userInvited) {
        this.userInvited = userInvited;
    }
}
