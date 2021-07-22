package com.lavanya.api.dto;


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
    private boolean isActive;
    private String roles;
    private List<ChildrenDto> children;
    private List<CommentDto> commentsReceived;
    private List<CommentDto> commentsMade;
    private List<ChildcareDto> childcareRequests;
    private List<ChildcareDto> childcareMissions;
    private List<FriendDto> sentInvitations;
    private List<FriendDto> receivedInvitations;



}
