package com.lavanya.web.proxies;

import com.lavanya.web.dto.AuthBodyDto;
import com.lavanya.web.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * interface required to communicate with api module and make all the requests related to User object.
 * @author lavanya
 */
@FeignClient(name = "userApi", url = "localhost:9090")
public interface UserProxy {

    @GetMapping("/user")
    UserDto getUserConnected(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/user/{id}")
    UserDto getUser(@PathVariable(name = "id") UUID userId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/login", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    String login(@RequestBody AuthBodyDto data);

    @GetMapping("/users/{pageNumber}")
    Page<UserDto> showUsersListByPage(@PathVariable(value = "pageNumber") int currentPage, @RequestHeader(name = "Authorization") String token);

    @GetMapping(value="/users", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> showUsersList(@RequestHeader(name = "Authorization") String token);

    @PostMapping (value="/saveUser", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void saveUser(@RequestBody UserDto userDto);

    @PostMapping (value="/updateUser", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void updateUser(@RequestBody UserDto userDto, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/loadUserByUsername/{username}")
    UserDto loadUserByUsername(@PathVariable ("username") String username);

    @PostMapping("/delete/user/{userToDeleteId}")
    void deleteUser(@PathVariable("userToDeleteId") UUID userDtoToDeleteId, @RequestHeader(name = "Authorization") String token);

    @PostMapping(value="/validate/profile", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void validateOrNotUserProfile(@RequestBody UserDto userDto, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/search/users/{pageNumber}")
    Page<UserDto> getUserSearchPage(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "pageNumber") int currentPage,
                                           @RequestParam(name="keyword", required=false) String keyword);
}
