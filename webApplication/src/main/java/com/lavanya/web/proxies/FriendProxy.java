package com.lavanya.web.proxies;

import com.lavanya.web.dto.FriendDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "friendApi", url = "localhost:9090")
public interface FriendProxy {

    @PostMapping(value="/saveFriend", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void save(@RequestBody FriendDto friendDto, @RequestHeader(name = "Authorization") String token);

    @GetMapping(value="/isFriend/{userProfileVisitedId}")
    Boolean isMyfriend(@RequestHeader(name = "Authorization") String token, @PathVariable ("userProfileVisitedId") int userProfileVisitedId);

    @GetMapping(value="/friendsRequest", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    List<FriendDto> getFriendRequestsByUser(@RequestHeader(name = "Authorization") String token);

    @GetMapping(value="/friends", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    List<FriendDto>getFriendsListByUser(@RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/updateFriend/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void acceptFriendInvitation(@PathVariable ("id") int id, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/deleteFriend/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void refuseFriendInvitation(@PathVariable ("id") int id, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/users/friend/{userInvitedId}")
    public FriendDto getFriendById(@PathVariable("userInvitedId") int userInvited, @RequestHeader(name = "Authorization") String token);

    @GetMapping("user/count/friends")
    public Integer getCountOfFriendsByUser(@RequestHeader(name = "Authorization") String token);

}
