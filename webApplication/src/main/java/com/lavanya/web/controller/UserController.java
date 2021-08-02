package com.lavanya.web.controller;

import com.lavanya.web.dto.UserDto;
import com.lavanya.web.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller used in MVC architecture to control all the requests related to User object.
 * @author lavanya
 */
@Controller
public class UserController {

    @Autowired
    UserProxy userProxy;

    /**
     * GET requests for /users endpoint.
     * This controller-method retrieves from database all users registered with admin or user role and pass that list to the view "usersList.html"
     *
     * @param model to pass data to the view.
     * @param currentPage an int to specify which page of Users to be displayed.
     * @return usersList.html
     */
    @GetMapping("/users/{pageNumber}")
    public String showUsersListByPage(@PathVariable(value = "pageNumber") int currentPage, Model model) {

//        model.addAttribute("user", userConnected);
//
//        int userId = userConnected.getId();

        Page<UserDto> page = userProxy.showUsersListByPage(currentPage);

        List<UserDto> usersPage = page.getContent();
        int totalPages = page.getTotalPages();
        long totalUsers = page.getTotalElements();

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalUsers", totalUsers);


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

    @GetMapping("/user/{id}")
    public String showUserProfile(@PathVariable("id") int id, Model model) {
        UserDto userDto = userProxy.getUserConnected(id);
        model.addAttribute("user", userDto);
        return "userProfile";
    }

    @GetMapping("profile/user/{id}")
    public String showUserProfileToVisit(@PathVariable("id") int id, Model model) {
        UserDto userDto = userProxy.getUserConnected(id);
        int totalChildren = userDto.getChildrenDtos().size();

        model.addAttribute("user", userDto);
        model.addAttribute("numberOfChildren", totalChildren);
        return "userProfileToVisit";
    }

    /**
     * POST request to send notification to another user to part of friends list.
     *
     * @param topo is the object Topo whose reservation attribute needs to be updated.
     * @param model to pass data to the view.
     * @return the url /user/topos
     */
    @PostMapping("/request/friend")
    public String sendRequestForFriendInvitation(Friend friend) {

        int id = topo.getId();

        topoService.save(topo);

        return "redirect:/topo/" + id;
    }




}
