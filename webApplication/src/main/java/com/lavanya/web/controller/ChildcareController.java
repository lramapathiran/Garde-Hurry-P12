package com.lavanya.web.controller;

import com.lavanya.web.dto.*;
import com.lavanya.web.proxies.ChildcareProxy;
import com.lavanya.web.proxies.ChildrenProxy;
import com.lavanya.web.proxies.FriendProxy;
import com.lavanya.web.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    @Autowired
    ChildrenProxy childrenProxy;

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
        int totalChildren = userConnected.getChildrenDtos().size();

        List<UserDto> users = new ArrayList<>();

        for(FriendDto friendDto : friendDtos) {

            if(friendDto.getUserWhoInvite().getId() == userConnectedId){
                users.add(friendDto.getUserInvited());
            }else{
                users.add(friendDto.getUserWhoInvite());
            }
        }

        model.addAttribute("childcareDto", childcareDto);
        model.addAttribute("friends", users);
        model.addAttribute("userConnectedId", userConnectedId);
        model.addAttribute("maxChildren", totalChildren);

        return "requestChildcareStepOne";
    }

    @PostMapping("/saveChildcare")
    public String saveChildcare(@Valid @ModelAttribute ("childcareDto") ChildcareDto childcareDto, BindingResult result,
                                @ModelAttribute ("id") int userConnectedId, @ModelAttribute ("userDtoWatchingId") int userDtoWatchingId, Model model){

        if (result.hasErrors()) {
            List<FriendDto> friendDtos = friendProxy.getFriendsListByUser(userConnectedId);
            UserDto userConnected = userProxy.getUserConnected(userConnectedId);
            int totalChildren = userConnected.getChildrenDtos().size();

            List<UserDto> users = new ArrayList<>();

            for(FriendDto friendDto : friendDtos) {

                if(friendDto.getUserWhoInvite().getId() == userConnectedId){
                    users.add(friendDto.getUserInvited());
                }else{
                    users.add(friendDto.getUserWhoInvite());
                }
            }

            model.addAttribute("childcareDto", childcareDto);
            model.addAttribute("friends", users);
            model.addAttribute("userConnectedId", userConnectedId);
            model.addAttribute("maxChildren", totalChildren);
            return "requestChildcareStepOne";
        }
        childcareDto.setUserDtoInNeed(userProxy.getUserConnected(userConnectedId));
        childcareDto.setUserDtoWatching(userProxy.getUserConnected(userDtoWatchingId));
        ChildcareDto childcareDtoSaved = childcareProxy.saveChildcare(childcareDto);

        return "redirect:/save/request/children/" + childcareDtoSaved.getId();
    }

    @GetMapping("/save/request/children/{id}")
    public String completeChildcare(@PathVariable ("id") int childcareId, @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "name", required = false) String name,Model model){

        String errorMessage = null;
        if(error != null) {
            errorMessage = "L'enfant " + name + " a déjà été ajouté, impossible de l'ajouter une seconde fois!";
        }
        model.addAttribute("errorMessage", errorMessage);

        ChildcareDto childcareDto = childcareProxy.getChildcareById(childcareId);
        model.addAttribute("childcare", childcareDto);

        int userId = childcareDto.getUserDtoInNeed().getId();
        model.addAttribute("userConnectedId", userId);

        int leftChildren = childcareDto.getNumberOfChildren() - childcareDto.getChildrenToWatch().size();
        model.addAttribute("leftChildren", leftChildren);

        if (childcareDto.getChildrenToWatch().size()<childcareDto.getNumberOfChildren()){
            String leftChildrenToAdd = "Vous devez encore ajouter " + leftChildren + " enfant(s) pour finaliser le formulaire de garde !";
            model.addAttribute("uncompleteAdditionOfChildrenToWatch", leftChildrenToAdd);
        }else{
            String success = "Vous avez compléter le formulaire de demande de garde avec succès, vous pouvez le valider pour notifier votre contact!";
            model.addAttribute("completeAdditionOfChildrenToWatch", success);
        }
        return "requestChildcareStepTwo";
    }

    @PostMapping("/saveChildrenToWatch")
    public String saveChildrenToWatchToChildcare(@ModelAttribute("childrenToWatchId") int childrenToWatchId, @ModelAttribute("childcareId") int childcareId) throws UnsupportedEncodingException {

        try{
            childcareProxy.saveChildrenToWatchToChildcare(childrenToWatchId,childcareId);
            return "redirect:/save/request/children/" + childcareId;
        }catch(Exception e){
            String name = childrenProxy.getChildrenById(childrenToWatchId).getName();
            String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String capNameEncoded = URLEncoder.encode(capName, "Utf-8");

            return "redirect:/save/request/children/" + childcareId + "?error=true&name=" + capNameEncoded;
        }


    }

    @PostMapping("/delete/childrenToWatch")
    public String deleteChildrenToWatchInChildcare(@ModelAttribute("childrenToWatchId") int childrenToWatchId, @ModelAttribute("childcareId") int childcareId){

        childcareProxy.deleteChildrenToWatchInChildcare(childrenToWatchId,childcareId);
        return "redirect:/save/request/children/" + childcareId;
    }

    @PostMapping("/validate/request/childcare")
    public String completeChildcareRequest(@ModelAttribute("childcareId") int childcareId, @ModelAttribute("userConnectedId") int userId) {
        childcareProxy.completeChildcareRequest(childcareId);
        return "redirect:/requests/childcares/" + userId;
    }

    @GetMapping("/requests/childcares/{id}")
    public String showUserChildcaresRequestsDashboard(@PathVariable("id") int userConnectedId,Model model) {

        UserDto userDto = userProxy.getUserConnected(userConnectedId);
        List<ChildcareDto> childcareDtosRequests = userDto.getChildcareDtosRequests();

        List<ChildcareDto> uncompleteChildcareDtosRequests = new ArrayList<>();
        List<ChildcareDto> unvalidatedChildcareDtosRequests = new ArrayList<>();
        List<ChildcareDto> validatedChildcareDtosRequests = childcareProxy.getChildcaresUserInNeedNotCommented(userDto);

        for(ChildcareDto childcareDto : childcareDtosRequests) {
            if(childcareDto.getComplete()==false) {
                uncompleteChildcareDtosRequests.add(childcareDto);
            }
            if(childcareDto.getValidated()==null){
                unvalidatedChildcareDtosRequests.add(childcareDto);
            }
        }
        model.addAttribute("uncompleteChildcares", uncompleteChildcareDtosRequests);
        model.addAttribute("validatedChildcares", validatedChildcareDtosRequests);
        model.addAttribute("unvalidatedChildcares", unvalidatedChildcareDtosRequests);

        model.addAttribute("userConnectedId", userConnectedId);

        return "childcaresRequestsDashboard";
    }

    @GetMapping("/missions/childcares/{id}")
    public String showUserChildcaresMissionsDashboard(@PathVariable("id") int userConnectedId,Model model) {

        UserDto userDto = userProxy.getUserConnected(userConnectedId);

        List<ChildcareDto> childcareDtosMissions = userDto.getChildcareDtosMissions();

        List<ChildcareDto> awaitingChildcareDtosMissions = new ArrayList<>();
        List<ChildcareDto> acceptedChildcareDtosMissions = childcareProxy.getChildcaresUserInChargeNotCommented(userDto);

        for(ChildcareDto childcareDto : childcareDtosMissions) {
            if(childcareDto.getComplete() && childcareDto.getValidated()==null) {
                awaitingChildcareDtosMissions.add(childcareDto);
            }
        }

        model.addAttribute("childcaresMissions", acceptedChildcareDtosMissions);
        model.addAttribute("unvalidatedChildcaresMissions", awaitingChildcareDtosMissions);

        model.addAttribute("userConnectedId", userConnectedId);

        return "childcaresMissionsDashboard";
    }

    @PostMapping("/validateChildcare")
    public String validateProfile(ValidateChildcare validateChildcare){
        ChildcareDto childcareDto = childcareProxy.getChildcareById(validateChildcare.getChildcareToValidateId());

        if(validateChildcare.getChildcareStatus()==null){
            childcareDto.setValidated(false);
        }else{
            childcareDto.setValidated(true);
        }


        childcareProxy.validateOrNotChildcare(childcareDto);

        return "redirect:/missions/childcares/" + validateChildcare.getUserConnectedId();
    }

    @PostMapping("/delete/childcare")
    public String deleteChildcare(@ModelAttribute ("id") int childcareId, @ModelAttribute ("userConnectedId") int userId, @ModelAttribute ("personWhoDelete") String personWhoDelete) {
        childcareProxy.deleteChildcare(childcareId);

        if(personWhoDelete.equals("childParent")) {
            return "redirect:/requests/childcares/" + userId;
        }else{
            return "redirect:/missions/childcares/" + userId;
        }
    }

    @PostMapping("/markAccomplishedChildcare")
    public String markChildcareAsAccomplished(@ModelAttribute ("childcareAccomplishedId") int childcareId, @ModelAttribute ("userConnectedId") int userId) {

        childcareProxy.accomplishChildcare(childcareId);
        return "redirect:/missions/childcares/" + userId;

    }


}
