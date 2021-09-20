package com.lavanya.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lavanya.web.comparator.UserDtoLastNameComparator;
import com.lavanya.web.dto.FriendDto;
import com.lavanya.web.dto.NotificationDto;
import com.lavanya.web.dto.UserDto;
import com.lavanya.web.proxies.FriendProxy;
import com.lavanya.web.proxies.NotificationProxy;
import com.lavanya.web.proxies.UserProxy;
import org.bouncycastle.math.ec.rfc7748.X448;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Controller used in MVC architecture to control all the requests related to Friend object.
 * @author lavanya
 */
@Controller
public class FriendController {

    @Autowired
    FriendProxy friendProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    NotificationProxy notificationProxy;

    /**
     * POST request to send notification to another user to be part of friends list.
     *
     * @param userInvitedId is the id of the user profile visited by the user connected.
     * @return the url /user/topos
     */
    @PostMapping("/request/friend")
    public String sendRequestForFriendInvitation(@ModelAttribute ("userInvitedId") UUID userInvitedId, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        FriendDto friendDto = new FriendDto();
        UserDto userInvited = userProxy.getUser(userInvitedId,token);
        UserDto userWhoInvite = userProxy.getUserConnected(token);

        friendDto.setUserInvited(userInvited);
        friendDto.setUserWhoInvite(userWhoInvite);

        friendProxy.save(friendDto,token);

        String fromFullId = userWhoInvite.getFirstName() + " " + userWhoInvite.getLastName();
        String fromEmail = userWhoInvite.getEmail();
        String  toFullId = userInvited.getFirstName() + " " + userInvited.getLastName();
        String toEmail = userInvited.getEmail();

        NotificationDto notificationDto = new NotificationDto(fromFullId,fromEmail,toFullId,toEmail);

        notificationProxy.sendFriendInvitation(notificationDto);

        return "redirect:/profile/user/" + userInvitedId;
    }

    @GetMapping("/user/friends")
    public String showlistOfFriendsByUser(HttpSession session, Model model) {

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

        UserDto userConnected = userProxy.getUserConnected(token);

        List<FriendDto> friendDtos1 = friendProxy.getFriendRequestsByUser(token);
        List<FriendDto> friendDtos2 = friendProxy.getFriendsListByUser(token);

        TreeMap<UserDto, FriendDto> mapOfFriendRequestWithUser = new TreeMap<>(new UserDtoLastNameComparator());

        for(FriendDto friendDto : friendDtos2){
            if(friendDto.getUserWhoInvite().getEmail().equals(userConnected.getEmail())){
                UserDto user = friendDto.getUserInvited();
                mapOfFriendRequestWithUser.put(user,friendDto);
            }else{
                UserDto user = friendDto.getUserWhoInvite();
                mapOfFriendRequestWithUser.put(user,friendDto);
            }
        }

        model.addAttribute("requests", friendDtos1);
        model.addAttribute("friendDtosMap", mapOfFriendRequestWithUser);

        return "friendsDashboard";
    }

    @PostMapping("/request/response")
    public String acceptFriendRequest(HttpSession session, @ModelAttribute ("id") int id){

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        friendProxy.acceptFriendInvitation(id,token);

        return "redirect:/user/friends";

    }

    @PostMapping("/delete/request")
    public String refuseFriendRequest(@ModelAttribute("id") int id, HttpSession session) {

        String token = (String) session.getAttribute("token");
        if(token==null) {
            return "redirect:/homePage#sign-in";
        }

        friendProxy.refuseFriendInvitation(id,token);



        return "redirect:/user/friends";
    }
}
