package com.lavanya.api.controller;

import com.lavanya.api.service.UserService;
import com.lavanya.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller to control all the requests related to User object.
 * @author lavanya
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * POST requests for /saveUser endpoint.
     * This controller-method saves an user and encode its password in database.
     *
     */
    @PostMapping("/saveUser")
    public void saveUser(@RequestBody User user) {

        userService.saveUser(user);

    }

    

}
