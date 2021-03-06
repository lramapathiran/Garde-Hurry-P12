package com.lavanya.api.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Bean representing an User.
 * User object is declared as a JPA entity with the corresponding annotation.
 * @author lavanya
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

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

    @OneToMany(mappedBy="user")
    private List<Children> childrens;

    @OneToMany(mappedBy="userCommented")
    private List<Comment> commentsReceived;

    @OneToMany(mappedBy="userComment")
    private List<Comment> commentsMade;

    @OneToMany(mappedBy="userInNeed")
    private List<Childcare> childcareRequests;

    @OneToMany(mappedBy="userWatching")
    private List<Childcare> childcareMissions;

    @OneToMany(mappedBy="userWhoInvite")
    private List<Friend> sentFriendInvitations;

    @OneToMany(mappedBy="userInvited")
    private List<Friend> receivedFriendInvitations;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public List<Children> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Children> childrens) {
        this.childrens = childrens;
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
