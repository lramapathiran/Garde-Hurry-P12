package com.lavanya.api.controller;

import com.lavanya.api.dto.FriendDto;
import com.lavanya.api.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Rest Controller to control all the requests related to Friend object.
 * @author lavanya
 */
@RestController
public class FriendController {

    @Autowired
    FriendService friendService;

    /**
     * method to save a friend request between two users.
     * @param friendDto to save in database.
     * @return LocalDate for the earliest due date amoung all the copies of a book borrowed.
     */
    @PostMapping(value="/saveFriend")
    public void save(@RequestBody FriendDto friendDto) {

        friendService.save(friendDto);

    }

    @GetMapping(value="/isFriend/{userProfileVisitedId}")
    public Boolean isMyfriend(@PathVariable ("userProfileVisitedId") UUID userProfileVisitedId) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return  friendService.isUsersFriends(username,userProfileVisitedId);
    }

    @GetMapping(value="/friendsRequest")
    public List<FriendDto> getFriendRequestsByUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.getListOfFriendRequests(username);
    }

    @GetMapping(value="/friends")
    public List<FriendDto> getFriendsListByUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.getListOfAllFriendsByUser(username);

    }

    @PostMapping(value="/updateFriend/{id}")
    public void acceptFriendInvitation(@PathVariable ("id") int id){
        friendService.updateFriend(id);
    }

    @PostMapping(value="/deleteFriend/{id}")
    public void refuseFriendInvitation(@PathVariable ("id") int id){
        friendService.deleteFriend(id);
    }

    @GetMapping("/users/friend/{userInvitedId}")
    public FriendDto getFriendById(@PathVariable("userInvitedId") UUID userInvitedId) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.findFriendRelationshipByBothUsersId(userInvitedId,username);
    }

    @GetMapping("user/count/friends")
    public Integer getCountOfFriendsByUser() {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.getcountOfFriendsByUser(username);
    }

}
