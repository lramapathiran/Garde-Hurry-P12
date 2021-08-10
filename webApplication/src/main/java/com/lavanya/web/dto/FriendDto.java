package com.lavanya.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class FriendDto {

    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate date;
    private Boolean isAccepted;
    private UserDto userWhoInvite;
    private UserDto userInvited;

    public FriendDto() {
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

    public UserDto getUserWhoInvite() {
        return userWhoInvite;
    }

    public void setUserWhoInvite(UserDto userWhoInvite) {
        this.userWhoInvite = userWhoInvite;
    }

    public UserDto getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(UserDto userInvited) {
        this.userInvited = userInvited;
    }
}
