package com.lavanya.api.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.service.UserService;
import com.lavanya.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest Controller to control all the requests related to User object.
 * @author lavanya
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public UserDto getUserConnected(@PathVariable("id") int id){
       return userService.getUserById(id);
    }

    /**
     * POST requests for /saveUser endpoint.
     * This controller-method saves an user and encode its password in database.
     *
     */
    @PostMapping("/saveUser")
    public void saveUser(@RequestBody UserDto userDto) {

        userService.saveUser(userDto);

    }

    @PostMapping("/updateUser")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }

    @PostMapping("/delete/user/{userToDeleteId}")
    public void deleteUser(@PathVariable("userToDeleteId") int userDtoToDeleteId){
        userService.deleteUser(userDtoToDeleteId);
    }

    /**
     * GET requests for /users endpoint.
     * This controller-method retrieves from database all users registered in database.
     *
     * @param currentPage an int to specify which page of Users to be displayed.
     * @return usersList page of all users registered
     */
    @GetMapping("/users/{pageNumber}")
    public Page<User> showUsersListByPage(@PathVariable(value = "pageNumber") int currentPage) {

        try {
            Page<User> usersList = userService.getAllUsers(currentPage);
            return usersList;
        }catch(JWTDecodeException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public List<UserDto> showUsersList(){
        return userService.getAllUsersInList();
    }

    @GetMapping("/loadUserByUsername/{username}")
    public UserDto loadUserByUsername(@PathVariable ("username") String username) {

        UserDto userDto = userService.findUserByUsername(username);

        return userDto;

    }







}
