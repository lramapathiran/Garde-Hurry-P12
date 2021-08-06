package com.lavanya.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lavanya.web.configuration.SimplePageImpl;
import com.lavanya.web.dto.FriendDto;
import com.lavanya.web.dto.UserDto;
import com.lavanya.web.proxies.FriendProxy;
import com.lavanya.web.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller used in MVC architecture to control all the requests related to User object.
 * @author lavanya
 */
@Controller
public class UserController {

    @Autowired
    UserProxy userProxy;

    @Autowired
    FriendProxy friendProxy;


    /**
     * GET requests for /users endpoint.
     * This controller-method retrieves from database all users registered with admin or user role and pass that list to the view "usersList.html"
     *
     * @param model to pass data to the view.
     * @param currentPage an int to specify which page of Users to be displayed.
     * @return usersList.html
     */
    @GetMapping("/users/{pageNumber}")
    public String showUsersListByPage(@PathVariable(value = "pageNumber") int currentPage, Model model) throws JsonProcessingException {

        String json = new Gson().toJson(userProxy.showUsersList());
        List<UserDto> userDtos = new ObjectMapper().readValue(
                json, new TypeReference<List<UserDto>>() { }
        );



        Sort sort = Sort.by("lastName").ascending();
        Pageable pageable = PageRequest.of(currentPage -1, 10, sort);

        final Page<UserDto> page = new PageImpl<>(userDtos, pageable, userDtos.size());

//        Page<UserDto> page = userProxy.showUsersListByPage(currentPage);


        List<UserDto> usersPage = page.getContent();
        int totalPages = page.getTotalPages();
        long totalUsers = page.getTotalElements();

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalUsers", totalUsers);

//        List<UserDto> userDtos = userProxy.showUsersList();
//        model.addAttribute("usersList", userDtos);


        return "usersList";

    }

    /**
     * GET requests for /signup endpoint.
     * This controller-method creates a new object User and pass it to the form for the User to be created with all its attributes.
     *
     * @param model to pass data to the view.
     * @return addUser.html
     */
    @GetMapping("/signup")
    public String showSignUpForm (Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "addUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute ("user") UserDto userDto, Model model) {
        userProxy.saveUser(userDto);

        int userId = userProxy.loadUserByUsername(userDto.getEmail()).getId();
        return "redirect:/user/" + userId;
    }

    @GetMapping("updateProfile/{id}")
    public String showUserProfileFormForUpdate(@PathVariable("id") int userConnectedId, Model model){
        UserDto userDto = userProxy.getUserConnected(userConnectedId);
        model.addAttribute("user",userDto);
        return "updateProfile";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute ("user") UserDto userDto) {
        userProxy.updateUser(userDto);

        int userId = userProxy.loadUserByUsername(userDto.getEmail()).getId();
        return "redirect:/user/" + userId;
    }

    @GetMapping("/user/{id}")
    public String showUserProfile(@PathVariable("id") int id, Model model) {
        UserDto userDto = userProxy.getUserConnected(id);
        model.addAttribute("user", userDto);
        return "userProfile";
    }

    @GetMapping("profile/user/{id}/{userConnectedId}")
    public String showUserProfileToVisit(@PathVariable("id") int id, @PathVariable("userConnectedId") int userConnectedId, Model model) {
        UserDto userDto = userProxy.getUserConnected(id);
        UserDto userDtoWhoInvite = userProxy.getUserConnected(userConnectedId);
        int totalChildren = userDto.getChildrenDtos().size();

        Boolean isMyFriend = friendProxy.isMyfriend(userConnectedId,id);
        if(isMyFriend){
            FriendDto friendDto = friendProxy.getFriendByIds(id,userConnectedId);
            model.addAttribute("friend", friendDto);
        }

        model.addAttribute("user", userDto);
        model.addAttribute("numberOfChildren", totalChildren);
        model.addAttribute("userConnected", userDtoWhoInvite);
        model.addAttribute("userConnectedId", userConnectedId);
        model.addAttribute("isMyFriend", isMyFriend);

        return "userProfileToVisit";
    }



}
