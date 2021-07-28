package com.lavanya.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    private String address;

    private String city;

    private String area;

    private String email;

    private String password;

    @Column(name="is_validated")
    private Boolean isValidated;

    private Boolean situation;

    @Column(name="is_active")
    private boolean isActive;

    private String roles;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Children> children;

    @OneToMany(mappedBy="userCommented", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentsReceived;

    @OneToMany(mappedBy="userComment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentsMade;

    @OneToMany(mappedBy="userInNeed", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Childcare> childcareRequests;

    @OneToMany(mappedBy="userWatching", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Childcare> childcareMissions;

    @OneToMany(mappedBy="userWhoInvite", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Friend> sentFriendInvitations;

    @OneToMany(mappedBy="userInvited", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Friend> receivedFriendInvitations;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
    }

    public Boolean getSituation() {
        return situation;
    }

    public void setSituation(Boolean situation) {
        this.situation = situation;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public List<Comment> getCommentsReceived() {
        return commentsReceived;
    }

    public void setCommentsReceived(List<Comment> commentsReceived) {
        this.commentsReceived = commentsReceived;
    }

    public List<Comment> getCommentsMade() {
        return commentsMade;
    }

    public void setCommentsMade(List<Comment> commentsMade) {
        this.commentsMade = commentsMade;
    }

    public List<Childcare> getChildcareRequests() {
        return childcareRequests;
    }

    public void setChildcareRequests(List<Childcare> childcareRequests) {
        this.childcareRequests = childcareRequests;
    }

    public List<Childcare> getChildcareMissions() {
        return childcareMissions;
    }

    public void setChildcareMissions(List<Childcare> childcareMissions) {
        this.childcareMissions = childcareMissions;
    }

    public List<Friend> getSentFriendInvitations() {
        return sentFriendInvitations;
    }

    public void setSentFriendInvitations(List<Friend> sentFriendInvitations) {
        this.sentFriendInvitations = sentFriendInvitations;
    }

    public List<Friend> getReceivedFriendInvitations() {
        return receivedFriendInvitations;
    }

    public void setReceivedFriendInvitations(List<Friend> receivedFriendInvitations) {
        this.receivedFriendInvitations = receivedFriendInvitations;
    }
}
