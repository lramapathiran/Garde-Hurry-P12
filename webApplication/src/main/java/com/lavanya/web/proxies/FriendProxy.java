package com.lavanya.web.proxies;

import com.lavanya.web.dto.FriendDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "friendApi", url = "localhost:9090")
public interface FriendProxy {

    @PostMapping(value="/saveFriend", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void save(@RequestBody FriendDto friendDto);

    @GetMapping(value="/isFriend/{userConnectedId}/{userProfileVisitedId}")
    Boolean isMyfriend(@PathVariable("userConnectedId") int userConnectedId, @PathVariable ("userProfileVisitedId") int userProfileVisitedId);

    @GetMapping(value="/friendsRequest/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    List<FriendDto> getFriendRequestsByUser(@PathVariable ("id") int userConnected);

    @GetMapping(value="/friends/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    List<FriendDto>getFriendsListByUser(@PathVariable ("id") int userConnected);

    @PostMapping(value="/updateFriend/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void acceptFriendInvitation(@PathVariable ("id") int id);

    @PostMapping(value="/deleteFriend/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void refuseFriendInvitation(@PathVariable ("id") int id);

    @GetMapping("/users/friend/{userInvitedId}/{userWhoInviteId}")
    public FriendDto getFriendByIds(@PathVariable("userInvitedId") int userInvited, @PathVariable("userWhoInviteId") int userWhoInviteId);

}
