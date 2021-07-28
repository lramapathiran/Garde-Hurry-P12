package com.lavanya.api.controller;

import com.lavanya.api.dto.UserDto;
import com.lavanya.api.service.UserService;
import com.lavanya.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void saveUser(@RequestBody UserDto userDto) {

        userService.saveUser(userDto);

    }

    /**
     * GET requests for /users endpoint.
     * This controller-method retrieves from database all users registered in database.
     *
     * @param currentPage an int to specify which page of Users to be displayed.
     * @return usersList page of all users registered
     */
    @GetMapping("/users")
    public Page<User> showUsersListByPage(@RequestParam (name="pageNumber") int currentPage) {


        Page<User> usersList = userService.getAllUsers(currentPage);

        return usersList;
    }






}
