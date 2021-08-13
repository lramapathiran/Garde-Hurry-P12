package com.lavanya.web.controller;

import com.lavanya.web.dto.ChildcareDto;
import com.lavanya.web.dto.FriendDto;
import com.lavanya.web.dto.UserDto;
import com.lavanya.web.proxies.ChildcareProxy;
import com.lavanya.web.proxies.FriendProxy;
import com.lavanya.web.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller used in MVC architecture to control all the requests related to Childcare object.
 * @author lavanya
 */
@Controller
public class ChildcareController {

    @Autowired
    ChildcareProxy childcareProxy;

    @Autowired
    FriendProxy friendProxy;

    @Autowired
    UserProxy userProxy;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("request/childcare/{userConnectedId}")
    public String showChildcareRequestForm(@PathVariable ("userConnectedId") int userConnectedId, Model model) {

        ChildcareDto childcareDto = new ChildcareDto();
        List<FriendDto> friendDtos = friendProxy.getFriendsListByUser(userConnectedId);
        UserDto userConnected = userProxy.getUserConnected(userConnectedId);

        List<UserDto> users = new ArrayList<>();

        for(FriendDto friendDto : friendDtos) {

            if(friendDto.getUserWhoInvite().getId() == userConnectedId){
                users.add(friendDto.getUserInvited());
            }else{
                users.add(friendDto.getUserWhoInvite());
            }
        }

        model.addAttribute("childcare", childcareDto);
        model.addAttribute("friends", users);
        model.addAttribute("userConnectedId", userConnectedId);

        return "requestChildcareStepOne";
    }

    @PostMapping("/saveChildcare")
    public String saveChildcare(@ModelAttribute ("childcare") ChildcareDto childcareDto,
                                @ModelAttribute ("id") int userConnectedId, @ModelAttribute ("userWatchingId") int userWatchingId){

        childcareDto.setUserInNeed(userProxy.getUserConnected(userConnectedId));
        childcareDto.setUserWatching(userProxy.getUserConnected(userWatchingId));
        ChildcareDto childcareDtoSaved = childcareProxy.saveChildcare(childcareDto);

        return "redirect:/save/request/children" + childcareDtoSaved.getId();
    }

    @GetMapping("/save/request/children/{id}")
    public String saveChildcareWithChildren(@PathVariable ("id") int childcareId){
        return "requestChildcareStepTwo";
    }
}
