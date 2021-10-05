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
     * POST requests for /saveFriend endpoint.
     * method to save a friend request between two users.
     * @param friendDto to save in database.
     * @return LocalDate for the earliest due date amoung all the copies of a book borrowed.
     */
    @PostMapping(value="/saveFriend")
    public void save(@RequestBody FriendDto friendDto) {

        friendService.save(friendDto);

    }

    /**
     * GET requests for /isFriend/{userProfileVisitedId} endpoint.
     * method to verify if a friend relationship exists between two users even if this relation is still awaiting a validation from one of the parties.
     * @param userProfileVisitedId uuid of the user wih who the user connected need to verify an already existing friendship.
     * @return a boolean true or false
     */
    @GetMapping(value="/isFriend/{userProfileVisitedId}")
    public Boolean isMyfriend(@PathVariable ("userProfileVisitedId") UUID userProfileVisitedId) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return  friendService.isUsersFriends(username,userProfileVisitedId);
    }

    /**
     * GET requests for /friendsRequest endpoint.
     * method to retrieve list of all Friends requests sent by a user of interest but not accepted yet.
     * @return list of FriendDtos.
     */
    @GetMapping(value="/friendsRequest")
    public List<FriendDto> getFriendRequestsByUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.getListOfFriendRequests(username);
    }

    /**
     * GET requests for /friends endpoint.
     * method to retrieve list of all Friends of the user connected.
     * @return list of FriendDtos.
     */
    @GetMapping(value="/friends")
    public List<FriendDto> getFriendsListByUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.getListOfAllFriendsByUser(username);

    }

    /**
     * POST requests for /updateFriend/{id} endpoint.
     * method to validate a friend request as accepted by the user who was invited.
     * @param id of the friend entity to accept.
     */
    @PostMapping(value="/updateFriend/{id}")
    public void acceptFriendInvitation(@PathVariable ("id") int id){
        friendService.updateFriend(id);
    }

    /**
     * POST requests for /deleteFriend/{id} endpoint.
     * method to delete a friend request not accepted yet or friend relation already accepted.
     * @param id of the friend entity of interest.
     */
    @PostMapping(value="/deleteFriend/{id}")
    public void refuseFriendInvitation(@PathVariable ("id") int id){
        friendService.deleteFriend(id);
    }

    /**
     * GET requests for /users/friend/{userInvitedId} endpoint.
     * method to retrieve a friend entity using both users involved.
     * @param userInvitedId uuid of the user involved in the friend relationship.
     * @return FriendDto.
     */
    @GetMapping("/users/friend/{userInvitedId}")
    public FriendDto getFriendById(@PathVariable("userInvitedId") UUID userInvitedId) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.findFriendRelationshipByBothUsersId(userInvitedId,username);
    }

    /**
     * GET requests for user/count/friends endpoint.
     * method to count the amount of Friends the user connected has.
     * @return Integer.
     */
    @GetMapping("user/count/friends")
    public Integer getCountOfFriendsByUser() {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return friendService.getcountOfFriendsByUser(username);
    }

}
