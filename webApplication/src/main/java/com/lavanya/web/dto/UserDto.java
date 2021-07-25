package com.lavanya.web.dto;

import java.util.List;

public class UserDto {

    private Integer id;
    private String lastName;
    private String firstName;
    private String address;
    private String city;
    private String area;
    private String email;
    private Boolean isValidated;
    private Boolean situation;
    private String roles;
    private List<ChildrenDto> children;
    private List<CommentDto> commentsReceived;
    private List<CommentDto> commentsMade;
    private List<ChildcareDto> childcareRequests;
    private List<ChildcareDto> childcareMissions;
    private List<FriendDto> sentInvitations;
    private List<FriendDto> receivedInvitations;

    public UserDto() {
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<ChildrenDto> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenDto> children) {
        this.children = children;
    }

    public List<CommentDto> getCommentsReceived() {
        return commentsReceived;
    }

    public void setCommentsReceived(List<CommentDto> commentsReceived) {
        this.commentsReceived = commentsReceived;
    }

    public List<CommentDto> getCommentsMade() {
        return commentsMade;
    }

    public void setCommentsMade(List<CommentDto> commentsMade) {
        this.commentsMade = commentsMade;
    }

    public List<ChildcareDto> getChildcareRequests() {
        return childcareRequests;
    }

    public void setChildcareRequests(List<ChildcareDto> childcareRequests) {
        this.childcareRequests = childcareRequests;
    }

    public List<ChildcareDto> getChildcareMissions() {
        return childcareMissions;
    }

    public void setChildcareMissions(List<ChildcareDto> childcareMissions) {
        this.childcareMissions = childcareMissions;
    }

    public List<FriendDto> getSentInvitations() {
        return sentInvitations;
    }

    public void setSentInvitations(List<FriendDto> sentInvitations) {
        this.sentInvitations = sentInvitations;
    }

    public List<FriendDto> getReceivedInvitations() {
        return receivedInvitations;
    }

    public void setReceivedInvitations(List<FriendDto> receivedInvitations) {
        this.receivedInvitations = receivedInvitations;
    }
}
