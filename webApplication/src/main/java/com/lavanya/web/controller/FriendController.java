package com.lavanya.web.controller;

import com.lavanya.web.dto.FriendDto;
import com.lavanya.web.dto.UserDto;
import com.lavanya.web.proxies.FriendProxy;
import com.lavanya.web.proxies.UserProxy;
import org.bouncycastle.math.ec.rfc7748.X448;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * POST request to send notification to another user to part of friends list.
     *
     * @param userInvitedId is the id of the user profile visited by the user connected.
     * @return the url /user/topos
     */
    @PostMapping("/request/friend")
    public String sendRequestForFriendInvitation(@ModelAttribute ("userInvitedId") int userInvitedId, @ModelAttribute ("userWhoInviteId") int userWhoInviteId) {

        FriendDto friendDto = new FriendDto();
        UserDto userInvited = userProxy.getUserConnected(userInvitedId);
        UserDto userWhoInvite = userProxy.getUserConnected(userWhoInviteId);

        friendDto.setUserInvited(userInvited);
        friendDto.setUserWhoInvite(userWhoInvite);

        friendProxy.save(friendDto);

        return "redirect:/profile/user/" + userInvitedId + "/" + userWhoInviteId;
    }

    @GetMapping("/user/friends/{id}")
    public String showlistOfFriendsByUser(@PathVariable ("id") int id, Model model) {

        List<FriendDto> friendDtos1 = friendProxy.getFriendRequestsByUser(id);
        List<FriendDto> friendDtos2 = friendProxy.getFriendsListByUser(id);

//      2nd way to send lsit of friends
        List<UserDto> friendsUserDtos = new ArrayList<>();

        for(FriendDto friendDto : friendDtos2){
           int userInvitedId = friendDto.getUserInvited().getId();
           int userWhoInviteId = friendDto.getUserWhoInvite().getId();
           if(userInvitedId == id ){
               UserDto userDto = userProxy.getUserConnected(userWhoInviteId);
               friendsUserDtos.add(userDto);
           }
        }
        model.addAttribute("friendsUserDtos", friendsUserDtos);
//     end of 2nd way


        model.addAttribute("requests", friendDtos1);
        model.addAttribute("friendDtos", friendDtos2);
        model.addAttribute("userConnectedId",id);

        return "friendsDashboard";
    }

    @PostMapping("/request/response")
    public String acceptFriendRequest(@ModelAttribute ("id") int id, @ModelAttribute ("userConnectedId") int userConnectedId){

        friendProxy.acceptFriendInvitation(id);

        return "redirect:/user/friends/" + userConnectedId;

    }

    @PostMapping("/delete/request")
    public String refuseFriendRequest(@ModelAttribute("id") int id, @ModelAttribute ("userConnectedId") int userConnectedId) {

        friendProxy.refuseFriendInvitation(id);

        return "redirect:/user/friends/" + userConnectedId;
    }
}
