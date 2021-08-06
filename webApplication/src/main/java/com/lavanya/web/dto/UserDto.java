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
    private String password;
    private Boolean isValidated;
    private Boolean situation;
    private String roles;
    private List<ChildrenDto> childrenDtos;
    private List<CommentDto> commentDtosReceived;
    private List<CommentDto> commentDtosMade;
    private List<ChildcareDto> childcareDtosRequests;
    private List<ChildcareDto> childcareDtosMissions;
    private List<FriendDto> sentFriendDtoInvitations;
    private List<FriendDto> receivedFriendDtoInvitations;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean isValidated) {
        isValidated = isValidated;
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

    public List<ChildrenDto> getChildrenDtos() {
        return childrenDtos;
    }

    public void setChildrenDtos(List<ChildrenDto> childrenDtos) {
        this.childrenDtos = childrenDtos;
    }

    public List<CommentDto> getCommentDtosReceived() {
        return commentDtosReceived;
    }

    public void setCommentDtosReceived(List<CommentDto> commentDtosReceived) {
        this.commentDtosReceived = commentDtosReceived;
    }

    public List<CommentDto> getCommentDtosMade() {
        return commentDtosMade;
    }

    public void setCommentDtosMade(List<CommentDto> commentDtosMade) {
        this.commentDtosMade = commentDtosMade;
    }

    public List<ChildcareDto> getChildcareDtosRequests() {
        return childcareDtosRequests;
    }

    public void setChildcareDtosRequests(List<ChildcareDto> childcareDtosRequests) {
        this.childcareDtosRequests = childcareDtosRequests;
    }

    public List<ChildcareDto> getChildcareDtosMissions() {
        return childcareDtosMissions;
    }

    public void setChildcareDtosMissions(List<ChildcareDto> childcareDtosMissions) {
        this.childcareDtosMissions = childcareDtosMissions;
    }

    public List<FriendDto> getSentFriendDtoInvitations() {
        return sentFriendDtoInvitations;
    }

    public void setSentFriendDtoInvitations(List<FriendDto> sentFriendDtoInvitations) {
        this.sentFriendDtoInvitations = sentFriendDtoInvitations;
    }

    public List<FriendDto> getReceivedFriendDtoInvitations() {
        return receivedFriendDtoInvitations;
    }

    public void setReceivedFriendDtoInvitations(List<FriendDto> receivedFriendDtoInvitations) {
        this.receivedFriendDtoInvitations = receivedFriendDtoInvitations;
    }
}
