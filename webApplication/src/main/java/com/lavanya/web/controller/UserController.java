package com.lavanya.web.controller;

import com.lavanya.web.dto.UserDto;
import com.lavanya.web.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @param userConnected is the authenticated User passed within the object MyUserDetails
     * @return usersList.html
     */
    @GetMapping("/users")
    public String showUsersListByPage(@RequestParam(name="pageNumber") int currentPage, Model model) {

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


}
