package com.lavanya.web.proxies;

import com.lavanya.web.configuration.WebappOpenFeignConfiguration;
import com.lavanya.web.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

/**
 * interface required to communicate with api module and make all the requests related to User object.
 * @author lavanya
 */
@FeignClient(name = "userApi", url = "localhost:9090",configuration = WebappOpenFeignConfiguration.class)
public interface UserProxy {

    @GetMapping("/user/{id}")
    Optional<UserDto> getUserConnected(@PathVariable("id") int id);

    @PostMapping(value="/api/auth/login", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    String login(@RequestBody AuthBodyDto data);

}
