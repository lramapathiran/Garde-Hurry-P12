package com.lavanya.api.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.lavanya.api.configs.JwtTokenProvider;
import com.lavanya.api.dto.AuthBodyDto;
import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.model.AuthBody;
import com.lavanya.api.service.UserService;
import com.lavanya.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * POST requests for /login endpoint.
     * This controller-method send data required for user authentication.
     *
     * @param data is the bean where the password and username of the user are stored to authenticate the user.
     * @return token generated during the login step.
     */
    @PostMapping("/login")
    public String login(@RequestBody AuthBodyDto data) {
        try {
            String token = userService.generateToken(data);
            return token;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("L'identifiant et/ou le mot de passe sont invalides!");
        }
    }

    @GetMapping("/user")
    public UserDto getUserConnected(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return userService.getUser(username);
    }

    @GetMapping("/user/{id}")
    UserDto getUser(@PathVariable(name = "id") int userId){
        return userService.getUserById(userId);
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

        try {
            return userService.getAllUsersInList();

        } catch (JWTDecodeException e){
            throw new RuntimeException(e);
        }

    }

//    @GetMapping("/loadUserByUsername/{username}")
//    public UserDto loadUserByUsername(@PathVariable ("username") String username) {
//
//        UserDto userDto = userService.findUserByUsername(username);
//
//        return userDto;
//
//    }

    @PostMapping("/validate/profile")
    void validateOrNotUserProfile(@RequestBody UserDto userDto) {
        userService.updateUserProfileValidationStatus(userDto);
    }







}
