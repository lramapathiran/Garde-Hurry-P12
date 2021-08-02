package com.lavanya.web.proxies;

import com.lavanya.web.configuration.WebappOpenFeignConfiguration;
import com.lavanya.web.dto.FriendDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "friendApi", url = "localhost:9090",configuration = WebappOpenFeignConfiguration.class)
public interface FriendProxy {

    @PostMapping(value="/saveFriend", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void save(@RequestBody FriendDto friendDto);

    @GetMapping(value="/isFriend/{userConnectedId}/{userProfileVisitedId}")
    Boolean isMyfriend(@PathVariable("userConnectedId") int userConnectedId, @PathVariable ("userProfileVisitedId") int userProfileVisitedId);

    @PostMapping(value="/friendsRequest/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    List<FriendDto> FriendRequests(@PathVariable ("id") int userConnected);
}
