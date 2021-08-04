package com.lavanya.web.proxies;

import com.lavanya.web.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * interface required to communicate with api module and make all the requests related to User object.
 * @author lavanya
 */
@FeignClient(name = "userApi", url = "localhost:9090")
public interface UserProxy {

    @GetMapping("/user/{id}")
    UserDto getUserConnected(@PathVariable("id") int id);

//    @PostMapping(value="/api/auth/login", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
//    String login(@RequestBody AuthBodyDto data);

    @GetMapping(value="/users/{pageNumber}")
    Page<UserDto> showUsersListByPage(@PathVariable(value = "pageNumber") int currentPage);

    @PostMapping (value="/saveUser", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void saveUser(@RequestBody UserDto userDto);
}
