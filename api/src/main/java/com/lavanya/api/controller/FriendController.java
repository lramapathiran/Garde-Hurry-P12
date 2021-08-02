package com.lavanya.api.controller;

import com.lavanya.api.dto.FriendDto;
import com.lavanya.api.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value="/isFriend/{userConnectedId}/{userProfileVisitedId}")
    public Boolean isMyfriend(@PathVariable ("userConnectedId") int userConnectedId, @PathVariable ("userProfileVisitedId") int userProfileVisitedId) {

        return  friendService.isUsersFriends(userConnectedId,userProfileVisitedId);
    }

    @PostMapping(value="/friendsRequest/{id}")
    public List<FriendDto> FriendRequests(@PathVariable("id") int userConnected) {
        return friendService.getListOfFriendRequests(userConnected);
    }



}
