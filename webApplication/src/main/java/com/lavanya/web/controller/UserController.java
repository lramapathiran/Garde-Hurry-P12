package com.lavanya.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavanya.web.configuration.SimplePageImpl;
import com.lavanya.web.dto.*;
import com.lavanya.web.proxies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Autowired
    ChildcareProxy childcareProxy;

    @Autowired
    CommentProxy commentProxy;

    @Autowired
    NotificationProxy notificationProxy;

    /**
     * GET requests for /homePage endpoint.
     * This controller-method show the homePage of the site and the login form for the user to be connected
     *
     * @param model  to pass data to the view.
     * @param error only when an error exist while login in.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return index.html
     */
    @GetMapping(value="/homePage")
    public String showLoginAndHomePage (@RequestParam(value = "error", required = false) String error,
                                        HttpSession session, Model model) {


        String token = (String) session.getAttribute("token");

        if (token!=null) {
            String subToken = token.substring(7);

            DecodedJWT jwt = JWT.decode(subToken);
            String fullname = jwt.getClaim("fullname").asString();

            model.addAttribute("fullname", fullname);
        }

        String errorMessage = null;
        if(error != null) {
            errorMessage = "L'identifiant ou le mot de passe est incorrect!!";
        }
        model.addAttribute("errorMessage", errorMessage);

        AuthBodyDto authBody = new AuthBodyDto();
        model.addAttribute("authBodyDto", authBody);
        return "index.html";
    }

    /**
     * POST requests for /login endpoint.
     * This controller-method send data required for user authentication to the api module.
     *
     * @param data is the bean where the password and username of the user are stored to authenticate the user.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated while login in.
     * @return redirect to userDashboard.html
     */
    @PostMapping("/login")
    public String sendAuthBodyForAuthentication(AuthBodyDto data, HttpSession session) {

        try{
            String resp = userProxy.login(data);
            String token = "Bearer " + resp;
            session.setAttribute("token", token);
            return "redirect:/user/homePage";
        }catch (Exception e) {
            return "redirect:/homePage?error=true#sign-in";
        }

    }

    /**
     * GET requests for /logout endpoint.
     * This controller-method is used to logout a user.
     * @param session HttpSession that needs to be invalidated to log out the user of interest.
     * @return homePage.html.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.setAttribute("token", null);

        return "redirect:/homePage";
    }

    @GetMapping("/user/homePage")
    public String showUserConnectedMainDashboard(HttpSession session, Model model){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        int positiveBadges = childcareProxy.countOfPositiveBadgesByUserId(token);
        int negativeBadges = childcareProxy.countOfNegativeBadgesByUserId(token);

        Integer userBadges = positiveBadges - negativeBadges;

        int userBadgesMean = 0;

        if(userBadges < 0){
            userBadgesMean = -userBadges;
            model.addAttribute("negativeBadgesNumber", userBadgesMean);

        }else{
            userBadgesMean = userBadges;
            model.addAttribute("positiveBadgesNumber", userBadgesMean);
        }

        Integer countOfFriends = friendProxy.getCountOfFriendsByUser(token);

        UserDto userDto = userProxy.getUserConnected(token);

        List<ChildcareDto> listOfRequests = childcareProxy.getChildcaresOfUserInNeedNotCommented(token);
        List<ChildcareDto> listOfMissions = childcareProxy.getChildcaresOfUserInChargeNotCommented(token);

        int countOfChildcaresToComment = childcareProxy.countOfChildcaresToCommentByUserInChargeId(token) + childcareProxy.countOfChildcaresToCommentByUserInNeedId(token);
        int countOfChildcaresToValidate = childcareProxy.countOfChildcaresToValidateByUserInChargeId(token);

        model.addAttribute("NumberOfFriends",countOfFriends);
        model.addAttribute("user",userDto);
        model.addAttribute("requests", listOfRequests);
        model.addAttribute("missions", listOfMissions);
        model.addAttribute("countOfChildcaresToComment", countOfChildcaresToComment);
        model.addAttribute("countOfChildcaresToValidate", countOfChildcaresToValidate);

        return "mainDashboard";
    }

    /**
     * GET requests for /users endpoint.
     * This controller-method retrieves from database all users registered with admin or user role and pass that list to the view "usersList.html"
     *
     * @param model to pass data to the view.
     * @param pageNumber an int to specify which page of Users to be displayed.
     * @return usersList.html
     */
    @GetMapping("/users/{pageNumber}")
    public String showUsersListByPage(@PathVariable(value = "pageNumber") int pageNumber, Model model, HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        List<UserDto> listUserDtos;
        Pageable pageable = PageRequest.of(pageNumber -1, 5);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<UserDto> userDtos = userProxy.showUsersList(token);

        int toIndex = Math.min(startItem + pageSize, userDtos.size());
        listUserDtos = userDtos.subList(startItem, toIndex);

        Page<UserDto> page = new PageImpl<UserDto>(listUserDtos, PageRequest.of(currentPage, pageSize), userDtos.size());
        List<UserDto> usersPage = page.getContent();
        int totalPages = page.getTotalPages();
        long totalUsers = page.getTotalElements();

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", pageNumber);
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
    public String showSignUpForm (@RequestParam(value = "error", required = false) String error,Model model) {

        UserToRegister user = new UserToRegister();
        model.addAttribute("user", user);

        String errorMessage = null;
        if(error != null) {
            errorMessage = "L'email renseigné existe déjà, Veuillez renseigner une autre adresse email!!";
        }
        model.addAttribute("errorMessage", errorMessage);

        return "addUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute ("user") UserToRegister user, Model model, HttpSession session) {

        AuthBodyDto data = new AuthBodyDto();
        data.setUsername(user.getEmail());
        data.setPassword(user.getPassword());

        String fullName = user.getFirstName() + " " + user.getLastName();

        NotificationDto notificationDto = new NotificationDto(fullName,user.getEmail());

        try{
            userProxy.saveUser(user);
            String resp = userProxy.login(data);
            String token = "Bearer " + resp;
            session.setAttribute("token", token);
        }catch(Exception e) {
            return "redirect:/signup?error=true";
        }

        notificationProxy.alertEmailForNewUserToValidateProfile(notificationDto);
        return "redirect:/createChildren";

    }

    @GetMapping("/updateProfile")
    public String showUserProfileFormToUpdate(Model model,HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        UserDto userDto = userProxy.getUserConnected(token);
        model.addAttribute("user",userDto);
        return "updateProfile";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute ("user") UserDto userDto, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        userProxy.updateUser(userDto, token);

        return "redirect:/user";
    }

    @PostMapping("/delete/user")
    public String deleteUser(@ModelAttribute ("uuid") UUID userDtoToDeleteId, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        userProxy.deleteUser(userDtoToDeleteId, token);
        return "redirect:/users/1";
    }

    @GetMapping("/user")
    public String showUserProfile(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        UserDto userDto = userProxy.getUserConnected(token);
        model.addAttribute("user", userDto);
        return "userProfile";
    }

    @GetMapping("profile/user/{uuid}")
    public String showUserProfileToVisit(@PathVariable("uuid") UUID id, HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        UserDto userDto = userProxy.getUser(id, token);
        UserDto userDtoWhoInvite = userProxy.getUserConnected(token);

        Boolean isUsersMatch = userDto.getEmail().equals(userDtoWhoInvite.getEmail());
        int totalChildren = userDto.getChildrenDtos().size();

        Boolean isMyFriend = friendProxy.isMyfriend(token,id);
        if(isMyFriend){
            FriendDto friendDto = friendProxy.getFriendById(id,token);
            model.addAttribute("friend", friendDto);
        }

        model.addAttribute("user", userDto);
        model.addAttribute("numberOfChildren", totalChildren);
        model.addAttribute("userConnected", userDtoWhoInvite);
        model.addAttribute("isMyFriend", isMyFriend);
        model.addAttribute("usersMatch", isUsersMatch);

        List<CommentDto> userCommentsReceived = commentProxy.getListOfCommentsByUserId(id,token);

        model.addAttribute("comments",userCommentsReceived);

        return "userProfileToVisit";
    }

    @PostMapping("/validateProfile/{pageNumber}")
    public String validateProfile(Validate validate, @PathVariable("pageNumber") int currentPage, HttpSession session){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        UserDto userDto = userProxy.getUser(validate.getUserToValidateId(), token);

        if(validate.getProfileStatus()==null){
            userDto.setValidated(false);
        }else{
            userDto.setValidated(true);
        }


        userProxy.validateOrNotUserProfile(userDto, token);

        return "redirect:/users/" + currentPage;
    }

    /**
     * GET requests for /showUsers/{pageNumber} endpoint.
     * This controller-method retrieves all users searched by keyword, display them as Page to the view "searchUser".
     * It offers the possibility to search a user with filters.
     *
     * @param model to pass data to the view.
     * @param currentPage an int to specify which page of Users to be displayed.
     * @param keyword a String attribute from Search object used to filter a search users by keyword.
     * @param session a HttpSession where attributes of interest are stored, here it concerns the token generated following user connection.
     * @return "searchUser.html".
     */
    @GetMapping("/showUsers/{pageNumber}")
    public String showUsersFiltered(@PathVariable(value = "pageNumber") int currentPage, HttpSession session,
    @RequestParam(name="keyword", required=false) String keyword, Model model) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        String subToken = token.substring(7);
        DecodedJWT jwt = JWT.decode(subToken);
        String fullname = jwt.getClaim("fullname").asString();
        String role = jwt.getClaim("role").asString();

        model.addAttribute("role", role);
        model.addAttribute("fullname", fullname);

        Page<UserDto> pageOfUsersFiltered = userProxy.getUserSearchPage(token,currentPage,keyword);

        model.addAttribute("keyword", keyword);

        List<UserDto> usersPage = pageOfUsersFiltered.getContent();
        int totalPages = pageOfUsersFiltered.getTotalPages();
        long totalUsers = pageOfUsersFiltered.getTotalElements();

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalUsers", totalUsers);

        return "searchUser";

    }



}
